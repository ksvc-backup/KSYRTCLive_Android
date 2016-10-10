package com.ksy.recordlib.demo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by qyvideo on 10/8/16.
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    public static final String CONNECTIVITY_ACTION_LOLLIPOP = "com.example.CONNECTIVITY_ACTION_LOLLIPOP";

    protected List<WeakReference<NetworkStateReceiverListener>> listeners;
    protected Boolean connected;
    private Object lock = new Object();

    public NetworkStateReceiver() {
        listeners = new LinkedList<>();
        connected = null;
    }

    public void onReceive(Context context, Intent intent) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP && TextUtils.equals(intent.getAction(), ConnectivityManager.CONNECTIVITY_ACTION) ||
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && TextUtils.equals(intent.getAction(), CONNECTIVITY_ACTION_LOLLIPOP)) {

            if (intent.getExtras() != null) {
                final ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                    connected = true;
                }
            }

            if (intent.getExtras().getBoolean(ConnectivityManager.EXTRA_NO_CONNECTIVITY, Boolean.FALSE)) {
                connected = false;
            }
        }

        notifyStateToAll();
    }



    private void notifyStateToAll() {
        synchronized(lock) {
            for (WeakReference<NetworkStateReceiverListener> listener : listeners)
                notifyState(listener.get());
        }
    }

    private void notifyState(NetworkStateReceiverListener listener) {
        if(connected == null || listener == null)
            return;

        if(connected == true)
            listener.networkAvailable();
        else
            listener.networkUnavailable();
    }

    public void addListener(WeakReference<NetworkStateReceiverListener> l) {
        synchronized(lock) {
            listeners.add(l);
            notifyState(l.get());
        }
    }

    public void removeListeners() {
        synchronized(lock) {
            listeners.clear();
        }
    }

    public interface NetworkStateReceiverListener {
        public void networkAvailable();
        public void networkUnavailable();
    }
}