package com.albert.accessiblityapplication;

import android.accessibilityservice.AccessibilityService;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/5/15.
 */

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class MyAccessibilityService extends AccessibilityService {


    public static final String TAG = "TestAccess";
    private boolean isContactBtnClicked;
    private static boolean isOfficeClicked;
    private volatile boolean isClicked;

    @Override
    protected void onServiceConnected() {
        Log.i(TAG, "Service is connecting");
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        AccessibilityNodeInfo source = event.getSource();
        if (source == null) {
            return;
        }

        clickOA(source);
        clickOffice(source);
        clickChckIn(source);
//        AccessibilityNodeInfo rowNode = getListItemNodeInfo(source);
    }

    private void clickChckIn(AccessibilityNodeInfo source) {
        if (source.getClassName().equals("android.webkit.WebView") && "小豹办公".equals(source.getContentDescription())) {
            source.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            if (!isClicked) {
                                isClicked = true;
                                Intent serviceIntent = new Intent();
                                serviceIntent.setClassName("com.albert.constriantapp", "com.albert.constriantapp.MyService");
                                startService(serviceIntent);
                            }
                        }
                    }
            ).start();
        }
    }

    private void clickOffice(AccessibilityNodeInfo source) {
        List<AccessibilityNodeInfo> nodeInfos2 = source.findAccessibilityNodeInfosByText("小豹办公");
        if (source.getClassName().equals("android.widget.ListView") && source.getChildCount() == 6) {
            source.getChild(2).performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

    private void clickOA(AccessibilityNodeInfo source) {
        List<AccessibilityNodeInfo> nodeInfos2 = source.findAccessibilityNodeInfosByText("猎豹OA");
        if (nodeInfos2 != null && nodeInfos2.size() > 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                nodeInfos2.get(0).performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }


    private void clickContactBtn(AccessibilityNodeInfo parent) {
        if (!isContactBtnClicked) {
            if (parent == null) {
                return;
            }
            parent.performAction(AccessibilityNodeInfo.ACTION_CLICK);
            if (parent.getParent() != null) {
                parent.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }
    }


    @Override
    public void onInterrupt() {

    }
}
