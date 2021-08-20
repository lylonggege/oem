package com.zhangying.oem1688.util;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapRepeatHasMap<K, V>  extends HashMap<K,V> {
    @Nullable
    @Override
    public V put(K key, V value) {
        V newV = value;
        List<V> list = new ArrayList<>();
        //   containsKey - -- 判断是否包含指定的键名
        if(containsKey(key)){
            List v = (List)get(key);
            v.add(value);
            list = v;
        }else {
            list.add(newV);
        }
        return super.put(key,(V)list);
    }
}
