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
import com.zhangying.oem1688.onterface.OnMultiClickListener;

import androidx.annotation.NonNull;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class ShareItemAdpter extends BaseRecyclerAdapter<String> {
    public ShareItemAdpter(Context context,ShareBean shareBean) {
        this.context = context;
        this.shareBean = shareBean;
    }
    private Context context;
    private ShareBean shareBean;
    private String platform = "";

    @Override
    protected int getItemLayoutId(int viewType) {
        return R.layout.share_item;
    }

    @Override
    protected void bindData(@NonNull RecyclerViewHolder holder, int position, String item) {
        ImageView image = holder.findViewById(R.id.image);
        String itemId = "";
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

        image.setOnClickListener(new OnMultiClickListener(){
            @Override
            public void onMultiClick(View view) {
                showShare(platform);
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
        oks.setUrl("http://sharesdk.cn");
        //启动分享
        oks.show(MobSDK.getContext());
    }
}
