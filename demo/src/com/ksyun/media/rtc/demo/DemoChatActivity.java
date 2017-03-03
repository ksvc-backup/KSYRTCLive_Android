package com.ksyun.media.rtc.demo;

import com.ksyun.media.streamer.kit.StreamerConstants;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

/**
 * rtc chat config
 */

public class DemoChatActivity extends Activity implements View.OnClickListener {
    private static final String TAG = DemoChatActivity.class.getSimpleName();
    private Button mStartChatButton;
    private EditText mFrameRateEditText;
    private RadioButton mRes360Button;
    private RadioButton mRes480Button;
    private RadioButton mRes540Button;
    private RadioButton mRes720Button;

    private RadioButton mLandscapeButton;
    private RadioButton mPortraitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.rtc_chat_demo_activity);

        mStartChatButton = (Button) findViewById(R.id.start_chat);
        mStartChatButton.setOnClickListener(this);

        mFrameRateEditText = (EditText) findViewById(R.id.frameRatePicker);
        mFrameRateEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
        mRes360Button = (RadioButton) findViewById(R.id.radiobutton1);
        mRes480Button = (RadioButton) findViewById(R.id.radiobutton2);
        mRes540Button = (RadioButton) findViewById(R.id.radiobutton3);
        mRes720Button = (RadioButton) findViewById(R.id.radiobutton4);
        mLandscapeButton = (RadioButton) findViewById(R.id.orientationbutton1);
        mPortraitButton = (RadioButton) findViewById(R.id.orientationbutton2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_chat:
                int frameRate = 0;
                int videoResolution;
                int orientation;

                //采集帧率
                if (!TextUtils.isEmpty(mFrameRateEditText.getText().toString())) {
                    frameRate = Integer.parseInt(mFrameRateEditText.getText()
                            .toString());
                }
                //预览分辨率
                if (mRes360Button.isChecked()) {
                    videoResolution = StreamerConstants.VIDEO_RESOLUTION_360P;
                } else if (mRes480Button.isChecked()) {
                    videoResolution = StreamerConstants.VIDEO_RESOLUTION_480P;
                } else if (mRes540Button.isChecked()) {
                    videoResolution = StreamerConstants.VIDEO_RESOLUTION_540P;
                } else {
                    videoResolution = StreamerConstants.VIDEO_RESOLUTION_720P;
                }

                //预览横竖屏
                if (mLandscapeButton.isChecked()) {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                } else if (mPortraitButton.isChecked()) {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                } else {
                    orientation = ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR;
                }

                RTCChatActivity.startActivity(getApplicationContext(), 0,
                        frameRate, videoResolution, orientation);

                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}