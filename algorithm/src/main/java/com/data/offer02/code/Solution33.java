package com.data.offer02.code;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 变位词组
public class Solution33 {

    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            // 排序
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            String s = new String(chars);

            // 排序后的字符串作为key，将List存入map
            map.putIfAbsent(s, new ArrayList<String>());
            // 将该key的value增加元素
            map.get(s).add(str);
        }
        return new ArrayList<>(map.values());
    }
}
