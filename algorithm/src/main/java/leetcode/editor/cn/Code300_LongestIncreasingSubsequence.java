//给你一个整数数组 nums ，找到其中最长严格递增子序列的长度。 
//
// 子序列是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序
//列。 
// 
//
// 示例 1： 
//
// 
//输入：nums = [10,9,2,5,3,7,101,18]
//输出：4
//解释：最长递增子序列是 [2,3,7,101]，因此长度为 4 。
// 
//
// 示例 2： 
//
// 
//输入：nums = [0,1,0,3,2,3]
//输出：4
// 
//
// 示例 3： 
//
// 
//输入：nums = [7,7,7,7,7,7,7]
//输出：1
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 2500 
// -10⁴ <= nums[i] <= 10⁴ 
// 
//
// 
//
// 进阶： 
//
// 
// 你可以设计时间复杂度为 O(n²) 的解决方案吗？ 
// 你能将算法的时间复杂度降低到 O(n log(n)) 吗? 
// 
// Related Topics 数组 二分查找 动态规划 👍 2179 👎 0

package leetcode.editor.cn;

import com.sun.xml.internal.bind.v2.model.core.ID;
import java.util.Arrays;

// 300.最长递增子序列
public class Code300_LongestIncreasingSubsequence {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int lengthOfLIS(int[] nums) {
      int n = nums.length;
      // dp[n]表示从0位置开始到n位置区间数组最长递增子序列的长度
      int[] dp = new int[n];
      Arrays.fill(dp, 1);
      int ans = 1;

      // 求每个位置的i
      for (int i = 1; i < n; i++) {
        // 枚举i前面的数，如果小于i位置的数，那么构成递增结构,可以写状态转移方程
        for (int j = 0; j < i; j++) {
          if (nums[i] > nums[j]) {
            dp[i] = Math.max(dp[i], dp[j] + 1);
          }
        }
        ans = Math.max(ans, dp[i]);
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}