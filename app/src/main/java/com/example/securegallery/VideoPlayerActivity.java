package com.example.securegallery;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class VideoPlayerActivity extends AppCompatActivity {

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoView = findViewById(R.id.videoView);

        String path = getIntent().getStringExtra("path");

        if (path == null) {
            Toast.makeText(this,
                    "Vidéo introuvable",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        File videoFile = new File(path);

        if (!videoFile.exists()) {
            Toast.makeText(this,
                    "Fichier vidéo absent",
                    Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        Uri uri = Uri.fromFile(videoFile);

        MediaController controller = new MediaController(this);
        controller.setAnchorView(videoView);

        videoView.setMediaController(controller);
        videoView.setVideoURI(uri);

        videoView.setOnPreparedListener(mp -> videoView.start());
    }
}
