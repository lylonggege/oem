package com.zhangying.oem1688.custom;

import android.content.Context;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.zhangying.oem1688.bean.ShareBean;
import com.zhangying.oem1688.onterface.BaseValidateCredentials;
import com.zhangying.oem1688.popu.SharePopu;
import com.zhangying.oem1688.util.StringUtils;

public class ShareRealization implements BaseValidateCredentials {

    public ShareRealization(Context context, ShareBean shareBean) {
        this.context = context;
        if (StringUtils.isEmity(shareBean.getDesc())){
            shareBean.setDesc(shareBean.getTitle());
        }
        this.shareBean = shareBean;
    }

    private ShareBean shareBean;
    private Context context;
    private BasePopupView popView;

    public void realization() {
        SharePopu shareView = new SharePopu(context,shareBean);
        popView = new XPopup.Builder(context)
                .setPopupCallback(new XPopupCallback() {
                    @Override
                    public void onCreated() {

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
                .asCustom(shareView);
        popView.popupInfo.popupAnimation = PopupAnimation.TranslateFromBottom;
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