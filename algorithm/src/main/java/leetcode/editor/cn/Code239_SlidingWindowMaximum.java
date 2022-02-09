//给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位
//。 
//
// 返回 滑动窗口中的最大值 。 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
//输出：[3,3,5,5,6,7]
//解释：
//滑动窗口的位置                最大值
//---------------               -----
//[1  3  -1] -3  5  3  6  7       3
// 1 [3  -1  -3] 5  3  6  7       3
// 1  3 [-1  -3  5] 3  6  7       5
// 1  3  -1 [-3  5  3] 6  7       5
// 1  3  -1  -3 [5  3  6] 7       6
// 1  3  -1  -3  5 [3  6  7]      7
// 
//
// 示例 2： 
//
// 
//输入：nums = [1], k = 1
//输出：[1]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= nums.length <= 10⁵ 
// -10⁴ <= nums[i] <= 10⁴ 
// 1 <= k <= nums.length 
// 
// Related Topics 队列 数组 滑动窗口 单调队列 堆（优先队列） 👍 1388 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;

// 239.滑动窗口最大值
public class Code239_SlidingWindowMaximum {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] maxSlidingWindow(int[] nums, int k) {
      if (nums == null || nums.length == 0 || nums.length < k) {
        return null;
      }

      // 单调递减队列，队首维持最大值
      int n = nums.length;
      LinkedList<Integer> list = new LinkedList<>();
      int[] ans = new int[n - k + 1];
      int index = 0;

      for (int i = 0; i < n; i++) {
        // 维持队列递减结构
        while (!list.isEmpty() && nums[i] >= nums[list.peekLast()]) {
          list.pollLast();
        }

        list.add(i);

        // 单调队列中维持有效的下标
        while (list.peekFirst() <= i - k) {
          list.pollFirst();
        }

        // 答案
        if (i >= k - 1) {
          ans[index++] = nums[list.peekFirst()];
        }
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}