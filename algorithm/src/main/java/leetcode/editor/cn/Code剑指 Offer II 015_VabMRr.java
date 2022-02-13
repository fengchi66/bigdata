//给定两个字符串 s 和 p，找到 s 中所有 p 的 变位词 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。 
//
// 变位词 指字母相同，但排列不同的字符串。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "cbaebabacd", p = "abc"
//输出: [0,6]
//解释:
//起始索引等于 0 的子串是 "cba", 它是 "abc" 的变位词。
//起始索引等于 6 的子串是 "bac", 它是 "abc" 的变位词。
// 
//
// 示例 2: 
//
// 
//输入: s = "abab", p = "ab"
//输出: [0,1,2]
//解释:
//起始索引等于 0 的子串是 "ab", 它是 "ab" 的变位词。
//起始索引等于 1 的子串是 "ba", 它是 "ab" 的变位词。
//起始索引等于 2 的子串是 "ab", 它是 "ab" 的变位词。
// 
//
// 
//
// 提示: 
//
// 
// 1 <= s.length, p.length <= 3 * 10⁴ 
// s 和 p 仅包含小写字母 
// 
//
// 
//
// 注意：本题与主站 438 题相同： https://leetcode-cn.com/problems/find-all-anagrams-in-a-
//string/ 
// Related Topics 哈希表 字符串 滑动窗口 👍 11 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

// 剑指 Offer II 015.字符串中的所有变位词
class Code_Offer_VabMRr {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<Integer> findAnagrams(String s, String p) {
      LinkedList<Integer> ans = new LinkedList<>();
      if (s.length() < p.length()) {
        return ans;
      }

      int[] counts = new int[26];
      for (int i = 0; i < p.length(); i++) {
        counts[p.charAt(i) - 'a']++;
        counts[s.charAt(i) - 'a']--;
      }

      if (allZero(counts)) {
        ans.add(0);
      }

      // 双指针：每在字符串中添加一个字符时，就把哈希表对应位置减一；减少一个字符时，就把哈希表对应位置加一
      for (int i = p.length(); i < s.length(); i++) {
        counts[s.charAt(i) - 'a']--;
        counts[s.charAt(i - p.length()) - 'a']++;
        if (allZero(counts)) {
          ans.add(i - p.length() - 1);
        }
      }
      return ans;
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