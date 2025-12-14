package com.example.securegallery;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ImageViewerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ImageView iv = new ImageView(this);
        iv.setAdjustViewBounds(true);
        setContentView(iv);

        String path = getIntent().getStringExtra("path");
        iv.setImageBitmap(BitmapFactory.decodeFile(path));
    }
}
