//给定一个字符串 s ，验证 s 是否是 回文串 ，只考虑字母和数字字符，可以忽略字母的大小写。 
//
// 本题中，将空字符串定义为有效的 回文串 。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "A man, a plan, a canal: Panama"
//输出: true
//解释："amanaplanacanalpanama" 是回文串 
//
// 示例 2: 
//
// 
//输入: s = "race a car"
//输出: false
//解释："raceacar" 不是回文串 
//
// 
//
// 提示： 
//
// 
// 1 <= s.length <= 2 * 10⁵ 
// 字符串 s 由 ASCII 字符组成 
// 
//
// 
//
// 注意：本题与主站 125 题相同： https://leetcode-cn.com/problems/valid-palindrome/ 
// Related Topics 双指针 字符串 👍 15 👎 0

package leetcode.editor.cn;

// 剑指 Offer II 018.有效的回文
class Code_Offer_XltzEq {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 双指针
    public boolean isPalindrome(String s) {
      int i = 0;
      int j = s.length() - 1;

      while (i < j) {
        // 只考虑字母和数字字符
        if (!Character.isLetterOrDigit(s.charAt(i))) {
          i++;
        } else if (!Character.isLetterOrDigit(s.charAt(j))) {
          j--;
        } else {
          char ch1 = s.charAt(i);
          char ch2 = s.charAt(j);
          if (Character.toLowerCase(ch1) != Character.toLowerCase(ch2)) {
            return false;
          }
          i++;
          j--;
        }
      }
      return true;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}