/**
 * <p>English description is not available for the problem. Please switch to Chinese.</p>
 * <div><div>Related Topics</div><div><li>数组</li><li>哈希表</li><li>前缀和</li></div></div><br><div><li>👍 38</li><li>👎 0</li></div>
 */
package leetcode.editor.cn;

import java.util.HashMap;

// 剑指 Offer II 010.和为 k 的子数组
class Code_Offer_QTMn0o {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 前缀和:map存前缀和以及次数
    public int subarraySum(int[] nums, int k) {
      HashMap<Integer, Integer> map = new HashMap<>();
      map.put(0, 1);

      int sum = 0, count = 0;

      for (int num : nums) {
        sum += num;
        count += map.getOrDefault(sum - k, 0);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
      }
      return count;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}