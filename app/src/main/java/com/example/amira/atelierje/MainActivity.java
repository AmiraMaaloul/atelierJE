package com.example.amira.atelierje;

import android.media.MediaPlayer;
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

public class MainActivity extends AppCompatActivity implements Callback {
    private MediaPlayer mediaPlayer;
    @Override
   public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mediaPlayer =  new MediaPlayer();
        SurfaceView surface = (SurfaceView)findViewById(R.id.surface);
        SurfaceHolder holder = surface.getHolder();
        holder.addCallback(this);
        holder.setFixedSize(400,300);

        Button playBtn = (Button) findViewById(R.id.btn_play);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


      /*
        // Add playback controls.
        MediaController vidControl = new MediaController(this);
        // Set it to use the VideoView instance as its anchor.
        vidControl.setAnchorView(vidView);
        // Set it as the media controller for the VideoView object.
        vidView.setMediaController(vidControl);
*/
        // Prepare the URI for the endpoint.
        String vidAddress = "android.resource://" + getPackageName() + "/" + R.raw.video;
        Uri vidUri = Uri.parse(vidAddress);
        // Parse the address string as a URI so that we can pass it to the VideoView object.
       // vidView.setVideoURI(vidUri);
        // Start playback.
       // vidView.start();
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        String vidAddress = "android.resource://" + getPackageName() + "/" + R.raw.video;

        try{
            mediaPlayer.setDisplay(holder);
            mediaPlayer.setDataSource(vidAddress);
            mediaPlayer.prepare();
            mediaPlayer.start();
            int pos = mediaPlayer.getCurrentPosition();
            int duration = mediaPlayer.getDuration();

            mediaPlayer.seekTo(pos +(duration-pos)/10);

        }catch (IllegalArgumentException e)
        {
            Log.d("Media_player", e.getMessage());
        }catch (IOException e)
        {
            Log.d("Media_player", e.getMessage());
        }

    }

    public void surfaceDestroyed(SurfaceHolder holder){
        mediaPlayer.release();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

}
