package com.example.securegallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddImageActivity extends AppCompatActivity {

    static final int PICK_FILE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        Button btnPick = findViewById(R.id.btnPick);

        btnPick.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            i.setType("*/*");
            i.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(i, PICK_FILE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FILE && resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            try {
                FileUtils.copyToSecureGallery(this, uri);
                Toast.makeText(this,
                        "Ajouté à la galerie ✔",
                        Toast.LENGTH_SHORT).show();
                finish();

            } catch (Exception e) {
                Toast.makeText(this,
                        "Erreur ajout fichier",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
