package com.albert.viewapplication.horizontal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.albert.viewapplication.R;

public class HorizontalViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
