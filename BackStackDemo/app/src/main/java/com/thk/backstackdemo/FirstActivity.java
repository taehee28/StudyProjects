package com.thk.backstackdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.thk.backstackdemo.databinding.ActivityFirstBinding;

public class FirstActivity extends BaseActivity<ActivityFirstBinding> {
    @Override
    ActivityFirstBinding getBinding() {
        return ActivityFirstBinding.inflate(getLayoutInflater());
    }

    @Override
    String getTag() {
        return FirstActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.btnStartSecond.setOnClickListener(view -> {
            Intent intent = new Intent(this, SecondActivity.class);
            startActivity(intent);
        });
    }
}