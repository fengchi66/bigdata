//一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。 
//
// 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。 
//
// 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？ 
//
// 网格中的障碍物和空位置分别用 1 和 0 来表示。 
//
// 
//
// 示例 1： 
//
// 
//输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
//输出：2
//解释：3x3 网格的正中间有一个障碍物。
//从左上角到右下角一共有 2 条不同的路径：
//1. 向右 -> 向右 -> 向下 -> 向下
//2. 向下 -> 向下 -> 向右 -> 向右
// 
//
// 示例 2： 
//
// 
//输入：obstacleGrid = [[0,1],[0,0]]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// m == obstacleGrid.length 
// n == obstacleGrid[i].length 
// 1 <= m, n <= 100 
// obstacleGrid[i][j] 为 0 或 1 
// 
// Related Topics 数组 动态规划 矩阵 👍 717 👎 0

package leetcode.editor.cn;

// 63.不同路径 II
public class Code63_UniquePathsIi {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
      if (obstacleGrid == null || obstacleGrid.length == 0) {
        return 0;
      }

      int m = obstacleGrid.length; // 行
      int n = obstacleGrid[0].length; // 列

      // int[m][n]表示机器人从左上角到[m][n]位置的路径数
      int[][] dp = new int[m][n];

      // dp计算
      for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
          // 如果遇到障碍物，那么路径为0
          if (obstacleGrid[i][j] == 1) dp[i][j] = 0;
          // base case,起始位置
          else if (i == 0 && j ==0) dp[i][j] = 1;
          // 填dp表的第一行和第一列
          else if (i == 0) dp[i][j] = dp[i][j-1];
          else if (j == 0) dp[i][j] = dp[i-1][j];
          // 常规情况，向下走或向右走
          else dp[i][j] = dp[i-1][j] + dp[i][j-1];
        }
      }
      return dp[m - 1][n - 1];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}