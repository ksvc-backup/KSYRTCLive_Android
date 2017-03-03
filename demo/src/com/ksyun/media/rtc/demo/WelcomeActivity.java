package com.ksyun.media.rtc.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * select rtc streamer or rtc chat
 */

public class WelcomeActivity extends Activity implements View.OnClickListener {
    private Button mRTCChatButton;
    private Button mRTCStreamerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtc_welcome);

        mRTCChatButton = (Button) findViewById(R.id.rtc_chat);
        mRTCStreamerButton = (Button) findViewById(R.id.rtc_streamer);
        mRTCChatButton.setOnClickListener(this);
        mRTCStreamerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rtc_streamer:
                //开启连麦推流的参数配置窗口
                Intent intent = new Intent(getApplicationContext(), DemoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            case R.id.rtc_chat:
                //开启连麦私聊的参数配置窗口
                intent = new Intent(getApplicationContext(), DemoChatActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
                break;
            default:
                break;
        }
    }
}
