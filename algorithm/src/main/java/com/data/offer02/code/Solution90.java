package com.data.offer02.code;

// 环形房屋偷盗
// 转换为两个线性结构
public class Solution90 {

  public int rob(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    int n = nums.length;
    if (n == 1) {
      return nums[0];
    }

    // 至少两个房间
    // dp[i]表示偷到第i号房间最大能偷到的钱
    int[] dp1 = new int[n]; // 偷第一间，不能偷第二间以及最后一间
    int[] dp2 = new int[n]; // 不偷第一间，可以偷最后一间
    dp1[0] = nums[0];
    dp1[1] = nums[0];
    dp2[1] = nums[1];

    for (int i = 2; i < n; i++) {
      dp1[i] = Math.max(dp1[i - 2] + nums[i], dp1[i - 1]);
      dp2[i] = Math.max(dp2[i - 2] + nums[i], dp2[i - 1]);
    }
    return Math.max(dp1[n - 2], dp2[n - 1]);
  }

}
