package com.thk.backstackdemo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {
    protected VB binding;
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = getBinding();
        setContentView(binding.getRoot());

        TAG = getTag();
    }

    abstract VB getBinding();
    abstract String getTag();

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: -------------------------------------------");
        super.onDestroy();
    }
}
