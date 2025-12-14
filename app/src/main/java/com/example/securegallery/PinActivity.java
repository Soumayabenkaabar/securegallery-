package com.example.securegallery;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PinActivity extends AppCompatActivity {

    private EditText etPin;
    private Button btnValidate;
    private MediaPlayer alarmPlayer;
    private SharedPreferences prefs;

    private static final String PREFS = "security";
    private static final String PIN_KEY = "pin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        etPin = findViewById(R.id.etPin);
        btnValidate = findViewById(R.id.btnValidate);

        prefs = getSharedPreferences(PREFS, MODE_PRIVATE);

        // PIN par défaut
        if (!prefs.contains(PIN_KEY)) {
            prefs.edit().putString(PIN_KEY, "1234").apply();
        }

        // 🔔 Alarme
        alarmPlayer = MediaPlayer.create(this, R.raw.alarmsouand);

        btnValidate.setOnClickListener(v -> checkPin());
    }

    private void checkPin() {
        String enteredPin = etPin.getText().toString().trim();
        String savedPin = prefs.getString(PIN_KEY, "1234");

        if (enteredPin.equals(savedPin)) {

            // ✅ PIN correct → ouvrir la galerie
            startActivity(new Intent(this, GalleryActivity.class));
            finish();

        } else {

            // ❌ PIN incorrect
            Toast.makeText(this, "PIN incorrect !", Toast.LENGTH_SHORT).show();

            // 🔔 lancer alarme
            if (alarmPlayer != null) alarmPlayer.start();

            // 📸 prendre photo automatique (caméra invisible)
            startActivity(new Intent(this, IntruderCaptureActivity.class));
        }

        etPin.setText(""); // vider champ
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (alarmPlayer != null) {
            alarmPlayer.release();
            alarmPlayer = null;
        }
    }
}
