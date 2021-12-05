//给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按 任意顺序 返回。 
//
// 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。 
//
// 
//
// 
//
// 示例 1： 
//
// 
//输入：digits = "23"
//输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]
// 
//
// 示例 2： 
//
// 
//输入：digits = ""
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：digits = "2"
//输出：["a","b","c"]
// 
//
// 
//
// 提示： 
//
// 
// 0 <= digits.length <= 4 
// digits[i] 是范围 ['2', '9'] 的一个数字。 
// 
// Related Topics 哈希表 字符串 回溯 👍 1615 👎 0

package leetcode.editor.cn;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// 17.电话号码的字母组合
public class Code17_LetterCombinationsOfAPhoneNumber {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    Map<Character, String> phoneMap = new HashMap<Character, String>() {{
      put('2', "abc");
      put('3', "def");
      put('4', "ghi");
      put('5', "jkl");
      put('6', "mno");
      put('7', "pqrs");
      put('8', "tuv");
      put('9', "wxyz");
    }};

    StringBuilder subset = new StringBuilder();
    List<String> ans = new LinkedList<>();

    public List<String> letterCombinations(String digits) {
      if (digits == null || digits.length() == 0) {
        return ans;
      }

      dfs(digits, 0);
      return ans;
    }

    private void dfs(String digits, int index) {
      // base case
      if (index == digits.length()) {
        ans.add(new String(subset));
      } else if (index < digits.length()) {
        // 还可以选字母,选index位置的数字
        char sh = digits.charAt(index);
        String phone = phoneMap.get(sh);
        for (int i = 0; i < phone.length(); i++) {
          // 回溯
          subset.append(phone.charAt(i));
          dfs(digits, index + 1);
          subset.deleteCharAt(index);
        }
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}