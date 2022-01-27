//给你一个整数数组 coins ，表示不同面额的硬币；以及一个整数 amount ，表示总金额。 
//
// 计算并返回可以凑成总金额所需的 最少的硬币个数 。如果没有任何一种硬币组合能组成总金额，返回 -1 。 
//
// 你可以认为每种硬币的数量是无限的。 
//
// 
//
// 示例 1： 
//
// 
//输入：coins = [1, 2, 5], amount = 11
//输出：3 
//解释：11 = 5 + 5 + 1 
//
// 示例 2： 
//
// 
//输入：coins = [2], amount = 3
//输出：-1 
//
// 示例 3： 
//
// 
//输入：coins = [1], amount = 0
//输出：0
// 
//
// 示例 4： 
//
// 
//输入：coins = [1], amount = 1
//输出：1
// 
//
// 示例 5： 
//
// 
//输入：coins = [1], amount = 2
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= coins.length <= 12 
// 1 <= coins[i] <= 2³¹ - 1 
// 0 <= amount <= 10⁴ 
// 
// Related Topics 广度优先搜索 数组 动态规划 👍 1681 👎 0

package leetcode.editor.cn;

// 322.零钱兑换
public class Code322_CoinChange {

  private int coinChange(int[] coins, int amount) {
    if (coins == null || coins.length == 0) {
      return 0;
    }

    return dfs(coins, 0, amount);
  }

  public static void main(String[] args) {

    int[] coins = {1, 2, 5};

    Code322_CoinChange change = new Code322_CoinChange();
    int i = change.coinChange2(coins, 51000);
    System.out.println(i);
  }

  // 从index位置开始选择，刚好凑齐rest这么多钱的硬币个数
  private int dfs(int[] coins, int index, int rest) {
    if (rest < 0) {
      return Integer.MAX_VALUE;
    }
    if (index == coins.length) {
      return rest == 0 ? 0 : Integer.MAX_VALUE;
    }

    int ans = Integer.MAX_VALUE;
    // 常规情况下，每个硬币可以选0个或多个，但不能超过rest
    for (int i = 0; i * coins[index] <= rest; i++) {
      int next = dfs(coins, index + 1, rest - i * coins[index]);
      if (next != Integer.MAX_VALUE) {
        ans = Math.min(ans, next + i);
      }
    }
    return ans;
  }

  public int coinChange2(int[] coins, int amount) {
    if (coins == null || coins.length == 0) {
      return 0;
    }

    int len = coins.length;
    // dp[i][j]表示从i位置开始，组成j的最小硬币数,返回dp[0][amount]
    int[][] dp = new int[len + 1][amount + 1];
    // base case
    dp[len][0] = 0;
    for (int j = 1; j <= amount; j++) {
      dp[len][j] = Integer.MAX_VALUE;
    }

    for (int i = len - 1; i >= 0; i--) {
      for (int j = 0; j <= amount; j++) {
        // 每种硬币可以选0个或者多个，刚好凑齐j的硬币数
        int ans = Integer.MAX_VALUE;
        for (int k = 0; k * coins[i] <= j; k++) {
          int next = dp[i + 1][j - k * coins[i]];
          if (next != Integer.MAX_VALUE) {
            ans = Math.min(ans, next + k);
          }
          dp[i][j] = ans;
        }
      }
    }
    return dp[0][amount];
  }


  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int coinChange(int[] coins, int amount) {
      if (coins == null || coins.length == 0) {
        return 0;
      }

      int len = coins.length;
      // dp[i][j]表示从i位置开始，组成j的最小硬币数,返回dp[0][amount]
      int[][] dp = new int[len + 1][amount + 1];
      // base case
      dp[len][0] = 0;
      for (int j = 1; j <= amount; j++) {
        dp[len][j] = Integer.MAX_VALUE;
      }

      for (int i = len - 1; i >= 0; i--) {
        for (int j = 0; j <= amount; j++) {
          // 每种硬币可以选0个或者多个，刚好凑齐j的硬币数
          int ans = Integer.MAX_VALUE;
          for (int k = 0; k * coins[i] <= j; k++) {
            int next = dp[i + 1][j - k * coins[i]];
            if (next != Integer.MAX_VALUE) {
              ans = Math.min(ans, next + k);
            }
            dp[i][j] = ans;
          }
        }
      }
      return dp[0][amount];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}