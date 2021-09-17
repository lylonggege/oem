package com.zhangying.oem1688.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;
import com.xuexiang.xui.widget.imageview.preview.ui.PreviewActivity;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.xuexiang.xui.widget.imageview.preview.ui.BasePhotoFragment.KEY_SING_FILING;

public class PreviewImageView {
    public static void save(ImageView imageView, int index, List<ImageViewInfo> list) {
        PreviewBuilder.from((Activity) imageView.getContext())
                .setImgs(list)
                .setCurrentIndex(index)
                .setSingleFling(true)
                .setProgressColor(R.color.app_blue)
                .setType(PreviewBuilder.IndicatorType.Number)
                .start();//启动
    }
}