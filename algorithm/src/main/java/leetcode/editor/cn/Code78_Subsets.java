//给你一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。 
//
// 解集 不能 包含重复的子集。你可以按 任意顺序 返回解集。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0]
//输出：[[],[0]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10 
// -10 <= nums[i] <= 10 
// nums 中的所有元素 互不相同 
// 
// Related Topics 位运算 数组 回溯 👍 1396 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

// 78.子集
public class Code78_Subsets {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 回溯法
    public List<List<Integer>> subsets(int[] nums) {
      List<List<Integer>> ans = new LinkedList<>();
      LinkedList<Integer> subset = new LinkedList<>();

      dfs(nums, ans, subset, 0);
      return ans;
    }

    // 从index位置开始选，能选到的子集
    private void dfs(int[] nums, List<List<Integer>> ans, LinkedList<Integer> subset, int index) {
      // base case
      if (index == nums.length) {
        ans.add(new LinkedList<>(subset));
      } else if (index < nums.length) {
        // 不选当前的元素
        dfs(nums, ans, subset, index + 1);

        // 选当前的元素
        subset.add(nums[index]);
        dfs(nums, ans, subset, index + 1);
        subset.removeLast();
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}