package com.ksy.recordlib.demo;

/**
 * Created by qyvideo on 8/2/16.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.ksy.recordlib.service.util.KSYOnHttpResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by qyvideo on 7/28/16.
 */
public class AuthHttpTask extends AsyncTask<String, Void, Void> {
    private static final String TAG = "AuthHttpTask";
    private static final boolean VERBOSE = true;
    public static final int RESPONSE_PARSE_ERROR = 600;


    private KSYOnHttpResponse mOnHttpResponse;

    public AuthHttpTask(KSYOnHttpResponse onHttpResponse) {
        this.mOnHttpResponse = onHttpResponse;
    }

    @Override
    protected Void doInBackground(String... strings) {
        HttpURLConnection conn = null;

        try {
            URL url = new URL(strings[0]);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();
            if (VERBOSE)
                Log.d(TAG, "responseCode=" + responseCode);
            if (responseCode == 200) {
                InputStream is = conn.getInputStream();
                String body = getStringFromInputStream(is);
                mOnHttpResponse.onHttpResponse(responseCode,body);
                if (VERBOSE)
                    Log.d(TAG, "response:" + body);
            } else {
                if (VERBOSE)
                    Log.e(TAG, "AuthHttpTask responseCode = " + responseCode);
                mOnHttpResponse.onHttpResponse(responseCode,null);
            }
        } catch (Exception e) {
            if (VERBOSE)
                Log.e(TAG, "AuthHttpTask failed");
            mOnHttpResponse.onHttpResponse(RESPONSE_PARSE_ERROR,null);
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }

    public String getStringFromInputStream(InputStream inStream) throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inStream.read(buffer)) != -1)
        {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        String body = outStream.toString();
        outStream.close();
        return body;
    }
}