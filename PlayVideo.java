package com.wheic.arapp;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

public class PlayVideo extends AppCompatActivity {

    VideoView videoView;
Button Back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        getSupportActionBar().hide();
        Back=findViewById(R.id.loopBack);
        videoView=findViewById(R.id.videoview);
        int r;

        if (newClass.flag==1) {
            r=R.raw.avatar_data;
            newClass.flag=0;
        }
        else{
            r=R.raw.v1;
        }
        Uri uri=Uri.parse("android.resource://" + getPackageName() + "/" + r);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.suspend();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                videoView.pause();
                
                Intent b=new Intent(PlayVideo.this,newClass.class);
                startActivity(b);

            }
        });
    }


    @Override
    protected void onResume() {
        videoView.resume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}