//给定一个数组 prices ，它的第 i 个元素 prices[i] 表示一支给定股票第 i 天的价格。 
//
// 你只能选择 某一天 买入这只股票，并选择在 未来的某一个不同的日子 卖出该股票。设计一个算法来计算你所能获取的最大利润。 
//
// 返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 0 。 
//
// 
//
// 示例 1： 
//
// 
//输入：[7,1,5,3,6,4]
//输出：5
//解释：在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
//     注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
// 
//
// 示例 2： 
//
// 
//输入：prices = [7,6,4,3,1]
//输出：0
//解释：在这种情况下, 没有交易完成, 所以最大利润为 0。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= prices.length <= 10⁵ 
// 0 <= prices[i] <= 10⁴ 
// 
// Related Topics 数组 动态规划 👍 2085 👎 0

package leetcode.editor.cn;

// 121.买卖股票的最佳时机
public class Code121_BestTimeToBuyAndSellStock {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 由于股票只会有一次买卖，想求第i天的最大收益只需要知道第i天之前股票的最低价格就行了
    public int maxProfit(int[] prices) {
      // dp[i]表示在0到i天股票的最低价格
      int n = prices.length;
      int[] dp = new int[n];
      dp[0] = prices[0];

      int ans = 0;

      for (int i = 1; i < n; i++) {
        dp[i] = Math.min(dp[i - 1], prices[i]);
        ans = Math.max(ans, prices[i] - dp[i]);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}