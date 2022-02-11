/**
 * <p>English description is not available for the problem. Please switch to Chinese.</p>
 * <div><div>Related Topics</div><div><li>数组</li><li>滑动窗口</li></div></div><br><div><li>👍 39</li><li>👎 0</li></div>
 */
package leetcode.editor.cn;

// 剑指 Offer II 009.乘积小于 K 的子数组
class Code_Offer_ZVAVXX {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 双指针，left和right之间维持乘积小于k
    public int numSubarrayProductLessThanK(int[] nums, int k) {
      int p1 = 0;
      int sum = 1;
      int count = 0;

      for (int p2 = 0; p2 < nums.length; p2++) {
        sum *= nums[p2];
        while (p1 <= p2 && sum >= k) {
          sum /= nums[p1++];
        }
        count += p2 - p1 + 1;
      }
      return count;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}