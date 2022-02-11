/**
 * <p>English description is not available for the problem. Please switch to Chinese.</p>
 * <div><div>Related Topics</div><div><li>数组</li><li>双指针</li><li>排序</li></div></div><br><div><li>👍 37</li><li>👎 0</li></div>
 */
package leetcode.editor.cn;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

// 剑指 Offer II 007.数组中和为 0 的三个数
class Code_Offer_OneFGaJU {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    List<List<Integer>> ans = new LinkedList<>();
    LinkedList<Integer> subset = new LinkedList<>();


    public List<List<Integer>> threeSum(int[] nums) {

      if (nums == null || nums.length < 3) {
        return null;
      }

      dfs(nums, 0);

      return null;
    }

    // 从index位置出发，找数组中和为 0 的三个数
    private void dfs(int[] nums, int index) {
      // 如果状态长度为3，且和为0
      if (subset.size() == 3 && subset.stream().mapToInt(r -> r).sum() == 0) {
        ans.add(new LinkedList<>(subset));
      } else if (index < nums.length) {
        // 不选当前位置的数字
        dfs(nums, index + 1);

        // 选当前位置的数字
        subset.offerLast(nums[index]);
        dfs(nums, index + 1);
        subset.pollLast();
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}