package com.albert.viewapplication;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.albert.viewapplication.drawmodel.HardWareDrawlActivity;
import com.albert.viewapplication.drawmodel.SoftWareDrawlActivity;
import com.albert.viewapplication.intercept.TestInterceptActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickHardware(View view) {
        startActivity(new Intent(this, HardWareDrawlActivity.class));
    }

    public void onClickSoftware(View view) {
        startActivity(new Intent(this, SoftWareDrawlActivity.class));
    }

    public void onClickIntercept(View view) {
        startActivity(new Intent(this, TestInterceptActivity.class));
    }

}
