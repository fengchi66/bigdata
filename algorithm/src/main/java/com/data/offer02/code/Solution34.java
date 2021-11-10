package com.data.offer02.code;

import java.util.HashMap;

// 外星语言是否排序 TODO
public class Solution34 {
    public boolean isAlienSorted(String[] words, String order) {
        // 用一个哈希表来记录排序字母表字母及其顺序
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < order.length(); i++) {
            map.put(order.charAt(i), i);
        }
        return true;
    }

}
