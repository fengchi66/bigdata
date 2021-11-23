package com.data.offer02.code;

// 房屋偷盗
public class Solution89 {

  // 不能挨着偷
  public int rob(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    int n = nums.length;
    // dp[i]表示偷到i位置时所能偷到的最多的钱
    int[] dp = new int[n];
    dp[0] = nums[0];
    if (n > 1) {
      dp[1] = Math.max(nums[0], nums[1]); // 偷0位置或者1位置
    }

    for (int i = 2; i < n; i++) {
      dp[i] = Math.max(dp[i - 2] + nums[i], dp[i - 1]); // 偷当前位置或者不偷
    }
    return dp[n - 1];
  }
}
