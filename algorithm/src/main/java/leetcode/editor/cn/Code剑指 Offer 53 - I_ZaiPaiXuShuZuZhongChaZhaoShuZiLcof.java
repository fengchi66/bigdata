//统计一个数字在排序数组中出现的次数。 
//
// 
//
// 示例 1: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2 
//
// 示例 2: 
//
// 
//输入: nums = [5,7,7,8,8,10], target = 6
//输出: 0 
//
// 
//
// 提示： 
//
// 
// 0 <= nums.length <= 10⁵ 
// -10⁹ <= nums[i] <= 10⁹ 
// nums 是一个非递减数组 
// -10⁹ <= target <= 10⁹ 
// 
//
// 
//
// 注意：本题与主站 34 题相同（仅返回值不同）：https://leetcode-cn.com/problems/find-first-and-last-
//position-of-element-in-sorted-array/ 
// Related Topics 数组 二分查找 👍 225 👎 0

package leetcode.editor.cn;

// 剑指 Offer 53 - I.在排序数组中查找数字 I
class Code_Offer_ZaiPaiXuShuZuZhongChaZhaoShuZiLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int search(int[] nums, int target) {
      // 第一个比target - 1大的下标也就是第一个target的下标
      int left = search2(nums, target - 1);
      // 第一个比target大的下标-1,也就是最后一个target的下标
      int right = search2(nums, target) - 1;
      if (left <= right && nums[left] == target) {
        return right - left + 1;
      }
      return 0;

    }

    // 查找第一个比target大的下标
    private int search2(int[] nums, int target) {
      int left = 0;
      int right = nums.length - 1;

      while (left <= right) {
        int mid = (left + right) / 2;
        if (nums[mid] > target) {
          // 判断mid是不是第一个
          if (mid == 0 || nums[mid - 1] <= target) {
            return mid;
          } else { // mid不是第一个
            right = mid - 1;
          }
        } else {
          left = mid + 1;
        }
      }
      return nums.length;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}