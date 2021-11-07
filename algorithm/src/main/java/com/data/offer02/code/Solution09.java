package com.data.offer02.code;

// 剑指 Offer II 009. 乘积小于 K 的子数组个数
public class Solution09 {

  // 双指针
  public int numSubarrayProductLessThanK(int[] nums, int k) {
    int p1 = 0;
    int sum = 1;
    int count = 0;

    for (int p2 = 0; p2 < nums.length; p2++) {
      sum *= nums[p2];
      while (p1 <= p2 && sum >= k) {
        sum /= nums[p1++];
      }
      count += p2 - p1 + 1;
    }
    return count;
  }

}
