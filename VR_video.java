package com.wheic.arapp;

import androidx.appcompat.app.AppCompatActivity;



import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.vr.sdk.widgets.common.VrWidgetView;
import com.google.vr.sdk.widgets.video.VrVideoView;

import com.google.vr.sdk.widgets.video.VrVideoEventListener;
import java.io.IOException;
import java.io.InputStream;

public class VR_video extends AppCompatActivity {
    private VrVideoView mVrVideoView;// for video

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_video);
        mVrVideoView = (VrVideoView) findViewById(R.id.vrVideoView);
        loadVideoSphere();

    }

    @Override
    public void onPause() {
        super.onPause();
        mVrVideoView.pauseRendering();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVrVideoView.resumeRendering();
    }

    @Override
    public void onDestroy() {
        mVrVideoView.shutdown();
        super.onDestroy();
    }

    private void loadVideoSphere() {
        VrVideoView.Options options = new VrVideoView.Options();
        options.inputType = VrVideoView.Options.TYPE_MONO;

        options.inputFormat = options.FORMAT_DEFAULT;

        try {
            mVrVideoView.loadVideoFromAsset("vr_vedio1.mp4", options);
        } catch (IOException e) {
            e.printStackTrace();
        }


        //mVrVideoView.setStereoModeButtonEnabled(true);
        /*mVrVideoView.setFullscreenButtonEnabled(true);
        mVrVideoView.setInfoButtonEnabled(true);
mVrVideoView.setTouchscreenBlocksFocus(true);*/
        mVrVideoView.setDisplayMode(VrWidgetView.DisplayMode.EMBEDDED);
        mVrVideoView.playVideo();

    }
}

