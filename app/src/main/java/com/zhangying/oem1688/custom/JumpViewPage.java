package com.zhangying.oem1688.custom;

import android.content.Context;

import com.xuexiang.xutil.app.ActivityUtils;
import com.zhangying.oem1688.bean.EvenBusBean;
import com.zhangying.oem1688.onterface.IJumPage;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.view.activity.MainActivity;
import com.zhangying.oem1688.view.activity.home.FactoryDetailActivity;
import com.zhangying.oem1688.view.activity.home.GoodsDetailActivity;
import com.zhangying.oem1688.view.activity.home.NewProductFactoryActivity;

import org.greenrobot.eventbus.EventBus;

/**
 * 跳转到各个页面
 */
public class JumpViewPage {
    /**
     * @param context
     * @param type    2：产品详情；3：店铺详情；4：新闻详情；5：需求详情；6：工厂列表；7：产品列表；8：发布承接代工；9：发布帮忙找工厂；10：其他.
     */
    public void intentActivity(Context context, int type, String id, String name) {
        switch (type) {
            case 1:
                break;
            case 2:
                GoodsDetailActivity.simpleActivity(context, id);
                break;
            case 3:
                FactoryDetailActivity.simpleActivity(context, id);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }
    }

    public void intentActivity(Context context, int type, String id, String name,String tilte) {
        switch (type) {
            case 1:
                NewProductFactoryActivity.simpleActivity(context, id, 1, name,tilte);
                break;
            case 2:
                GoodsDetailActivity.simpleActivity(context, id);
                break;
            case 3:
                FactoryDetailActivity.simpleActivity(context, id);
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
        }
    }


}
