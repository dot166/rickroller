package com.j.rickroller;

import static com.j.rickroller.settings.KEY_ONLINE;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import androidx.preference.PreferenceManager;

public class VideoActivity extends Activity {
    SharedPreferences prefs;
    boolean OnlineMode;
    VideoView video;
    int stopPosition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        video = findViewById(R.id.video);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        OnlineMode = prefs.getBoolean(KEY_ONLINE, false);
        Log.i("CONFIG", "online = " + OnlineMode);
        if (OnlineMode) {
            video.setVideoURI(Uri.parse("https://jf916.github.io/coolvideo.mp4"));
            video.start();
        } else {
            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.rickroll));
            video.start();
        }
        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });
    }
    @Override
    public void onPause() {
        Log.d("Video", "onPause called");
        super.onPause();
        stopPosition = video.getCurrentPosition(); //stopPosition is an int
        video.pause();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d("Video", "onResume called");
        video.seekTo(stopPosition);
        video.start(); //Or use resume() if it doesn't work. I'm not sure
    }
}