package com.data.offer02.demo;

import java.util.HashMap;

/**
 * 和为k的子数组
 */
public class Solution560 {

  /**
   * 思路： 1. 遍历数组，将前缀和以及次数存下来， 2. 当遍历到i位置时，此时前缀和为sum，看是否存在前缀和为sum - k，如果存在，则存在和为k的子数组
   */

  public int subarraySum(int[] nums, int k) {
    // 存前缀和以及次数
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    int sum = 0;
    int count = 0;

    for (int num : nums) {
      sum += num; // 前缀和
      // 是否存在前缀和为sum - k
      count += map.getOrDefault(sum - k, 0);
      // 更新前缀和以及次数
      map.put(sum, map.getOrDefault(sum, 0) + 1);

    }
    return count;
  }

}
