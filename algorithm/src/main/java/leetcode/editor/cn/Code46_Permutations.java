//给定一个不含重复数字的数组 nums ，返回其 所有可能的全排列 。你可以 按任意顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,2,3]
//输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1]
//输出：[[0,1],[1,0]]
// 
//
// 示例 3： 
//
// 
//输入：nums = [1]
//输出：[[1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 6 
// -10 <= nums[i] <= 10 
// nums 中的所有整数 互不相同 
// 
// Related Topics 数组 回溯 👍 1657 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

// 46.全排列
public class Code46_Permutations {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<List<Integer>> permute(int[] nums) {
      int n = nums.length;
      List<List<Integer>> ans = new LinkedList<>();
      LinkedList<Integer> subset = new LinkedList<>();
      // 每个位置的元素是否使用过
      boolean[] used = new boolean[n];

      // 从0位置开始选择数字生成全排列
      dfs(nums, 0, ans, subset, used);
      return ans;
    }

    private void dfs(int[] nums, int index, List<List<Integer>> ans, LinkedList<Integer> subset,
        boolean[] used) {
      // base case
      if (index == nums.length) {
        ans.add(subset);
      }
      for (int i = 0; i < nums.length; i++) {
        // 当前位置的数没有用过
        if (!used[i]) {
          subset.add(nums[i]);
          used[i] = true;
          dfs(nums, index +1, ans, subset, used);
          used[i] = false;
          subset.removeLast();
        }
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}