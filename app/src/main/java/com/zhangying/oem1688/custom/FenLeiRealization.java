package com.zhangying.oem1688.custom;

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

    public FenLeiRealization(FragmentActivity fragmentActivity, BaseView baseView) {
        this.fragmentActivity = fragmentActivity;
        this.mbaseView = baseView;
    }
    private BaseView mbaseView;
    private FragmentActivity fragmentActivity;
    private PinLeiPopu pinLeiView;

    public void realization() {
        mbaseView.showloading();
        pinLeiView = new PinLeiPopu(fragmentActivity, null);
        BasePopupView popView = new XPopup.Builder(fragmentActivity)
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
                                        mbaseView.hidenloading();
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
        mbaseView.showloading();
        realization();
    }
}
