//给你一个 只包含正整数 的 非空 数组 nums 。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,5,11,5]
//输出：true
//解释：数组可以分割成 [1, 5, 5] 和 [11] 。 
//
// 示例 2： 
//
// 
//输入：nums = [1,2,3,5]
//输出：false
//解释：数组不能分割成两个元素和相等的子集。
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 200 
// 1 <= nums[i] <= 100 
// 
// Related Topics 数组 动态规划 👍 1115 👎 0

package leetcode.editor.cn;

// 416.分割等和子集
public class Code416_PartitionEqualSubsetSum {

  public boolean canPartition(int[] nums) {
    int n = nums.length;
    int sum = 0;
    for (int num : nums) {
      sum += num;
    }

    int m = sum / 2;
    if (sum % 2 != 0) {
      return false;
    }

    // dp[i][j]表示在前i个元素中是否能选一些元素和为j
    boolean[] dp = new boolean[m + 1];
    dp[0] = true;

    for (int i = 1; i < n; i++) {
      for (int j = m; j >= nums[i]; j--) {
        // 选该元素或者不选
        dp[j] = dp[j] || dp[j - nums[i]];
      }
    }
    return dp[m];
  }

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public boolean canPartition(int[] nums) {
      int n = nums.length;
      int sum = 0;
      for (int num : nums) {
        sum += num;
      }

      int m = sum / 2;
      if (sum % 2 != 0) {
        return false;
      }

      // dp[i][j]表示在前i个元素中是否能选一些元素和为j
      boolean[][] dp = new boolean[n + 1][m + 1];
      dp[0][0] = true;

      for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
          // 选该元素或者不选
          if (j >= nums[i - 1]) { // 选: i-1就已经满足了 或者加上i位置刚好等于j
            dp[i][j] = dp[i - 1][j - nums[i - 1]] || dp[i - 1][j];
          } else { // 不选
            dp[i][j] = dp[i - 1][j]; // 前i-1位置能否凑齐j
          }
        }
      }
      return dp[n][m];
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}