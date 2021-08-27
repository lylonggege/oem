package com.zhangying.oem1688.singleton;

import com.zhangying.oem1688.util.MD5Util;
import com.zhangying.oem1688.util.StringUtils;
import com.zhangying.oem1688.util.TokenUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapSingleton extends HashMap {

    private static class LazyHoler {
        private static final HashMapSingleton instance = new HashMapSingleton();
    }

    public static final HashMapSingleton getInstance() {
        return LazyHoler.instance;
    }

    public HashMapSingleton() {

    }

    /**
     * 删除所有元素,除token、timestamp、sign
     */
    public void reload(){
        long timestamp = System.currentTimeMillis() / 1000;
        String token = TokenUtils.getToken();
        String tokenKey = "token";
        if (!this.containsKey(tokenKey) && !StringUtils.isEmity(token)){
            this.put(tokenKey, token);
            this.put("timestamp", timestamp);

            String url = timestamp + token + "&^%$RSTUih09135ZST)(*";
            System.out.println("timestamp::" + timestamp);
            System.out.println("token::" + timestamp);
            System.out.println("timestamp::" + timestamp);
            String md5Str = MD5Util.getMD5Str(url);
            this.put("sign", md5Str);
        }

        if (!this.containsKey("ly")){
            this.put("ly", "app");
        }

        ArrayList<String> keyList = new ArrayList(this.keySet());
        String mapKey = "";
        for (int i = 0; i < keyList.size(); i++) {
            mapKey = keyList.get(i);
            if (!tokenKey.equals(mapKey) && !"timestamp".equals(mapKey) && !"sign".equals(mapKey) && !"ly".equals(mapKey)){
                this.remove(mapKey);
            }
        }
    }

}
