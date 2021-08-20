package com.zhangying.oem1688.util;

import android.app.Activity;

import com.zhangying.oem1688.view.activity.MainActivity;

import java.util.Iterator;
import java.util.Stack;

public class AppManagerUtil {
    private Stack<Activity> activityStack = new Stack<>();
    //返回首页需要存储的activity
    private Stack<Activity> activityStackHome = new Stack<>();

    private AppManagerUtil() {
    }

    private static AppManagerUtil appManagerUtil;

    public static AppManagerUtil getInstance() {
        if (appManagerUtil == null) {
            synchronized (AppManagerUtil.class) {
                if (appManagerUtil == null) {
                    appManagerUtil = new AppManagerUtil();
                }
            }
        }
        return appManagerUtil;
    }

    /*activity添加到栈*/
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }
    /*activity添加到栈*/
    public void addHomeActivity(Activity activity) {
        activityStackHome.add(activity);
    }

    /*获取当前activity*/
    public Activity getCurrentActivity() {
        Activity activity = activityStack.lastElement();
        return activity;
    }

    /*结束制定的activity*/
    public void finishActivity(Activity activity) {
        if (activityStack.contains(activity) && activity != null) {
            activityStack.remove(activity);
            activity.finish();
        }
    }

    /*结束制定的activity*/
    public void finishhomeActivity(Activity activity) {
        if (activityStackHome.contains(activity) && activity != null) {
            activityStackHome.remove(activity);
            activity.finish();
        }
    }

    /*结束其他指定类名的activity*/
    public void finishActivity(Class<?> clazz) {
        Iterator<Activity> iterator = activityStack.iterator();
        if (iterator.hasNext()) {
            Activity next = iterator.next();
            if (!next.getClass().equals(clazz)) {
                finishActivity(next);
            }
        }
    }

    /*结束所有的activity*/
    public void finishAllActivity() {
        for (Activity activity : activityStack) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStack.clear();
    }

    /*结束所有的Homeactivity*/
    public void finishAllHomeActivity() {
        for (Activity activity : activityStackHome) {
            if (activity != null) {
                activity.finish();
            }
        }
        activityStackHome.clear();
    }
}
