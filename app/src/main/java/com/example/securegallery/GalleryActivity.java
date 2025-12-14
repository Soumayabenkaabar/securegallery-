package com.example.securegallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class GalleryActivity extends AppCompatActivity {

    RecyclerView rvImages;
    Button btnAdd;
    GalleryAdapter adapter;
    ArrayList<File> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        rvImages = findViewById(R.id.rvImages);
        btnAdd = findViewById(R.id.btnAdd);

        rvImages.setLayoutManager(new GridLayoutManager(this, 3));

        // 🔥 INITIALISATION OBLIGATOIRE
        imageList = new ArrayList<>();
        adapter = new GalleryAdapter(this, imageList);
        rvImages.setAdapter(adapter);

        loadImages();

        btnAdd.setOnClickListener(v -> {
            Intent i = new Intent(GalleryActivity.this, AddImageActivity.class);
            startActivityForResult(i, 1001);
        });
    }

    private void loadImages() {

        imageList.clear();

        File dir = FileUtils.getSecureDir(this);


        if (dir.exists()) {
            File[] files = dir.listFiles();

            if (files != null) {
                Arrays.sort(files, (a, b) ->
                        Long.compare(b.lastModified(), a.lastModified()));

                for (File file : files) {
                    imageList.add(file);
                }
            }
        }

        adapter.notifyDataSetChanged(); // 🔥 MAINTENANT ÇA MARCHE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loadImages();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadImages(); // 🔥 recharge automatique
    }
}

