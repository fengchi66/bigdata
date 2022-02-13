//给定一个非空字符串 s，请判断如果 最多 从字符串中删除一个字符能否得到一个回文字符串。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "aba"
//输出: true
// 
//
// 示例 2: 
//
// 
//输入: s = "abca"
//输出: true
//解释: 可以删除 "c" 字符 或者 "b" 字符
// 
//
// 示例 3: 
//
// 
//输入: s = "abc"
//输出: false 
//
// 
//
// 提示: 
//
// 
// 1 <= s.length <= 10⁵ 
// s 由小写英文字母组成 
// 
//
// 
//
// 注意：本题与主站 680 题相同： https://leetcode-cn.com/problems/valid-palindrome-ii/ 
// Related Topics 贪心 双指针 字符串 👍 21 👎 0

package leetcode.editor.cn;

// 剑指 Offer II 019.最多删除一个字符得到回文
class Code_Offer_RQku0D {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean validPalindrome(String s) {
      int left = 0;
      int right = s.length() - 1;

      while (left < right) {
        char ch1 = s.charAt(left);
        char ch2 = s.charAt(right);
        if (ch1 == ch2) {
          left++;
          right--;
        } else {
          return validPalindrome(s, left + 1, right) || validPalindrome(s, left, right - 1);
        }
      }
      return true;
    }

    // 判断字符串s从left到right是不是回文
    private boolean validPalindrome(String s, int left, int right) {
      while (left < right) {
        char ch1 = s.charAt(left);
        char ch2 = s.charAt(right);
        if (ch1 != ch2) {
          return false;
        }
        left++;
        right--;
      }
      return true;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}