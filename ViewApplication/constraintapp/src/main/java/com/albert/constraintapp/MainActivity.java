package com.albert.constraintapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        findViewById(R.id.root_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Root is Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.cmd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                try {
                                        Process p = Runtime.getRuntime().exec(new String[]{"input", "tap", "450", "1400"});
                                        p.waitFor();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                ).start();
            }
        });

        findViewById(R.id.toast_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "HelloWorld", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.inner_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent innerServiceIntent = new Intent(MainActivity.this, MyService.class);
                startService(innerServiceIntent);
            }
        });

        findViewById(R.id.out_service_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outServiceIntent = new Intent();
                outServiceIntent.setClassName("com.albert.accessiblityapplication", "com.albert.accessiblityapplication.MyService");
                startService(outServiceIntent);
            }
        });

    }
}
