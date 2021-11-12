package com.data.offer02.demo;

import java.util.HashMap;

// 1248. 统计「优美子数组」
public class Solution1248 {

  public int numberOfSubarrays(int[] nums, int k) {
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    int sum = 0;
    int count = 0;

    for (int num : nums) {
      sum += num % 2 == 0 ? 0 : 1;
      count += map.getOrDefault(sum - k, 0);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }

    return count;
  }

}
