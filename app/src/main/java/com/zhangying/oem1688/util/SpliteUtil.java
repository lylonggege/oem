package com.zhangying.oem1688.util;

import android.text.TextUtils;

public class SpliteUtil {


    public static boolean isMobileNO(String mobileNums) {
            /**
            * 判断字符串是否符合手机号码格式
            * 移动号段: 134,135,136,137,138,139,147,150,151,152,157,158,159,170,178,182,183,184,187,188
            * 联通号段: 130,131,132,145,155,156,170,171,175,176,185,186,166
            * 电信号段: 133,149,153,170,172,173,177,180,181,189
            * @param str
            * @return 待检测的字符串
            */
        String telRegex = "^((13[0-9])|(19[0-9])|(14[0-9])|(15[0-9])|(18[0-9])|(17[0-9])|(16[0-9]))\\d{8}$";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
    }
}
