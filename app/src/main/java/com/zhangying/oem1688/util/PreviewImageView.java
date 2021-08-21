package com.zhangying.oem1688.util;

import android.app.Activity;
import android.widget.ImageView;

import com.xuexiang.xui.widget.imageview.preview.PreviewBuilder;
import com.zhangying.oem1688.R;

import java.util.List;

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