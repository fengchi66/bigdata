//输入一个整型数组，数组中的一个或连续多个整数组成一个子数组。求所有子数组的和的最大值。 
//
// 要求时间复杂度为O(n)。 
//
// 
//
// 示例1: 
//
// 输入: nums = [-2,1,-3,4,-1,2,1,-5,4]
//输出: 6
//解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。 
//
// 
//
// 提示： 
//
// 
// 1 <= arr.length <= 10^5 
// -100 <= arr[i] <= 100 
// 
//
// 注意：本题与主站 53 题相同：https://leetcode-cn.com/problems/maximum-subarray/ 
//
// 
// Related Topics 数组 分治 动态规划 👍 425 👎 0

package leetcode.editor.cn;

// 剑指 Offer 42.连续子数组的最大和
class Code_Offer_LianXuZiShuZuDeZuiDaHeLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int maxSubArray(int[] nums) {
      int n = nums.length;
      // dp[i]表示从0到i位置，连续子数组的最大和
      int[] dp = new int[n];
      dp[0] = nums[0];
      int ans = dp[0];
      for (int i = 1; i < n; i++) {
        dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
        ans = Math.max(ans, dp[i]);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}