package com.example.app_ban_hang;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

import com.example.app_ban_hang.controller.MainActivity;
import com.github.ybq.android.spinkit.style.Wave;

public class WellcomeActivity extends AppCompatActivity {
    private ProgressBar spin_kitRecharge;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);

        spin_kitRecharge = findViewById(R.id.spin_kitRecharge);

        Wave wave = new Wave();
        spin_kitRecharge.setIndeterminateDrawable(wave);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(new Intent(WellcomeActivity.this, MainActivity.class));
                finish();

            }
        },3000);

    }
}
