package com.albert.viewapplication.drawmodel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.albert.viewapplication.R;

public class HardWareDrawlActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hw_view);
        ((TextView)findViewById(R.id.libel)).setText("Hardware Draw model");
    }
}
