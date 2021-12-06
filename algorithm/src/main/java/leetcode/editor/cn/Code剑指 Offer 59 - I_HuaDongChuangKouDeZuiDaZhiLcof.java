//给定一个数组 nums 和滑动窗口的大小 k，请找出所有滑动窗口里的最大值。 
//
// 示例: 
//
// 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
//输出: [3,3,5,5,6,7] 
//解释: 
//
//  滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7 
//
// 
//
// 提示： 
//
// 你可以假设 k 总是有效的，在输入数组不为空的情况下，1 ≤ k ≤ 输入数组的大小。 
//
// 注意：本题与主站 239 题相同：https://leetcode-cn.com/problems/sliding-window-maximum/ 
// Related Topics 队列 滑动窗口 单调队列 堆（优先队列） 👍 349 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;

// 剑指 Offer 59 - I.滑动窗口的最大值
class Code_Offer_HuaDongChuangKouDeZuiDaZhiLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 单调队列
    public int[] maxSlidingWindow(int[] nums, int k) {
      if (nums == null || nums.length == 0 || k > nums.length) {
        return new int[]{};
      }
      // 单调队列，存数组的下标
      LinkedList<Integer> queue = new LinkedList<>();
      // 答案
      int[] ans = new int[nums.length - k + 1];
      int index = 0;

      for (int i = 0; i < nums.length; i++) {
        // 1. 维持单调队列结构
        while (!queue.isEmpty() && nums[i] >= nums[queue.peekLast()]) {
          queue.pollLast();
        }
        queue.offerLast(i);

        // 2. 移除过期的元素
        while (i - queue.peekFirst() >= k) {
          queue.pollFirst();
        }

        // 3.取出答案
        if (i >= k - 1) {
          ans[index] = nums[queue.peekFirst()];
          index++;
        }
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}