//给定一个整数数组 nums ，数组中的元素 互不相同 。返回该数组所有可能的子集（幂集）。 
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
//
// 
//
// 注意：本题与主站 78 题相同： https://leetcode-cn.com/problems/subsets/ 
// Related Topics 位运算 数组 回溯 👍 14 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

// 剑指 Offer II 079.所有子集
class Code_Offer_TVdhkn {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 答案
    List<List<Integer>> ans = new LinkedList<>();
    // 状态
    LinkedList<Integer> subset = new LinkedList<>();

    public List<List<Integer>> subsets(int[] nums) {

      if (nums == null) {
        return ans;
      }

      dfs(nums, 0);
      return ans;

    }

    // 从index位置开始求子集
    private void dfs(int[] nums, int index) {
      // base case
      if (index == nums.length) {
        ans.add(new LinkedList<>(subset));
      } else if (index < nums.length) { // index位置的数还可以选择
        // 不选
        dfs(nums, index + 1);

        // 选
        subset.addLast(nums[index]);
        dfs(nums, index + 1);
        subset.pollLast();
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}