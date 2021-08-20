package com.zhangying.oem1688.base;

import android.content.res.Configuration;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.xuexiang.xaop.cache.XMemoryCache;
import com.xuexiang.xpage.base.XPageFragment;
import com.xuexiang.xui.utils.WidgetUtils;
import com.xuexiang.xui.widget.actionbar.TitleBar;
import com.xuexiang.xui.widget.progress.loading.IMessageLoader;
import com.zhangying.oem1688.R;
import com.zhangying.oem1688.util.AppManagerUtil;
import com.zhangying.oem1688.util.LoadingView;

public abstract class XBaseFragment extends XPageFragment {
    /**
     * 初始化控件
     */
    protected abstract void initViews();

    /**
     * 消息加载框
     */
    private IMessageLoader mMessageLoader;

    /**
     * 初始化监听
     */
    protected abstract void initListeners();


    @Override
    protected void initPage() {
        initViews();
        initListeners();
    }

    public IMessageLoader getMessageLoader() {
        if (mMessageLoader == null) {
            mMessageLoader = WidgetUtils.getMiniLoadingDialog(getContext());
        }
        return mMessageLoader;
    }

    public IMessageLoader getMessageLoader(String message) {
        if (mMessageLoader == null) {
            mMessageLoader = WidgetUtils.getMiniLoadingDialog(getContext(), message);
        } else {
            mMessageLoader.updateMessage(message);
        }
        return mMessageLoader;
    }

    @Override
    public void onDestroyView() {
        if (mMessageLoader != null) {
            mMessageLoader.dismiss();
        }
        super.onDestroyView();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        //屏幕旋转时刷新一下title
        super.onConfigurationChanged(newConfig);
        XMemoryCache.getInstance().clear();
        ViewGroup root = (ViewGroup) getRootView();
        if (root.getChildAt(0) instanceof TitleBar) {
            root.removeViewAt(0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }





}
