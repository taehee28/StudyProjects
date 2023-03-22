package com.thk.backstackdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.thk.backstackdemo.databinding.ActivityThirdBinding;

public class ThirdActivity extends BaseActivity<ActivityThirdBinding> {

    @Override
    ActivityThirdBinding getBinding() {
        return ActivityThirdBinding.inflate(getLayoutInflater());
    }

    @Override
    String getTag() {
        return ThirdActivity.class.getSimpleName();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.btnStartMain.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}