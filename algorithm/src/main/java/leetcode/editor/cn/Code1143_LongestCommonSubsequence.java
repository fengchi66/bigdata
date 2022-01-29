//给定两个字符串 text1 和 text2，返回这两个字符串的最长 公共子序列 的长度。如果不存在 公共子序列 ，返回 0 。 
//
// 一个字符串的 子序列 是指这样一个新的字符串：它是由原字符串在不改变字符的相对顺序的情况下删除某些字符（也可以不删除任何字符）后组成的新字符串。 
//
// 
// 例如，"ace" 是 "abcde" 的子序列，但 "aec" 不是 "abcde" 的子序列。 
// 
//
// 两个字符串的 公共子序列 是这两个字符串所共同拥有的子序列。 
//
// 
//
// 示例 1： 
//
// 
//输入：text1 = "abcde", text2 = "ace" 
//输出：3  
//解释：最长公共子序列是 "ace" ，它的长度为 3 。
// 
//
// 示例 2： 
//
// 
//输入：text1 = "abc", text2 = "abc"
//输出：3
//解释：最长公共子序列是 "abc" ，它的长度为 3 。
// 
//
// 示例 3： 
//
// 
//输入：text1 = "abc", text2 = "def"
//输出：0
//解释：两个字符串没有公共子序列，返回 0 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= text1.length, text2.length <= 1000 
// text1 和 text2 仅由小写英文字符组成。 
// 
// Related Topics 字符串 动态规划 👍 823 👎 0

package leetcode.editor.cn;

// 1143.最长公共子序列
public class Code1143_LongestCommonSubsequence {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int longestCommonSubsequence(String text1, String text2) {
      int m = text1.length();
      int n = text2.length();

      // 为了避免边界条件的讨论，将text1和text2前面分别置为" "
      text1 = " " + text1;
      text2 = " " + text2;

      // dp[i][j]表示text1和text2分别从0位置到i、j位置的公共子序列的长度
      int[][] dp = new int[m + 1][n + 1];

      for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
          if (text1.charAt(i) == text2.charAt(j)) { // i和j位置字符相同
            dp[i][j] = dp[i - 1][j - 1] + 1;
          } else { // i和j位置的字符不同
            dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
          }
        }
      }
      return dp[m][n];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}