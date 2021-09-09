package com.zhangying.oem1688.util;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbiz.WXOpenCustomerServiceChat;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhangying.oem1688.constant.BuildConfig;

public class WeiXinActivity {
    public static void init(Context context) {
        try { Intent intent = new Intent(Intent.ACTION_MAIN);
            ComponentName cmp = new ComponentName("com.tencent.mm","com.tencent.mm.ui.LauncherUI");
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setComponent(cmp);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // TODO: handle exception
            Toast.makeText(context, "检查到您手机没有安装微信，请安装后使用该功能", Toast.LENGTH_LONG).show();
        }
    }

    //打开微信客服
    public static void openService(Context context){
        String appId = BuildConfig.WX_APPID; // 填移动应用(App)的 AppId
        IWXAPI api = WXAPIFactory.createWXAPI(context, appId);

        // 判断当前版本是否支持拉起客服会话
        if (api.getWXAppSupportAPI() >= Build.SUPPORT_OPEN_CUSTOMER_SERVICE_CHAT) {
            WXOpenCustomerServiceChat.Req req = new WXOpenCustomerServiceChat.Req();
            req.corpId = "wwe74a2803c4b31f74";							      // 企业ID
            req.url = "https://work.weixin.qq.com/kfid/kfc77a139564d0723f3";	// 客服URL
            api.sendReq(req);
        }
    }
}
