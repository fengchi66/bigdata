//给你一个整数数组 nums ，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 。请你找出并返回那个只出现了一次的元素。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,2,3,2]
//输出：3
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1,0,1,0,1,100]
//输出：100
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 3 * 10⁴ 
// -2³¹ <= nums[i] <= 2³¹ - 1 
// nums 中，除某个元素仅出现 一次 外，其余每个元素都恰出现 三次 
// 
//
// 
//
// 进阶：你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？ 
//
// 
//
// 注意：本题与主站 137 题相同：https://leetcode-cn.com/problems/single-number-ii/ 
// 👍 42 👎 0

package leetcode.editor.cn;

import java.util.HashMap;
import java.util.Map.Entry;

// 剑指 Offer II 004.只出现一次的数字
class Code_Offer_WGki4K {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int singleNumber(int[] nums) {
      HashMap<Integer, Integer> map = new HashMap<>();

      for (int num : nums) {
        map.put(num, map.getOrDefault(num, 0) + 1);
      }

      for (Entry<Integer, Integer> entry : map.entrySet()) {
        if (entry.getValue() == 1) {
          return entry.getKey();
        }
      }
      return -1;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}