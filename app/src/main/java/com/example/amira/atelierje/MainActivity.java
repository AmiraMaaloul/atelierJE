package com.example.amira.atelierje;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Callback, MediaPlayer.OnPreparedListener {
    private MediaPlayer mediaPlayer;
    private SurfaceHolder videoHolder;
    private SurfaceView videoSurface;
    String vidAddress = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void surfaceCreated(SurfaceHolder holder) {

        Uri vidUri = Uri.parse(vidAddress);
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDisplay(videoHolder);
            mediaPlayer.setDataSource(vidAddress);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        } catch (IllegalArgumentException e) {
            Log.d("Media_player", e.getMessage());
        } catch (IOException e) {
            Log.d("Media_player", e.getMessage());
        }

    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        mediaPlayer.release();
    }

    public void onPrepared(MediaPlayer media){
        mediaPlayer.start();
        /*int pos = mediaPlayer.getCurrentPosition();
        int duration = mediaPlayer.getDuration();
       mediaPlayer.seekTo(pos + (duration - pos) / 10);*/
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mediaPlayer = new MediaPlayer();
        videoSurface = (SurfaceView) findViewById(R.id.surface);
        videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);


        MediaController videoControl = new MediaController(this);
        videoControl.setAnchorView(videoSurface);
       // videoSurface.setMediaController(videoControl);

        videoHolder.setFixedSize(400, 300);


        Button fastRewindBtn = (Button) findViewById(R.id.btn_fast_rewind);
        fastRewindBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                mediaPlayer.seekTo(pos - (duration - pos) / 10);
            }
        });

        Button playBtn = (Button) findViewById(R.id.btn_play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            boolean stateVideo = false;
            @Override
            public void onClick (View v){
                onPrepared(mediaPlayer);
            }
            /*if(stateVideo==false)
            {
                @Override
                public void onClick (View v){
                onPrepared(mediaPlayer);
                }
                stateVideo = true;
            }
            }else{
                mediaPlayer.stop();
            }*/
        });

        Button fastForwardBtn = (Button) findViewById(R.id.btn_fast_forward);
        fastForwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = mediaPlayer.getCurrentPosition();
                int duration = mediaPlayer.getDuration();
                mediaPlayer.seekTo(pos + (duration - pos) / 5);
            }
        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}