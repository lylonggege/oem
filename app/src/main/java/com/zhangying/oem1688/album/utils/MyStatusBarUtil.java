package com.zhangying.oem1688.album.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 状态栏工具类（沉浸式）
 */
public class MyStatusBarUtil {

    public static void setAndroidNativeLightStatusBar(Activity activity, boolean dark) {
        View decor = activity.getWindow().getDecorView();
        if (dark) {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
    /**设置状态栏透明与字体颜色*/
    public static void setStatusBarTrans(Activity activity, boolean lightStatusBar) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        ILightStatusBar IMPL;
        if (MIUILightStatusBarImpl.isMe()) {
            IMPL = new MIUILightStatusBarImpl();
        } else if (MeizuLightStatusBarImpl.isMe()) {
            IMPL = new MeizuLightStatusBarImpl();
        } else {
            IMPL = new ILightStatusBar() {
                @Override
                public void setLightStatusBar(Window window, boolean lightStatusBar) {
                    View decor = activity.getWindow().getDecorView();
                    if (lightStatusBar) {
                        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }
            };
        }
        IMPL.setLightStatusBar(activity.getWindow(), lightStatusBar);
    }

    /**小米状态栏设置类*/
    public static class MIUILightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            return "Xiaomi".equals(Build.MANUFACTURER);
        }

        @Override
        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            Class<? extends Window> clazz = window.getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                int darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                extraFlagField.invoke(window, lightStatusBar ? darkModeFlag : 0, darkModeFlag);
            } catch (Exception e) {
            }
        }
    }

    /**魅族状态栏设置类*/
    public static class MeizuLightStatusBarImpl implements ILightStatusBar {
        static boolean isMe() {
            final Method method;
            try {
                method = Build.class.getMethod("hasSmartBar");
                return method != null;
            } catch (NoSuchMethodException e) {
            }
            return false;
        }

        @Override
        public void setLightStatusBar(Window window, boolean lightStatusBar) {
            WindowManager.LayoutParams params = window.getAttributes();
            try {
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(params);
                if (lightStatusBar) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(params, value);
                window.setAttributes(params);
                darkFlag.setAccessible(false);
                meizuFlags.setAccessible(false);
            } catch (Exception e) {
            }
        }
    }

    interface ILightStatusBar {

        void setLightStatusBar(Window window, boolean lightStatusBar);
    }

    /**
     * 设置状态栏的颜色
     */
    public static void statusBarTintColor(Activity activity, int color) {
        // 代表 5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(activity.getResources().getColor(color));
            return;
        }

        // versionCode > 4.4  and versionCode < 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 在原来的位置上添加一个状态栏
            View statusBarView = createStatusBarView(activity);
            ViewGroup androidContainer = (ViewGroup) activity.getWindow().getDecorView();
            androidContainer = (ViewGroup) androidContainer.getChildAt(0);
            androidContainer.addView(statusBarView, 0);
            statusBarView.setBackgroundColor(color);
        }
    }

    /**
     * 创建一个需要填充statusBarView
     */
    private static View createStatusBarView(Activity activity) {
        View statusBarView = new View(activity);
        ViewGroup.LayoutParams statusBarParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight(activity));
        statusBarView.setLayoutParams(statusBarParams);
        return statusBarView;
    }

    /**
     * 获取状态栏的高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * //设置布局距离状态栏高度
     */
    public static void setLayoutPadding(Activity activity, View contentLayout) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            contentLayout.setPadding(contentLayout.getPaddingLeft(),
                    (getStatusBarHeight(activity) + contentLayout.getPaddingTop()),
                    contentLayout.getPaddingRight(),
                    contentLayout.getPaddingBottom());
        }
    }
    /**
     * 状态栏透明,整个界面全屏
     */
    public static void statusBarTranslucent(Activity activity) {
        // 代表 5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            return;
        }
        // versionCode > 4.4  and versionCode < 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    //适配刘海屏幕
    public static int getStatusBarHeightAccordingToDevice(Context context){

        if (isNotchSupportVersion()) {
            //判断手机厂商：华为、小米、oppo、vivo
            String brand = Build.BRAND.toLowerCase();
            System.out.println("&&手机厂商"+brand);
            if("huawei".equals(brand) || "honor".equals(brand)){
                if (isNotch_HUAWEI(context)){
                    return getNotchSize_HUAWEI(context)[1];
                }
                else {
                    return getStatusBarHeight(context);
                }
            }else if("xiaomi".equals(brand)){
//               if (isNotch_XIAOMI(context)){
//                   System.out.println("小米异形屏幕");
//                    return getNotchSize_XIAOMI(context)[1];
//                }
//                else {
//                    return getStatusBarHeight(context);
//                }
                return getStatusBarHeight(context);
            }else if("vivo".equals(brand)){
                if (isNotch_VIVO(context)){
                    return getNotchHeight(context);
                }
                else {
                    return getStatusBarHeight(context);
                }
            }else if("oppo".equals(brand)){
                if (isNotch_OPPO(context)){
                    return getNotchHeight(context);
                }
                else {
                    return getStatusBarHeight(context);
                }
            }else {
                return getStatusBarHeight(context);
            }
        }
        return  getStatusBarHeight(context);
    }



    /* 判断是否支持异性屏*/
    public static boolean isNotchSupportVersion(){
        int curApiVersion = Build.VERSION.SDK_INT;
        if(curApiVersion > 26){
            return true;
        }
        return false;
    }
    /**
     * xiaomi、huawei、vivo、oppo流行机型异型屏判断工具类
     */

        //检查流行机型是否存在刘海屏
        public static boolean isNotch(Context context){
            return isNotch_VIVO(context) || isNotch_OPPO(context) || isNotch_HUAWEI(context) || isNotch_XIAOMI(context);
        }

        //检查vivo是否存在刘海屏、水滴屏等异型屏
        public static boolean isNotch_VIVO(Context context){
            boolean isNotch = false;
            try {
                ClassLoader cl = context.getClassLoader();
                Class cls = cl.loadClass("android.util.FtFeature");
                Method method = cls.getMethod("isFeatureSupport", int.class);
                isNotch = (boolean) method.invoke(cls,0x00000020);//0x00000020：是否有刘海  0x00000008：是否有圆角
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                return isNotch;
            }
        }

        //检查oppo是否存在刘海屏、水滴屏等异型屏
        public static boolean isNotch_OPPO(Context context){
            boolean isNotch = false;
            try {
                isNotch = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                return isNotch;
            }
        }

        //检查huawei是否存在刘海屏、水滴屏等异型屏
        public static boolean isNotch_HUAWEI(Context context) {
            boolean isNotch = false;
            try {
                ClassLoader cl = context.getClassLoader();
                Class cls = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
                Method method = cls.getMethod("hasNotchInScreen");
                isNotch = (boolean) method.invoke(cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return isNotch;
            }
        }

        //检查xiaomi是否存在刘海屏、水滴屏等异型屏
        public static boolean isNotch_XIAOMI(Context context){
            boolean isNotch = false;
            try {
                ClassLoader cl = context.getClassLoader();
                Class cls = cl.loadClass("android.os.SystemProperties");
                Method method = cls.getMethod("getInt", String.class, int.class);
                isNotch = ((int) method.invoke(null, "ro.miui.notch", 0) == 1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }finally {
                return isNotch;
            }
        }

        //获取huawei刘海屏、水滴屏的宽度和高度：int[0]值为刘海宽度 int[1]值为刘海高度
        public static int[] getNotchSize_HUAWEI(Context context) {
            int[] notchSize = new int[]{0, 0};
            try {
                ClassLoader cl = context.getClassLoader();
                Class cls = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
                Method method = cls.getMethod("getNotchSize");
                notchSize = (int[]) method.invoke(cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return notchSize;
            }
        }

        //获取xiaomi刘海屏、水滴屏的宽度和高度：int[0]值为刘海宽度 int[1]值为刘海高度
        public static int[] getNotchSize_XIAOMI(Context context){
            int[] notchSize = new int[]{0,0};
            if(isNotch_XIAOMI(context)) {
                int resourceWidthId = context.getResources().getIdentifier("notch_width", "dimen", "android");
                if (resourceWidthId > 0) {
                    notchSize[0] = context.getResources().getDimensionPixelSize(resourceWidthId);
                }
                int resourceHeightId = context.getResources().getIdentifier("notch_height", "dimen", "android");
                if (resourceHeightId > 0) {
                    notchSize[1] = context.getResources().getDimensionPixelSize(resourceHeightId);
                }
            }
            return notchSize;
        }

        //获取vivo、oppo刘海屏、水滴屏的高度：官方没有给出标准的获取刘海高度的API，由于大多情况是：状态栏≥刘海，因此此处获取刘海高度采用状态栏高度
        public static int getNotchHeight(Context context){
            int notchHeight = 0;
            if(isNotch_VIVO(context) || isNotch_OPPO(context)) {
                //若不想采用状态栏高度作为刘海高度或者可以采用官方给出的刘海固定高度：vivo刘海固定高度：27dp（need dp2px）  oppo刘海固定高度：80px
                int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    notchHeight = context.getResources().getDimensionPixelSize(resourceId);
                }
            }
            return notchHeight;
        }

        //dp转px
        private int dp2px(Context context, float dpValue){
            float scale=context.getResources().getDisplayMetrics().density;
            return (int)(dpValue*scale+0.5f);
        }

    //获取手机状态栏高度

}
