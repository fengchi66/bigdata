//给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。 
//
// 字母异位词 是由重新排列源单词的字母得到的一个新单词，所有源单词中的字母通常恰好只用一次。 
//
// 
//
// 示例 1: 
//
// 
//输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
//输出: [["bat"],["nat","tan"],["ate","eat","tea"]] 
//
// 示例 2: 
//
// 
//输入: strs = [""]
//输出: [[""]]
// 
//
// 示例 3: 
//
// 
//输入: strs = ["a"]
//输出: [["a"]] 
//
// 
//
// 提示： 
//
// 
// 1 <= strs.length <= 10⁴ 
// 0 <= strs[i].length <= 100 
// strs[i] 仅包含小写字母 
// 
// Related Topics 哈希表 字符串 排序 👍 992 👎 0

package leetcode.editor.cn;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

// 49.字母异位词分组
public class Code49_GroupAnagrams {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 先对str排序，哈希值相同的分到同一个组
    public List<List<String>> groupAnagrams(String[] strs) {
      HashMap<String, LinkedList<String>> map = new HashMap<>();

      for (String str : strs) {
        char[] chars = str.toCharArray();
        Arrays.sort(chars);

        String s = new String(chars);
        LinkedList<String> list = map.getOrDefault(s, new LinkedList<>());
        list.add(str);
        map.put(s, list);
      }
      return new LinkedList<>(map.values());
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}