//你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，如果两间相邻的房屋在同一晚上
//被小偷闯入，系统会自动报警。 
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。 
//
// 
//
// 示例 1： 
//
// 
//输入：[1,2,3,1]
//输出：4
//解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
//     偷窃到的最高金额 = 1 + 3 = 4 。 
//
// 示例 2： 
//
// 
//输入：[2,7,9,3,1]
//输出：12
//解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
//     偷窃到的最高金额 = 2 + 9 + 1 = 12 。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 100 
// 0 <= nums[i] <= 400 
// 
// Related Topics 数组 动态规划 👍 1859 👎 0

package leetcode.editor.cn;

// 198.打家劫舍
public class Code198_HouseRobber {

  public int rob(int[] nums) {
    int n = nums.length;
    // dp[i][j]表示在进入或不进去i号房间时的最大收益
    int[][] dp = new int[n][2];
    dp[0][0] = 0;
    dp[0][1] = nums[0];

    for (int i = 1; i < n; i++) {
      // 不进入i号房间：即进入i-1和不进入i-1房间的最大值
      dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
      // 进入i号房间：不进入i-1房间 + nums[i]
      dp[i][1] = dp[i - 1][0] + nums[i];
    }
    return Math.max(dp[n-1][0], dp[n-1][1]);
  }

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int rob(int[] nums) {
      if (nums == null || nums.length == 0) {
        return 0;
      }
      if (nums.length == 1) {
        return nums[0];
      }
      // dp[i]表示偷到第i号房间，所能偷到的最大金额
      int n = nums.length;
      int[] dp = new int[n];

      dp[0] = nums[0];
      dp[1] = Math.max(nums[0], nums[1]);

      for (int i = 2; i < n; i++) {
        // 两个选择：偷当前位置或者不偷当前位置房间，求最大值
        dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i]);
      }
      return dp[n - 1];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}