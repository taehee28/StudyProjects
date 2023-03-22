package com.thk.backstackdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.thk.backstackdemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Log.d(TAG, "onCreate: ---------------------------");

        int randomNumber = (int) (Math.random() * 10);
        binding.tvHashcode.setText(String.valueOf(randomNumber));

        binding.btnStartFirst.setOnClickListener(view -> {
            Intent intent = new Intent(this, FirstActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.d(TAG, "onNewIntent: ----------------------");
    }
}