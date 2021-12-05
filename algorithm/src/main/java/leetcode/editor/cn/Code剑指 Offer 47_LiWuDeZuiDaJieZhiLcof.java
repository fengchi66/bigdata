//在一个 m*n 的棋盘的每一格都放有一个礼物，每个礼物都有一定的价值（价值大于 0）。你可以从棋盘的左上角开始拿格子里的礼物，并每次向右或者向下移动一格、直
//到到达棋盘的右下角。给定一个棋盘及其上面的礼物的价值，请计算你最多能拿到多少价值的礼物？ 
//
// 
//
// 示例 1: 
//
// 输入: 
//[
//  [1,3,1],
//  [1,5,1],
//  [4,2,1]
//]
//输出: 12
//解释: 路径 1→3→5→2→1 可以拿到最多价值的礼物 
//
// 
//
// 提示： 
//
// 
// 0 < grid.length <= 200 
// 0 < grid[0].length <= 200 
// 
// Related Topics 数组 动态规划 矩阵 👍 217 👎 0

package leetcode.editor.cn;

// 剑指 Offer 47.礼物的最大价值
class Code_Offer_LiWuDeZuiDaJieZhiLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxValue(int[][] grid) {
      if (grid == null || grid[0].length == 0) {
        return 0;
      }

      int m = grid.length; // 行
      int n = grid[0].length; // 列

      // dp[i][j]表示从左上角开始到[i,j]位置的礼物最大值
      int[][] dp = new int[m][n];
      dp[0][0] = grid[0][0];

      for (int i = 1; i < m; i++) {
        dp[i][0] = dp[i - 1][0] + grid[i][0];
      }

      for (int i = 1; i < n; i++) {
        dp[0][i] = dp[0][i - 1] + grid[0][i];
      }

      for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
          dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
        }
      }
      return dp[m-1][n-1];

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}