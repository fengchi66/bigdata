package com.data.offer02.code;

import java.util.HashMap;

// 剑指 Offer II 011. 0 和 1 个数相同的子数组
public class Solution11 {

  // 求最长连续子数组的长度
  // 前缀和：遇到1加1，遇到0就-1，若当前i前缀和为m，若之前存在一个位置j前缀和也是m，则长度为i - j，
  // 哈希表中应该存数组前缀和以及索引位置
  public int findMaxLength(int[] nums) {
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);

    int sum = 0;
    int size = 0;

    for (int i = 0; i < nums.length; i++) {
      sum += nums[i] == 1 ? 1 : -1;
      size = Math.max(size, i - map.getOrDefault(sum, i));
      map.put(sum, Math.min(map.getOrDefault(sum, i), i));
    }
    return size;
  }

  public int findMaxLength2(int[] nums) {
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, -1);

    int sum = 0;
    int size = 0;

    for (int i = 0; i < nums.length; i++) {
      sum += nums[i] == 1 ? 1 : -1;
      if (map.containsKey(sum)) {
        size = Math.max(size, i - map.get(sum));
      } else {
        map.put(sum, i);
      }
    }
    return size;
  }

}
