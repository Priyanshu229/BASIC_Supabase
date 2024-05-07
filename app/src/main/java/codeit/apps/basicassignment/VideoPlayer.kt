package codeit.apps.basicassignment

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView

class VideoPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        val videoUrl = intent.getStringExtra("videoUrl")
        val channel = intent.getStringExtra("channel")
        val title = intent.getStringExtra("title")
        val likes = intent.getStringExtra("likes")
        val description = intent.getStringExtra("description")

        val channelTV = findViewById<TextView>(R.id.channelTV)
        val titleTV = findViewById<TextView>(R.id.titleTV)
        val descriptionTV = findViewById<TextView>(R.id.descriptionTV)

        channelTV.text = channel
        titleTV.text = title
        descriptionTV.text = description



        val videoView: VideoView = findViewById(R.id.videoView)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoUri = Uri.parse(videoUrl)
        videoView.setVideoURI(videoUri)
        videoView.start()
    }
}