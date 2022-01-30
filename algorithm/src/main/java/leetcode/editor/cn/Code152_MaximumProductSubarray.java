//给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。 
//
// 
//
// 示例 1: 
//
// 输入: [2,3,-2,4]
//输出: 6
//解释: 子数组 [2,3] 有最大乘积 6。
// 
//
// 示例 2: 
//
// 输入: [-2,0,-1]
//输出: 0
//解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。 
// Related Topics 数组 动态规划 👍 1469 👎 0

package leetcode.editor.cn;

// 152.乘积最大子数组
public class Code152_MaximumProductSubarray {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxProduct(int[] nums) {
      int n = nums.length;

      // dp[i]表示从0到位置i乘积最大/最小子数组
      int[] fMax = new int[n];
      int[] fMin = new int[n];

      fMax[0] = nums[0];
      fMin[0] = nums[0];
      int ans = nums[0];

      for (int i = 1; i < n; i++) {
        fMax[i] = Math.max(Math.max(fMax[i - 1] * nums[i], nums[i]), fMin[i - 1] * nums[i]);
        fMin[i] = Math.min(Math.min(fMax[i - 1] * nums[i], nums[i]), fMin[i - 1] * nums[i]);
        ans = Math.max(ans, fMax[i]);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}