//在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。 s 只包含小写字母。 
//
// 示例 1: 
//
// 
//输入：s = "abaccdeff"
//输出：'b'
// 
//
// 示例 2: 
//
// 
//输入：s = "" 
//输出：' '
// 
//
// 
//
// 限制： 
//
// 0 <= s 的长度 <= 50000 
// Related Topics 队列 哈希表 字符串 计数 👍 157 👎 0

package leetcode.editor.cn;

// 剑指 Offer 50.第一个只出现一次的字符
class Code_Offer_DiYiGeZhiChuXianYiCiDeZiFuLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public char firstUniqChar(String s) {
      if (s == null || s.length() == 0) {
        return ' ';
      }

      int[] counts = new int[26];
      for (int i = 0; i < s.length(); i++) {
        counts[s.charAt(i) - 'a']++;
      }

      for (int i = 0; i < s.length(); i++) {
        if (counts[s.charAt(i) - 'a'] == 1) {
          return s.charAt(i);
        }
      }
      return ' ';
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}