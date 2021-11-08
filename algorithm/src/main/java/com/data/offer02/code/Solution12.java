package com.data.offer02.code;

// 左右两边子数组和相等
public class Solution12 {

  public int pivotIndex(int[] nums) {
    int total = 0;
    for (int num : nums) {
      total += num;
    }

    // 前缀和
    int sum = 0;
    for (int i = 0; i < nums.length; i++) {
      sum += nums[i];
      // 如果在当前位置，sum - nums[i] == total - sum，则说明左右两边的子数组和相等
      if (sum - nums[i] == total - sum)
        return i;
    }
    return -1;
  }

}
