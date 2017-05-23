package com.albert.accessiblityapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("testService", "this is accessibility server");
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
        return super.onStartCommand(intent, flags, startId);
    }
}