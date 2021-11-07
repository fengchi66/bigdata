package com.data.offer02.code;

// 剑指 Offer II 008. 和大于等于 target 的最短子数组
public class Solution08 {

  // 同向双指针p1、p2之间始终维持sum >= target
  public int minSubArrayLen(int target, int[] nums) {
    int p1 = 0;
    int sum = 0;
    int size = Integer.MAX_VALUE;

    for (int p2 = 0; p2 < nums.length; p2++) {
      sum += nums[p2];
      while (p1 <= p2 && sum >= target) {
        size = Math.min(size, p2 - p1 + 1);
        sum -= nums[p1++];
      }
    }
    return size == Integer.MAX_VALUE ? 0 : size;
  }


}
