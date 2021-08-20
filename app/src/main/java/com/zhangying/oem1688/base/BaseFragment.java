package com.zhangying.oem1688.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.zhangying.oem1688.R;
import com.zhangying.oem1688.singleton.HashMapSingleton;
import com.zhangying.oem1688.util.LoadingView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.jessyan.autosize.AutoSizeConfig;

public  abstract  class BaseFragment   extends Fragment {

    public View inflate;
    private Unbinder bind;
    public Context context;
    protected LoadingView loadingView;


    protected abstract int getLayoutId();

    public abstract void initView();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        AutoSizeConfig.getInstance().setCustomFragment(true);
        //清空hashmap里面的数据
        HashMapSingleton.getInstance().clear();
        if (inflate != null) {//解决第二次加载空白问题
            ViewGroup parent = (ViewGroup) inflate.getParent();
            if (parent != null) {
                parent.removeView(inflate);
            }
            return inflate;
        }
        inflate = inflater.inflate(getLayoutId(), null);
        bind = ButterKnife.bind(this, inflate);
        initView();


        return inflate;
    }

    protected void showLoading() {
        if (loadingView == null) {
//            progressDialog = new ProgressDialog(context);
            loadingView = new LoadingView(context, R.style.custom_dialog2);
        }
        loadingView.show();
    }


    protected void dissmissLoading() {
        if (loadingView != null && loadingView.isShowing()) {
            loadingView.dismiss();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        bind.unbind();

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            if (inflate != null) {
                ((ViewGroup) inflate.getParent()).removeView(inflate);
            }
        }catch (Exception e){

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        context = null;
    }
}
