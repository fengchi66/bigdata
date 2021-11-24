//给定一个字符串 s ，请你找出其中不含有重复字符的 最长子串 的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: s = "abcabcbb"
//输出: 3 
//解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
// 
//
// 示例 2: 
//
// 
//输入: s = "bbbbb"
//输出: 1
//解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
// 
//
// 示例 3: 
//
// 
//输入: s = "pwwkew"
//输出: 3
//解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
//     请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
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
// Related Topics 哈希表 字符串 滑动窗口 👍 6460 👎 0

package leetcode.editor.cn;

import java.util.HashSet;

// 3.无重复字符的最长子串
public class Code3_LongestSubstringWithoutRepeatingCharacters {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 同向双指针
    public int lengthOfLongestSubstring(String s) {
      int right = 0;
      int ans = 0;
      HashSet<Character> set = new HashSet<>();

      for (int left = 0; left < s.length(); left++) {
        // 在固定左端点的情况下，查看右端点的取值范围，及将右端点取值范围的数据加入到set中，直到出现重复元素为止
        while (right < s.length() && !set.contains(s.charAt(right))) {
          set.add(s.charAt(right));
          right++;
        }
        ans = Math.max(ans, right - left);
        // 每一次计算后会移动左端点，将对应的左端点位置的元素从set中移除
        set.remove(s.charAt(left));
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}