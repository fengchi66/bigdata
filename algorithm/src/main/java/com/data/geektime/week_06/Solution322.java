package com.data.geektime.week_06;

import java.util.Arrays;

public class Solution322 {

  public int coinChange(int[] coins, int amount) {
    int INF = (int) 1e9;
    // opt[n]表示兑换面值为所需要的最小硬币数
    int[] opt = new int[amount + 1];
    opt[0] = 0;

    // 算0到amount之间的每一个i
    for (int i = 1; i <= amount; i++) {
      opt[i] = INF;
      // 枚举coins的面额
      for (int j = 0; j < coins.length; j++) {
        if (i - coins[j] >= 0) {
          // 凑齐i需要的硬币数是opt[i]与 i与coins[j] +1的最小值
          opt[i] = Math.min(opt[i], opt[i - coins[j]] + 1);
        }

        if (opt[amount] >= INF) {
          opt[amount] = -1;
        }
      }
    }
    return opt[amount];
  }

  // dp
  public int coinChange2(int[] coins, int amount) {
    int max = amount + 1;
    // dp[i]表示兑换i金额需要的硬币数
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, max);
    // base case
    dp[0] = 0;
    for (int i = 1; i <= amount; i++) {
      for (int j = 0; j < coins.length; j++) {
        if (coins[j] <= i) {
          // 状态转移方程
          dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
        }
      }
    }
    return dp[amount] > amount ? -1 : dp[amount];
  }
}
