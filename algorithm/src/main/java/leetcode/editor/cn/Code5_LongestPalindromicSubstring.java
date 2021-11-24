//给你一个字符串 s，找到 s 中最长的回文子串。 
//
// 
//
// 示例 1： 
//
// 
//输入：s = "babad"
//输出："bab"
//解释："aba" 同样是符合题意的答案。
// 
//
// 示例 2： 
//
// 
//输入：s = "cbbd"
//输出："bb"
// 
//
// 示例 3： 
//
// 
//输入：s = "a"
//输出："a"
// 
//
// 示例 4： 
//
// 
//输入：s = "ac"
//输出："a"
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 1000 
// s 仅由数字和英文字母（大写和/或小写）组成 
// 
// Related Topics 字符串 动态规划 👍 4372 👎 0

package leetcode.editor.cn;

// 5.最长回文子串
public class Code5_LongestPalindromicSubstring {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 在s[L...R]范围内寻找最长回文子串
    public String longestPalindrome(String s) {

      if (s == null || s.length() == 0) {
        return s;
      }

      // 最长子串开始的位置以及跨越的长度
      int start = 0;
      int step = 1;

      int n = s.length();
      // dp[i][j]表示字符串在[i...j]范围内是否是回文字符串
      boolean[][] dp = new boolean[n][n];

      // base case:i==j时，dp为true
      for (int i = 0; i < n; i++) {
        dp[i][i] = true;
      }

      // base case，j==i+1时，看这两个字符是否相等
      for (int i = 0; i < n - 1; i++) {
        dp[i][i + 1] = s.charAt(i) == s.charAt(i + 1);
        if (dp[i][i + 1]) {
          start = i;
          step = 2;
        }
      }

      // 普遍情况：填dp表格
      for (int i = n - 3; i >= 0; i--) {
        for (int j = i + 2; j < n; j++) {
          // dp[i][j]是否为回文，需要dp[i + 1][j - 1]是回文，且i、j位置的字符相等
          dp[i][j] = dp[i + 1][j - 1] && s.charAt(i) == s.charAt(j);
          if (dp[i][j] && j - i + 1 > step) {
            start = i;
            step = j - i + 1;
          }
        }
      }
      return s.substring(start, start + step);
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}