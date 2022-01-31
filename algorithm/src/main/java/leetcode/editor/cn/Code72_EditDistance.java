//给你两个单词 word1 和 word2， 请返回将 word1 转换成 word2 所使用的最少操作数 。 
//
// 你可以对一个单词进行如下三种操作： 
//
// 
// 插入一个字符 
// 删除一个字符 
// 替换一个字符 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：word1 = "horse", word2 = "ros"
//输出：3
//解释：
//horse -> rorse (将 'h' 替换为 'r')
//rorse -> rose (删除 'r')
//rose -> ros (删除 'e')
// 
//
// 示例 2： 
//
// 
//输入：word1 = "intention", word2 = "execution"
//输出：5
//解释：
//intention -> inention (删除 't')
//inention -> enention (将 'i' 替换为 'e')
//enention -> exention (将 'n' 替换为 'x')
//exention -> exection (将 'n' 替换为 'c')
//exection -> execution (插入 'u')
// 
//
// 
//
// 提示： 
//
// 
// 0 <= word1.length, word2.length <= 500 
// word1 和 word2 由小写英文字母组成 
// 
// Related Topics 字符串 动态规划 👍 2076 👎 0

package leetcode.editor.cn;

// 72.编辑距离
public class Code72_EditDistance {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int minDistance(String word1, String word2) {
      int n = word1.length();
      int m = word2.length();

      word1 = " " + word1;
      word2 = " " + word2;

      // dp[i,j]表示将word1[1...i]变为word2[1...j]的编辑距离。
      int[][] dp = new int[n + 1][m + 1];
      // word1变为空字符串或者空字符串变为word2
      for (int i = 0; i <= n; i++) dp[i][0] = i;
      for (int i = 0; i <= m; i++) dp[0][i] = i;

      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          // 插入、删除、替换(不变)
          int step = word1.charAt(i) == word2.charAt(j) ? 0 : 1;
          dp[i][j] = Math.min(Math.min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + step);
        }
      }
      return dp[n][m];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}