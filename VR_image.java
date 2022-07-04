package com.wheic.arapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.IOException;
import java.io.InputStream;

public class VR_image extends AppCompatActivity {
    private VrPanoramaView mVrPanoramaView;// for image

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vr_image);
        mVrPanoramaView = (VrPanoramaView) findViewById(R.id.vrPanoramaView);
        loadPhotoSphere();

    }
    @Override
    public void onPause() {
        super.onPause();
        mVrPanoramaView.pauseRendering();
    }

    @Override
    public void onResume() {
        super.onResume();
        mVrPanoramaView.resumeRendering();
    }

    @Override
    public void onDestroy() {
        mVrPanoramaView.shutdown();
        super.onDestroy();
    }
    private void loadPhotoSphere() {
        VrPanoramaView.Options options = new VrPanoramaView.Options();
        InputStream inputStream = null;

        AssetManager assetManager = getAssets();
        try {
            inputStream = assetManager.open("vr_image1"+ ".jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        options.inputType = VrPanoramaView.Options.TYPE_MONO;

        mVrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);

        mVrPanoramaView.setStereoModeButtonEnabled(true);
        mVrPanoramaView.setInfoButtonEnabled(true);
        mVrPanoramaView.setPureTouchTracking(true);
        mVrPanoramaView.setFullscreenButtonEnabled(true);

    }

}