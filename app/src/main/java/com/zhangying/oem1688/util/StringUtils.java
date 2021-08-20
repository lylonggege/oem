package com.zhangying.oem1688.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    int counter = 0;

    public static boolean isEmity(String string) {
        return string == null || string.equals("");
    }

    /*是否是正确的手机格式*/
    public static boolean isMobileNum(String string) {
        String regex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
        if (string.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(string);
            return m.matches();
        }
    }

    /**
     * 判断str1中包含str2的个数
     * @param str1
     * @param str2
     * @return counter
     */
    public  int countStr(String str1, String str2) {

        if (str1.indexOf(str2) == -1) {
            return 0;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            countStr(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2);
            return counter;
        }
        return 0;
    }


    /*判断字符串是否是数字*/
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /*网址链接*/
    public static Matcher getUrl(String str){
        String space = "([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://|[wW]{3}.|[wW][aA][pP].|[fF][tT][pP].|[fF][iI][lL][eE].)[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";
        Pattern compile = Pattern.compile(space);
        Matcher matcher = compile.matcher(str);
        return matcher;
    }
}
