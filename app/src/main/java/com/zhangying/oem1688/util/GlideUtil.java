package com.zhangying.oem1688.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.zhangying.oem1688.R;


import java.io.File;



public class GlideUtil {


    /*
     *加载图片(默认)
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background) //占位图
                .error(R.drawable.ic_launcher_background)      //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!((Activity)context).isFinishing()){
            Glide.with(context).load(url).apply(options).into(imageView);
        }



    }

    /*
     *加载圆角图片(默认)
     */
    public static void loadCircleSizeImage(Context context, String url, ImageView imageView, int round, int margin) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background) //占位图
                .error(R.drawable.ic_launcher_background)      //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(url).apply(options).into(imageView);
        }

    }


    /*
     *加载图片(默认)
     */
    public static void loadLocalImage(Context context, int resouceId, ImageView imageView) {
        RequestOptions options = new RequestOptions()
//                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background) //占位图
                .error(R.drawable.ic_launcher_background)       //错误图
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(resouceId).apply(options).into(imageView);
        }

    }

    /**
     * 指定图片大小;使用override()方法指定了一个图片的尺寸。
     * Glide现在只会将图片加载成width*height像素的尺寸，而不会管你的ImageView的大小是多少了。
     * 如果你想加载一张图片的原始尺寸的话，可以使用Target.SIZE_ORIGINAL关键字----override(Target.SIZE_ORIGINAL)
     *
     * @param context
     * @param url
     * @param imageView
     * @param width
     * @param height
     */
    public static void loadImageSize(Context context, String url, ImageView imageView, int width, int height) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background) //占位图
                .error(R.drawable.ic_launcher_background)       //错误图
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(url).apply(options).into(imageView);
        }

    }


    /**
     * 禁用内存缓存功能
     * diskCacheStrategy()方法基本上就是Glide硬盘缓存功能的一切，它可以接收五种参数：
     * <p>
     * DiskCacheStrategy.NONE： 表示不缓存任何内容。
     * DiskCacheStrategy.DATA： 表示只缓存原始图片。
     * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
     * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
     * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
     */

    public static void loadImageSizekipMemoryCache(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background) //占位图
                .error(R.drawable.ic_launcher_background)       //错误图S
                .skipMemoryCache(true)//禁用掉Glide的内存缓存功能
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(url).apply(options).into(imageView);
        }
    }

    /**
     * 加载圆形图片
     */
    public static void loadCircleImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!((Activity)context).isFinishing()){
            Glide.with(context).load(url).apply(options).into(imageView);
        }

    }

    /**
     * 预先加载图片
     * 在使用图片之前，预先把图片加载到缓存，调用了预加载之后，我们以后想再去加载这张图片就会非常快了，
     * 因为Glide会直接从缓存当中去读取图片并显示出来
     */
    public static void preloadImage(Context context, String url) {
        Glide.with(context)
                .load(url)
                .preload();

    }

    /**
     * 加载圆角图片
     */
    public static void loadRoundCircleImage(Context context, String url, ImageView imageView, int radiu) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop()//设置圆形
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(url).apply(options).into(imageView);
        }
    }



    /**
     * 加载模糊图片（自定义透明度）
     *
     * @param context
     * @param url
     * @param imageView
     * @param blur      模糊度，一般1-100够了，越大越模糊
     */
    public static void loadBlurImage(Context context, String url, ImageView imageView, int blur) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /*
     *加载灰度(黑白)图片（自定义透明度）
     */
    public static void loadBlackImage(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                //.priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    /**
     * Glide.with(this).asGif()    //强制指定加载动态图片
     * 如果加载的图片不是gif，则asGif()会报错， 当然，asGif()不写也是可以正常加载的。
     * 加入了一个asBitmap()方法，这个方法的意思就是说这里只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。
     * 如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
     *
     * @param context
     * @param url       例如：https://image.niwoxuexi.com/blog/content/5c0d4b1972-loading.gif
     * @param imageView
     */
    public static void loadGif(Context context, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context)
                    .load(url)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
        }


    }

    /*加载本地GIF图*/
    public static void loadLocalGif(Context context, int resouceId, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context)
                    .load(resouceId)
                    .apply(options)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imageView);
        }
    }


    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public void downloadImage(final Context context, final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //String url = "http://www.guolin.tech/book.png";
                    FutureTarget<File> target = Glide.with(context)
                            .asFile()
                            .load(url)
                            .submit();
                    final File imageFile = target.get();
                    LogUtil.i("logcat", "下载好的图片文件路径=" + imageFile.getPath());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
//                        }
//                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public static void loadCoustomIma(Context mContext, String resouce, int maxWidtht, ImageView img) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        if (!((Activity) mContext).isFinishing()) {
            Glide.with(mContext)
                    .asBitmap()
                    .load(resouce)
                    .apply(options)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            int widht = resource.getWidth();
                            int height = resource.getHeight();
                            if (widht > maxWidtht) {
                                float multiple = ((float) widht) / maxWidtht + 0.5f;
                                widht = (int) (widht / multiple);
                                height = (int) (height / multiple);
                            }
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) img.getLayoutParams();
                            params.width = widht;
                            params.height = height;
                            img.setLayoutParams(params);
                            img.setImageBitmap(resource);
                        }
                    });
        }
    }

    /*line  列表（gride 或者瀑布流） 列数*/
    public static void loadWithautoHight(Context context, String url, ImageView ima, int line) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (!((Activity) context).isFinishing()) {
            Glide.with(context).load(url).apply(options).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                    ViewGroup.LayoutParams params = ima.getLayoutParams();
                    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                    params.width = metrics.widthPixels / line; //瀑布流为两列
                    double scale = params.width * 1.0 / resource.getIntrinsicWidth();
                    params.height = (int) (resource.getIntrinsicHeight() * scale);
                    ima.setLayoutParams(params);
                    ima.setImageDrawable(resource);
                }
            });
        }
    }

    /*t以图片宽高比加载*/
    public static void loadImaWithScale(Context context, String url, float scale, int width, int height, ImageView ima) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                ViewGroup.LayoutParams params = ima.getLayoutParams();
                DisplayMetrics metrics = context.getResources().getDisplayMetrics();
                if (width == 0) {//以高度为基准
                    params.height = (int) (height * metrics.density);
                    params.width = (int) ((int) (height * scale) * metrics.density);
                } else {//以宽度为基准
                    params.width = (int) (width * metrics.density);
                    params.height = (int) ((int) (width * scale) * metrics.density);
                }
//                params.width = metrics.widthPixels / line; //瀑布流为两列
//                double scale = params.width * 1.0/resource.getIntrinsicWidth();
//                params.height = (int)(resource.getIntrinsicHeight()*scale);
                ima.setLayoutParams(params);
                ima.setImageDrawable(resource);
            }
        });
    }

    /*图片加载过程回调的方法*/
    public void setLoadingIma(Context context, String url, ImageView ima) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context).load(url).apply(options)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(ima);
    }
}
