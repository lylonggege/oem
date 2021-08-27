package com.zhangying.oem1688.util;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MyUtilsWebView {
    private final static Pattern ATTR_PATTERN = Pattern.compile("<img[^<>]*?\\ssrc=['\"]?(.*?)['\"]?\\s.*?>", Pattern.CASE_INSENSITIVE);
    //替换body里面的图片路径问题
    public static String getAbsSource(String source, String bigpath) {
        Matcher matcher = ATTR_PATTERN.matcher(source);
        List<String> list = new ArrayList<String>();  // 装载了匹配整个的Tag
        List<String> list2 = new ArrayList<String>(); // 装载了src属性的内容
        while (matcher.find()) {
            list.add(matcher.group(0));
            list2.add(matcher.group(1));
        }
        StringBuilder sb = new StringBuilder();
        sb.append(source.split("<img")[0]); // 连接<img之前的内容
        for (int i = 0; i < list.size(); i++) { // 遍历list
            sb.append(list.get(i).replace(list2.get(i), // 对每一个Tag进行替换
                    bigpath + list2.get(i).substring(1)));
        }
        sb.append(source.split("(?:<img[^<>]*?\\s.*?['\"]?\\s.*?>)+")[1]);
        return sb.toString();
    }



    public static String setWebViewText(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        sb.insert(0, "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" http-equiv=\"content-type\" content=\"text/html\" />\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1,\n" +
                "    user-scalable=no, width=device-width\"/>\n" +
                "    <style>" +
                "       body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{margin:0;padding:0;font-size:15px;color:#333333;}" +
                "       p{\n" +
                "            word-wrap : break-word;\n" +
                "            max-height: none!important;\n" +
                "        }\n" +
                "       img{\n" +
                "           max-width: 100%!important;\n" +
                "           display: block;\n" +
                "           height: auto!important;\n" +
                "           margin: 0 auto!important;\n" +
                "       }\n" +
                "       iframe{\n" +
                "           max-width: 100%!important;\n" +
                "           margin: 0 auto!important;\n" +
                "       }\n" +
                "       video{\n" +
                "           max-width:100%;\n" +
                "           position: absolute;\n" +
                "           z-index: -1;\n" +
                "           opacity: 0;\n" +
                "       }\n" +
                "       section,div{\n" +
                "          max-width:100%!important;\n" +
                //"          text-align: justify;\n" +
                //"          text-justify: inter-ideograph;\n" +
                "        }\n" +
//                "        p,section,div{\n" +
//                "            line-height: " + lineHeight + "rem !important;\n" +
//                "        }" +
                "        video::-internal-media-controls-download-button { \n" +
                "display:none; \n" +
                "}\n" +
                "\n" +
                "video::-webkit-media-controls-enclosure { \n" +
                "overflow:hidden; \n" +
                "}\n" +
                "\n" +
                "video::-webkit-media-controls-panel { \n" +
                "width: calc(100%); \n" +
                "}\n" +
                ".content{width: 100%;overflow: hidden;text-align: justify;text-justify: inter-ideograph; padding-top: 0px;}\n" +
                "    </style>\n" +
                "    \n" +
                "    <script type=\"text/javascript\" src=\"file:///android_asset/my.js\" ></script>\n" +
                "</head>\n" +
                "<body>\n" +
                //"    <div style=\"width: 100%;overflow: hidden;\">");
                "    <div class='content'>");

        sb.append("</div>\n" +
                "</body>\n"+
                "</html>");
        return sb.toString();
    }
    public static String setWebViewText2(String content) {
        StringBuilder sb = new StringBuilder();
        sb.append(content);
        sb.insert(0, "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\" http-equiv=\"content-type\" content=\"text/html\" />\n" +
                "    <meta name=\"viewport\" content=\"initial-scale=1, maximum-scale=1,\n" +
                "    user-scalable=no, width=device-width\"/>\n" +
                "    <style>" +
                "       body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td,hr,button,article,aside,details,figcaption,figure,footer,header,hgroup,menu,nav,section{margin:0;padding:0}" +
                "       p{\n" +
                "            word-wrap : break-word;\n" +
                "            max-height: none!important;\n" +
                "        }\n" +
                "       img{\n" +
                "           max-width: 100%!important;\n" +
                "           display: block;\n" +
                "           height: auto!important;\n" +
                "           margin: 0 auto!important;\n" +
                "       }\n" +
                "       iframe{\n" +
                "           max-width: 100%!important;\n" +
                "           margin: 0 auto!important;\n" +
                "       }\n" +
                "       video{\n" +
                "           max-width:100%;\n" +
                "           position: absolute;\n" +
                "           z-index: -1;\n" +
                "           opacity: 0;\n" +
                "       }\n" +
                "       section,div{\n" +
                "          max-width:100%!important;\n" +
                //"          text-align: justify;\n" +
                //"          text-justify: inter-ideograph;\n" +
                "        }\n" +
//                "        p,section,div{\n" +
//                "            line-height: " + lineHeight + "rem !important;\n" +
//                "        }" +
                "        video::-internal-media-controls-download-button { \n" +
                "display:none; \n" +
                "}\n" +
                "\n" +
                "video::-webkit-media-controls-enclosure { \n" +
                "overflow:hidden; \n" +
                "}\n" +
                "\n" +
                "video::-webkit-media-controls-panel { \n" +
                "width: calc(100%); \n" +
                "}\n" +
                ".content{width: 100%;overflow: hidden;text-align: justify;text-justify: inter-ideograph; padding-top: 0px;}\n" +
                "    </style>\n" +
                "    \n" +
                "    <script type=\"text/javascript\" src=\"file:///android_asset/my.js\" ></script>\n" +
                "</head>\n" +
                "<body>\n" +
                //"    <div style=\"width: 100%;overflow: hidden;\">");
                "    <div class='content'>");

        sb.append("</div>\n" +
                "</body>\n"+
                "</html>");
        return sb.toString();
    }

    /**
     * 根据手机分辨率从DP转成PX
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 获取控件的高度或者宽度  isHeight=true则为测量该控件的高度，isHeight=false则为测量该控件的宽度
     * @param view
     * @param isHeight
     * @return
     */
    public static int getViewHeight(View view, boolean isHeight){
        int result;
        if(view==null)return 0;
        if(isHeight){
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(h,0);
            result =view.getMeasuredHeight();
        }else{
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(0,w);
            result =view.getMeasuredWidth();
        }
        return result;
    }

}
