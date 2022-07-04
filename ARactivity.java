package com.wheic.arapp;



import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Pose;
import com.google.ar.core.Session;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.Renderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import java.lang.ref.WeakReference;
import java.util.Objects;
import android.media.MediaPlayer;
public class ARactivity extends AppCompatActivity {

    private ArFragment arCam; //object of ArFragment Class

    private int clickNo = 0; //helps to render the 3d model only once when we tap the screen
    private AnchorNode anchorNode;

    public static boolean checkSystemSupport(Activity activity) {

        //checking whether the API version of the running Android >= 24 that means Android Nougat 7.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            String openGlVersion = ((ActivityManager) Objects.requireNonNull(activity.getSystemService(Context.ACTIVITY_SERVICE))).getDeviceConfigurationInfo().getGlEsVersion();

            //checking whether the OpenGL version >= 3.0
            if (Double.parseDouble(openGlVersion) >= 3.0) {
                return true;
            } else {
                Toast.makeText(activity, "App needs OpenGl Version 3.0 or later", Toast.LENGTH_SHORT).show();
                activity.finish();
                return false;
            }
        } else {
            Toast.makeText(activity, "App does not support required Build Version", Toast.LENGTH_SHORT).show();
            activity.finish();
            return false;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSystemSupport(this)) {

            arCam = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arCameraArea);
            //ArFragment is linked up with its respective id used in the activity_main.xml

            arCam.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
                clickNo++;
                //the 3d model comes to the scene only when clickNo is one that means once
                if (clickNo >=0) {
//System.out.println((QR_scanner.D.matches("gfg")));
                    Anchor anchor = hitResult.createAnchor();

                    //if (newClass.maxPos==1)  {//???

                        ModelRenderable.builder()
                                .setSource(this, R.raw.taj)

                                .setIsFilamentGltf(true)
                                .build()
                                .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                .exceptionally(throwable -> {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                    return null;
                                });

                    /*}
                    else if (newClass.maxPos==2){
                        ModelRenderable.builder()
                                .setSource(this, R.raw.gfg_gold_text_stand_2)
                                .setIsFilamentGltf(true)
                                .build()
                                .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                .exceptionally(throwable -> {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                    return null;
                                });

                    }
                    else  {
                        ModelRenderable.builder()
                                .setSource(this, R.raw.hg)
                                .setIsFilamentGltf(true)
                                .build()
                                .thenAccept(modelRenderable -> addModel(anchor, modelRenderable))
                                .exceptionally(throwable -> {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                    builder.setMessage("Somthing is not right" + throwable.getMessage()).show();
                                    return null;
                                });
                    }*/
                    playAudio();
                }



            });

        } else {

            return;

        }


    }

    private void addModel(Anchor anchor, ModelRenderable modelRenderable) {
        /*Session session=arCam.getArSceneView().getSession();

        float[] position = { 0, 0, (float) -0.75};       // 75 cm away from camera
        float[] rotation = { 1, 1, 1, 1 };
        Anchor a=session.createAnchor(new Pose(position,rotation));

        anchorNode = new AnchorNode(a);
        anchorNode.setRenderable(modelRenderable);
        anchorNode.setParent(arCam.getArSceneView().getScene());*/
        AnchorNode anchorNode = new AnchorNode(anchor);
        // Creating a AnchorNode with a specific anchor
        anchorNode.setParent(arCam.getArSceneView().getScene());
        //attaching the anchorNode with the ArFragment
        TransformableNode model = new TransformableNode(arCam.getTransformationSystem());
        model.setParent(anchorNode);
        //attaching the anchorNode with the TransformableNode
        model.setRenderable(modelRenderable);
        //attaching the 3d model with the TransformableNode that is already attached with the node
        model.select();

    }


    public void playAudio() {
        MediaPlayer music;
        /*if (d==0) {
            music = MediaPlayer.create(this, R.raw.grd);
        } else if(d==1) {
            music = MediaPlayer.create(this, R.raw.mgaudio);
        }
        else{*/
            music= MediaPlayer.create(this,R.raw.shastri);

        music.start();
        //music.stop();
    }
}