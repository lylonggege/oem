package com.zhangying.oem1688.util;

import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewSeting {
    public static void setting(WebView webView, Activity activity, String content) {
        WebSettings settings = webView.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        settings.setJavaScriptEnabled(true);//支持js
        settings.setBuiltInZoomControls(true); // 显示放大缩小
        settings.setSupportZoom(true); // 可以缩放
        int screenWidth = ScreenTools.instance(activity).getScreenWidth();
        int dpiwith = ScreenTools.instance(activity).px2dip(screenWidth);
        settings.setDefaultFontSize((dpiwith * 20 / 320));
        webView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
    }
}
