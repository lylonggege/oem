package com.zhangying.oem1688.adpter;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.widget.ImageView;

import com.mob.MobSDK;
import com.xuexiang.xui.adapter.recyclerview.BaseRecyclerAdapter;
import com.xuexiang.xui.adapter.recyclerview.RecyclerViewHolder;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.bean.ShareBean;
import com.zhangying.oem1688.onterface.IMessageView;
import com.zhangying.oem1688.onterface.OnMultiClickListener;
import com.zhangying.oem1688.util.ToastUtil;

import androidx.annotation.NonNull;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.onekeyshare.ShareContentCustomizeCallback;

public class ShareItemAdpter extends BaseRecyclerAdapter<String> {
    public ShareItemAdpter(Context context,ShareBean shareBean) {
        this.context = context;
        this.shareBean = shareBean;
    }
    private Context context;
    private ShareBean shareBean;

    private IMessageView iCloseEvent;
    public void setiCloseEvent(IMessageView iCloseEvent) {
        this.iCloseEvent = iCloseEvent;
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.share_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
        ImageView image = holder.findViewById(R.id.image);
        String itemId = "",platform = "";
        switch (item){
            case "1":
                itemId = "share_wx";
                platform = "Wechat";
                break;
            case "2":
                itemId = "share_group";
                platform = "WechatMoments";
                break;
            case "3":
                itemId = "share_qq";
                platform = "QQ";
                break;
            case "4":
                itemId = "share_zone";
                platform = "QZone";
                break;
        }
        image.setImageResource(this.getResId(itemId));

        String finalPlatform = platform;
        image.setOnClickListener(new OnMultiClickListener(){
            @Override
            public void onMultiClick(View view) {
                showShare(finalPlatform);
            }
        });
    }

    private int getResId(String name){
        Resources r = context.getResources();
        int resId = r.getIdentifier(name, "drawable", context.getPackageName());
        return resId;
    }

    private void showShare(String platform) {
        final OnekeyShare oks = new OnekeyShare();
        //指定分享的平台，如果为空，还是会调用九宫格的平台列表界面
        if (platform != null) {
            oks.setPlatform(platform);
        }
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(shareBean.getTitle());
        // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
        oks.setTitleUrl(shareBean.getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(shareBean.getDesc());
        //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
        oks.setImageUrl(shareBean.getImage());
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(shareBean.getUrl());
        //分享回调
        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                // 分享成功回调
                System.out.println("Share Complete");
            }
            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                // 分享失败回调
                // 失败的回调，arg:平台对象，arg1:表示当前的动作(9表示分享)，arg2:异常信息
                System.out.println("Share Error");
            }
            @Override
            public void onCancel(Platform platform, int i) {
                System.out.println("Share Cancel");
                // 分享取消回调
                ToastUtil.showToast("取消分享");
            }
        });
        //去除分享正在后台的提示
        oks.setDisappearShareToast(true);
        //关闭一键分享默认ui
        oks.setSilent(true);
        //启动分享
        oks.show(MobSDK.getContext());

        if (iCloseEvent != null){
            iCloseEvent.viewPosition(0);
        }
    }
}
