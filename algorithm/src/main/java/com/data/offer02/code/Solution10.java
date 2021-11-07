package com.data.offer02.code;

import java.util.HashMap;

// 剑指 Offer II 010. 和为 k 的子数组
public class Solution10 {

  public int subarraySum(int[] nums, int k) {
    // 前缀和以及次数
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    // 前缀和
    int sum = 0;
    int count = 0;

    for (int num : nums) {
      sum += num;
      map.put(sum, map.getOrDefault(sum, 0) + 1);
      count += map.getOrDefault(sum - k, 0);
    }
    return count;
  }

}
