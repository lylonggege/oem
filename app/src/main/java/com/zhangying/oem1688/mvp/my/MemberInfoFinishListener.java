package com.zhangying.oem1688.mvp.my;

import com.zhangying.oem1688.bean.MemberInfoBean;
import com.zhangying.oem1688.bean.MineinfoBean;

public interface MemberInfoFinishListener {
    void success(MineinfoBean memberInfoBean);
    void hidendloading();
}
