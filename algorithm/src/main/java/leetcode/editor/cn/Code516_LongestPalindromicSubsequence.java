//给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。 
//
// 子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "bbbab"
//输出：4
//解释：一个可能的最长回文子序列为 "bbbb" 。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出：2
//解释：一个可能的最长回文子序列为 "bb" 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由小写英文字母组成 
// 
// Related Topics 字符串 动态规划 👍 717 👎 0

package leetcode.editor.cn;

// 516.最长回文子序列
public class Code516_LongestPalindromicSubsequence {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int longestPalindromeSubseq(String s) {
      int n = s.length();
      // dp[i...j]表示在s[i...j]范围内最长回文子序列的长度
      int[][] dp = new int[n][n];

      // 填dp表
      dp[n - 1][n - 1] = 1;

      for (int i = 0; i < n - 1; i++) {
        dp[i][i] = 1;
        dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1) ? 2 : 1;
      }

      // 填dp表中剩余的格子
      for (int i = n - 3; i >= 0; i--) {
        for (int j = i + 2; j < n; j++) {
          if (s.charAt(i) == s.charAt(j)) {
            dp[i][j] = 2 + dp[i + 1][j - 1];
          } else {
            dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
          }
        }
      }
      return dp[0][n - 1];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}