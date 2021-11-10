package com.data.offer02.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// 插入、删除和随机访问都是 O(1) 的容器
public class Solution30 {
    // map中存数字和数字在数组中的索引
    HashMap<Integer, Integer> numToIndex;
    // 数组存数字
    ArrayList<Integer> num;

    public Solution30() {
        numToIndex = new HashMap<>();
        num = new ArrayList<>();
    }

    // map和数组中都要插入
    public boolean insert(int val) {
        if (numToIndex.containsKey(val))
            return false;

        numToIndex.put(val, num.size());
        num.add(val);
        return true;
    }

    // map和数组中都要删除
    public boolean remove(int val) {
        if (!num.contains(val))
            return false;

        // 该数字在数组中的位置
        int index = numToIndex.get(val);
        // 不直接删除，先将index位置和数组最后一个位置交换
        numToIndex.put(num.get(num.size() - 1), index);
        numToIndex.remove(val);

        // 数组：将最后一个元素赋值到index位置，相当于把index位置删除
        num.set(index, num.get(num.size() - 1));
        num.remove(num.size() - 1);
        return true;
    }

    // 随机从数组中取出一个元素
    public int getRandom() {
        int i = new Random().nextInt(num.size());
        return num.get(i);
    }
}
