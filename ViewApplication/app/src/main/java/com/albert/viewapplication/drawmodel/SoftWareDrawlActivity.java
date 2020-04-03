package com.albert.viewapplication.drawmodel;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.albert.viewapplication.R;

/**
 * the only different with HardWareDrawlActivity is Manifest declare
 * android:hardwareAccelerated="false"
 */
public class SoftWareDrawlActivity extends HardWareDrawlActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((TextView)findViewById(R.id.libel)).setText("SoftWare Draw model");
    }
}
