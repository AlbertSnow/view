package com.albert.accessiblityapplication;

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
        findViewById(R.id.root_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Root View Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        findViewById(R.id.cmd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fakeClickView();
            }
        });
        findViewById(R.id.inner_server_cmd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent innerIntent = new Intent(MainActivity.this, MyService.class);
                startService(innerIntent);
            }
        });
        findViewById(R.id.out_server_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent outServer = new Intent();
                outServer.setClassName("com.albert.constraintapp", "com.albert.constraintapp.MyService");
                startService(outServer);
            }
        });
    }

    private void fakeClickView() {
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
}
