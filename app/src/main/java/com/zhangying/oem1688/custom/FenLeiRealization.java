package com.zhangying.oem1688.custom;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.zhangying.oem1688.bean.SitetopinfoBean;
import com.zhangying.oem1688.internet.DefaultDisposableSubscriber;
import com.zhangying.oem1688.internet.RemoteRepository;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.onterface.BaseView;
import com.zhangying.oem1688.popu.PinLeiPopu;

public class FenLeiRealization implements BaseValidateCredentials {

    public FenLeiRealization(Context fragmentActivity, BaseView baseView) {
        this.fragmentActivity = fragmentActivity;
        this.mbaseView = baseView;
    }
    private BaseView mbaseView;
    private Context fragmentActivity;
    private PinLeiPopu pinLeiView;
    BasePopupView popView;

    public void realization() {
        pinLeiView = new PinLeiPopu(fragmentActivity, null);
        pinLeiView.setParentView(mbaseView);
        popView = new XPopup.Builder(fragmentActivity)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onCreated() {
                        RemoteRepository.getInstance()
                                .sitetopinfo()
                                .subscribeWith(new DefaultDisposableSubscriber<SitetopinfoBean>() {

                                    @Override
                                    protected void success(SitetopinfoBean sitetopinfoBean) {
                                        mbaseView.hidenloading();
                                        if (sitetopinfoBean == null) {
                                            return;
                                        }

                                        pinLeiView.refreshData(sitetopinfoBean);
                                    }

                                    @Override
                                    public void onError(Throwable t) {
                                        super.onError(t);
                                    }
                                });
                    }

                    @Override
                    public void beforeShow() {

                    }

                    @Override
                    public void onShow() {

                    }

                    @Override
                    public void onDismiss() {

                    }

                    @Override
                    public boolean onBackPressed() {
                        return false;
                    }
                })
                .dismissOnTouchOutside(true)
                .asCustom(pinLeiView);
        popView.popupInfo.popupAnimation = PopupAnimation.TranslateFromTop;
        popView.show();
    }

    @Override
    public void validateCredentials() {
        if (popView != null){
            popView.show();
            return;
        }

        realization();
    }
}