//给定两个字符串 s1 和 s2，写一个函数来判断 s2 是否包含 s1 的某个变位词。 
//
// 换句话说，第一个字符串的排列之一是第二个字符串的 子串 。 
//
// 
//
// 示例 1： 
//
// 
//输入: s1 = "ab" s2 = "eidbaooo"
//输出: True
//解释: s2 包含 s1 的排列之一 ("ba").
// 
//
// 示例 2： 
//
// 
//输入: s1= "ab" s2 = "eidboaoo"
//输出: False
// 
//
// 
//
// 提示： 
//
// 
// 1 <= s1.length, s2.length <= 10⁴ 
// s1 和 s2 仅包含小写字母 
// 
//
// 
//
// 注意：本题与主站 567 题相同： https://leetcode-cn.com/problems/permutation-in-string/ 
// Related Topics 哈希表 双指针 字符串 滑动窗口 👍 25 👎 0

package leetcode.editor.cn;

import java.util.Arrays;

// 剑指 Offer II 014.字符串中的变位词
class Code_Offer_MPnaiL {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 双指针
    public boolean checkInclusion(String s1, String s2) {
      if (s1.length() > s2.length()) {
        return false;
      }

      int[] counts = new int[26];
      for (int i = 0; i < s1.length(); i++) {
        counts[s1.charAt(i) - 'a']++;
        counts[s2.charAt(i) - 'a']--;
      }

      if (allZero(counts)) {
        return true;
      }

      // 双指针：每在字符串中添加一个字符时，就把哈希表对应位置减一；减少一个字符时，就把哈希表对应位置加一
      for (int i = s1.length(); i < s2.length(); i++) {
        counts[s2.charAt(i) - 'a']--;
        counts[s2.charAt(i - s1.length()) - 'a']++;
        if (allZero(counts)) return true;
      }
      return false;
    }

    // counts数组元素是不是全为0
    private boolean allZero(int[] counts) {
      for (int count : counts) {
        if (count != 0) {
          return false;
        }
      }
      return true;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}