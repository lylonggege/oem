package com.zhangying.oem1688.singleton;

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

}
