//给你一个整数数组 nums 和一个整数 k ，请你统计并返回该数组中和为 k 的连续子数组的个数。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,1], k = 2
//输出：2
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3], k = 3
//输出：2
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2 * 10⁴ 
// -1000 <= nums[i] <= 1000 
// -10⁷ <= k <= 10⁷ 
// 
// Related Topics 数组 哈希表 前缀和 👍 1300 👎 0

package leetcode.editor.cn;

import java.util.HashMap;

// 560.和为 K 的子数组
public class Code560_SubarraySumEqualsK {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int subarraySum(int[] nums, int k) {
      // map存前缀和以及次数
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