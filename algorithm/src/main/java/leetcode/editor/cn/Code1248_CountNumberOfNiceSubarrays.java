//给你一个整数数组 nums 和一个整数 k。如果某个连续子数组中恰好有 k 个奇数数字，我们就认为这个子数组是「优美子数组」。 
//
// 请返回这个数组中 「优美子数组」 的数目。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,1,2,1,1], k = 3
//输出：2
//解释：包含 3 个奇数的子数组是 [1,1,2,1] 和 [1,2,1,1] 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [2,4,6], k = 1
//输出：0
//解释：数列中不包含任何奇数，所以不存在优美子数组。
// 
//
// 示例 3： 
//
// 
//输入：nums = [2,2,2,1,2,2,1,2,2,2], k = 2
//输出：16
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 50000 
// 1 <= nums[i] <= 10^5 
// 1 <= k <= nums.length 
// 
// Related Topics 数组 哈希表 数学 滑动窗口 👍 203 👎 0

package leetcode.editor.cn;

import java.util.HashMap;

// 1248.统计「优美子数组」
public class Code1248_CountNumberOfNiceSubarrays {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int numberOfSubarrays(int[] nums, int k) {
      // map存前缀和以及前缀和出现的次数
      HashMap<Integer, Integer> map = new HashMap<>();
      map.put(0, 1);

      int sum = 0, count = 0;

      for (int num : nums) {
        // 前缀和
        sum += num % 2 == 0 ? 0 : 1;
        // 前缀和为sum -k的次数和何为k的子数组个数相同
        count += map.getOrDefault(sum - k, 0);
        map.put(sum, map.getOrDefault(sum, 0) + 1);
      }
      return count;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)
}