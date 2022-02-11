/**
 * <p>English description is not available for the problem. Please switch to Chinese.</p>
 * <div><div>Related Topics</div><div><li>数组</li><li>二分查找</li><li>前缀和</li><li>滑动窗口</li></div></div><br><div><li>👍 34</li><li>👎 0</li></div>
 */
package leetcode.editor.cn;

// 剑指 Offer II 008.和大于等于 target 的最短子数组
class Code_Offer_TwoVG8Kg {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 双指针，left与right之间维持大于等于 target的一个子序和
    public int minSubArrayLen(int target, int[] nums) {

      int left = 0;
      int sum = 0;
      int size = Integer.MAX_VALUE;
      for (int right = 0; right < nums.length; right++) {
        sum += nums[right];
        while (sum >= target) {
          size = Math.min(size, right - left + 1);
          sum -= nums[left];
          left++;
        }
      }
      return size == Integer.MAX_VALUE ? 0 : size;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}