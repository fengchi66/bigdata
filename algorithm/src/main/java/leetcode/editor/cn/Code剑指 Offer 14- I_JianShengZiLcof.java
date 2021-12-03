//给你一根长度为 n 的绳子，请把绳子剪成整数长度的 m 段（m、n都是整数，n>1并且m>1），每段绳子的长度记为 k[0],k[1]...k[m-1] 。
//请问 k[0]*k[1]*...*k[m-1] 可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18
//。 
//
// 示例 1： 
//
// 输入: 2
//输出: 1
//解释: 2 = 1 + 1, 1 × 1 = 1 
//
// 示例 2: 
//
// 输入: 10
//输出: 36
//解释: 10 = 3 + 3 + 4, 3 × 3 × 4 = 36 
//
// 提示： 
//
// 
// 2 <= n <= 58 
// 
//
// 注意：本题与主站 343 题相同：https://leetcode-cn.com/problems/integer-break/ 
// Related Topics 数学 动态规划 👍 326 👎 0

package leetcode.editor.cn;

// 剑指 Offer 14- I.剪绳子
class Code_Offer_JianShengZiLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int cuttingRope(int n) {
      if (n < 2) {
        return 0;
      }
      if (n == 2) {
        return 1;
      }
      if (n == 3) {
        return 2;
      }
      // dp[n]表示长度为n的绳子剪断后乘积最大值
      int[] dp = new int[n + 1];
      dp[0] = 0;
      dp[1] = 1;
      dp[2] = 2;
      dp[3] = 3;

      int max = 0;
      for (int i = 4; i <= n; i++) {
        // 对每个i而言，从1开始剪，最多剪到一半
        for (int j = 1; j <= i / 2; j++) {
          dp[i] = dp[j] * dp[i - j];
          max = Math.max(max, dp[i]);
        }
        dp[i] = max;
      }
      return max;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}