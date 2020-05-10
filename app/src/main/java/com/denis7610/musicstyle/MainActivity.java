package com.denis7610.musicstyle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void licenseInfo(View view) {
        Intent intent = new Intent(this, LicenseInfo.class);
        startActivity(intent);
    }
}
