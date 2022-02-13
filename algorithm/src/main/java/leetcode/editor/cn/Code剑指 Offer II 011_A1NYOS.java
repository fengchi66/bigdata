//给定一个二进制数组 nums , 找到含有相同数量的 0 和 1 的最长连续子数组，并返回该子数组的长度。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [0,1]
//输出: 2
//说明: [0, 1] 是具有相同数量 0 和 1 的最长连续子数组。 
//
// 示例 2: 
//
// 
//输入: nums = [0,1,0]
//输出: 2
//说明: [0, 1] (或 [1, 0]) 是具有相同数量 0 和 1 的最长连续子数组。 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// nums[i] 不是 0 就是 1 
// 
//
// 
//
// 注意：本题与主站 525 题相同： https://leetcode-cn.com/problems/contiguous-array/ 
// Related Topics 数组 哈希表 前缀和 👍 35 👎 0

package leetcode.editor.cn;

import java.util.HashMap;

// 剑指 Offer II 011.0 和 1 个数相同的子数组
class Code_Offer_A1NYOS {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 前缀和
    public int findMaxLength(int[] nums) {
      // 存前缀和以及前缀和第一次出现的数组下标
      // 前缀合为sum，表示i-j位置的子序和为0
      HashMap<Integer, Integer> map = new HashMap<>();
      map.put(0, -1);
      int sum = 0;
      int ans = 0;

      for (int i = 0; i < nums.length; i++) {
        sum += nums[i] == 0 ? -1 : 1;
        if (map.containsKey(sum)) {
          ans = Math.max(ans, i - map.get(sum));
        } else {
          map.put(sum, i);
        }
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}