package com.j.rickroller

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.MediaController
import android.widget.VideoView
import io.github.dot166.jlib.app.jActivity
import androidx.core.net.toUri

class MainActivity : jActivity() {
    var onlineMode: Boolean = false
    var video: VideoView? = null
    var mediaControls: MediaController? = null
    var stopPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(findViewById(R.id.actionbar))

        video = findViewById(R.id.video)
        onlineMode = Config.getOnlineMode(this)
        Log.i("CONFIG", "online = $onlineMode")
        if (mediaControls == null) {
            // create an object of media controller class
            mediaControls = MediaController(this)
            mediaControls!!.setAnchorView(video)
        }
        // set the media controller for video view
        video!!.setMediaController(mediaControls)
        if (onlineMode) {
            video!!.setVideoURI(Config.getURL(applicationContext)?.toUri())
            video!!.start()
        } else {
            video!!.setVideoURI(("android.resource://" + packageName + "/" + R.raw.rickroll).toUri())
            video!!.start()
        }
        video!!.setOnPreparedListener { mp -> mp.isLooping = true }
    }

    public override fun onPause() {
        Log.d("Video", "onPause called")
        super.onPause()
        stopPosition = video!!.getCurrentPosition() //stopPosition is an int
        video!!.pause()
    }

    public override fun onResume() {
        super.onResume()
        Log.d("Video", "onResume called")
        video!!.seekTo(stopPosition)
        video!!.start() //Or use resume() if it doesn't work. I'm not sure
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.options -> {
                // User chooses the "Settings" item. Show the app settings UI.
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }

            R.id.share -> {
                ShareBottomSheet().show(
                    supportFragmentManager,
                    ShareBottomSheet.TAG
                )
                return true
            }

            else ->
                // The user's action isn't recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item)
        }
    }
}
