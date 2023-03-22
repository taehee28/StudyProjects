package com.thk.backstackdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.thk.backstackdemo.databinding.ActivitySecondBinding;

public class SecondActivity extends BaseActivity<ActivitySecondBinding> {
    @Override
    ActivitySecondBinding getBinding() {
        return ActivitySecondBinding.inflate(getLayoutInflater());
    }

    @Override
    String getTag() {
        return SecondActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.btnStartThird.setOnClickListener(view -> {
            Intent intent = new Intent(this, ThirdActivity.class);
            startActivity(intent);
        });
    }
}