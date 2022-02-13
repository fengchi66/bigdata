/**
 * <p>English description is not available for the problem. Please switch to Chinese.</p>
 * <div><div>Related Topics</div><div><li>数组</li><li>双指针</li><li>排序</li></div></div><br><div><li>👍 37</li><li>👎 0</li></div>
 */
package leetcode.editor.cn;

import java.lang.annotation.Target;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// 剑指 Offer II 007.数组中和为 0 的三个数
class Code_Offer_OneFGaJU {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    List<List<Integer>> ans = new LinkedList<>();

    // 在固定一个数的情况下，求threeSum==0
    public List<List<Integer>> threeSum(int[] nums) {
      if (nums.length >= 3) {
        Arrays.sort(nums);
      }

      int i = 0;
      while (i < nums.length - 2) {
        twoSum(nums, i);
        // 跳过i相同的数
        int temp = nums[i];
        while (nums[i] == temp && i < nums.length - 2) {
          i++;
        }
      }
      return ans;

    }

    // 从i+1位置出发，找数组中和为 0 的三个数
    private void twoSum(int[] nums, int i) {
      int j = i + 1;
      int k = nums.length - 1;
      while (j < k) {
        if (nums[i] + nums[j] + nums[k] == 0) {
          ans.add(Arrays.asList(nums[i], nums[j], nums[k]));
          // 跳过j相同的数据
          int temp = nums[j];
          while (nums[j] == temp && j < k) {
            j++;
          }
        } else if (nums[i] + nums[j] + nums[k] < 0) {
          j++;
        } else {
          k--;
        }
      }

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}