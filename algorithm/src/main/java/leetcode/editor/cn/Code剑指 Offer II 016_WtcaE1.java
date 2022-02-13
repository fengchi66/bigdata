//给定一个字符串 s ，请你找出其中不含有重复字符的 最长连续子字符串 的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子字符串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子字符串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
// 
//
// 示例 4: 
//
// 
//输入: s = ""
//输出: 0
// 
//
// 
//
// 提示： 
//
// 
// 0 <= s.length <= 5 * 10⁴ 
// s 由英文字母、数字、符号和空格组成 
// 
//
// 
//
// 注意：本题与主站 3 题相同： https://leetcode-cn.com/problems/longest-substring-without-
//repeating-characters/ 
// Related Topics 哈希表 字符串 滑动窗口 👍 20 👎 0

package leetcode.editor.cn;

import java.util.HashSet;

// 剑指 Offer II 016.不含重复字符的最长子字符串
class Code_Offer_WtcaE1 {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int lengthOfLongestSubstring(String s) {
      HashSet<Character> set = new HashSet<>();

      char[] chars = s.toCharArray();
      int size = 0;
      int right = 0;
      for (int left = 0; left < chars.length; left++) {
        // 固定左指针，延伸右指针，直到遇到重复元素
        while (right < chars.length && !set.contains(chars[right])) {
          set.add(chars[right]);
          right++;
        }
        size = Math.max(size, right - left);
        // 当左指针移动时，将左边的元素从set中拿出来
        set.remove(chars[left]);
      }
      return size;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}