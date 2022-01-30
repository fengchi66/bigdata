//你是一个专业的小偷，计划偷窃沿街的房屋，每间房内都藏有一定的现金。这个地方所有的房屋都 围成一圈 ，这意味着第一个房屋和最后一个房屋是紧挨着的。同时，相邻的
//房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警 。 
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 在不触动警报装置的情况下 ，今晚能够偷窃到的最高金额。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [2,3,2]
//输出：3
//解释：你不能先偷窃 1 号房屋（金额 = 2），然后偷窃 3 号房屋（金额 = 2）, 因为他们是相邻的。
// 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3,1]
//输出：4
//解释：你可以先偷窃 1 号房屋（金额 = 1），然后偷窃 3 号房屋（金额 = 3）。
//     偷窃到的最高金额 = 1 + 3 = 4 。 
//
// 示例 3： 
//
// 
//输入：nums = [1,2,3]
//输出：3
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 1000 
// 
// Related Topics 数组 动态规划 👍 909 👎 0

package leetcode.editor.cn;

// 213.打家劫舍 II
public class Code213_HouseRobberIi {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int rob(int[] nums) {
      if (nums == null || nums.length == 0)
        return 0;
      if (nums.length == 1)
        return nums[0];
      if (nums.length == 2)
        return Math.max(nums[0], nums[1]);

      int n = nums.length;
      // 偷第一家，不偷最后一家的最大利益
      int[] dp1 = new int[n - 1];
      // 偷最后一家，不偷第一家的最大利益
      int[] dp2 = new int[n];

      dp1[0] = nums[0];
      dp1[1] = nums[0];

      for (int i = 2; i < n - 1; i++) {
        // 当前位置两种选择：偷与不偷
        dp1[i] = Math.max(dp1[i - 2] + nums[i], dp1[i - 1]);
      }

      dp2[0] = 0;
      dp2[1] = nums[1];

      for (int i = 2; i < n; i++) {
        dp2[i] = Math.max(dp2[i - 2] + nums[i], dp2[i - 1]);
      }

      return Math.max(dp1[n - 2], dp2[n - 1]);
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}