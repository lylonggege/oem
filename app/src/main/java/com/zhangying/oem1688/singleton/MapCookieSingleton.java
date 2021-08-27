package com.zhangying.oem1688.singleton;

import java.util.HashSet;

public class MapCookieSingleton extends HashSet {

    private static class LazyCookieHoler {
        private static final MapCookieSingleton instance = new MapCookieSingleton();
    }

    public static final MapCookieSingleton getInstance() {
        return LazyCookieHoler.instance;
    }

    public MapCookieSingleton() {

    }

}
