package com.albert.viewapplication.intercept;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.albert.viewapplication.R;

public class TestInterceptActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intercept);
    }
}
