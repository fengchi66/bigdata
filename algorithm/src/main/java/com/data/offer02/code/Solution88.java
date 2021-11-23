package com.data.offer02.code;

// 爬楼梯的最小成本
public class Solution88 {

  // dp[i]表示第i级台阶往上爬的最小成本
  public int minCostClimbingStairs(int[] cost) {
    int N = cost.length;
    int[] dp = new int[N];
    dp[0] = cost[0];
    dp[1] = cost[1];

    for (int i = 2; i < N; i++) {
      dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
    }
    return Math.min(dp[N - 1], dp[N - 2]);
  }

}
