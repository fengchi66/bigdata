//给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。 
//
// 如果数组中不存在目标值 target，返回 [-1, -1]。 
//
// 进阶： 
//
// 
// 你可以设计并实现时间复杂度为 O(log n) 的算法解决此问题吗？ 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 8
//输出：[3,4] 
//
// 示例 2： 
//
// 
//输入：nums = [5,7,7,8,8,10], target = 6
//输出：[-1,-1] 
//
// 示例 3： 
//
// 
//输入：nums = [], target = 0
//输出：[-1,-1] 
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
// Related Topics 数组 二分查找 👍 1308 👎 0

package leetcode.editor.cn;

// 34.在排序数组中查找元素的第一个和最后一个位置
public class Code34_FindFirstAndLastPositionOfElementInSortedArray {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int[] searchRange(int[] nums, int target) {
      // 第一个比target - 1大的下标也就是第一个target的下标
      int left = search(nums, target - 1);
      // 第一个比target大的下标-1,也就是最后一个target的下标
      int right = search(nums, target) - 1;
      if (left <= right && nums[left] == target) {
        return new int[]{left, right};
      }
      return new int[]{-1, -1};
    }

    // 查找第一个比target大的下标
    private int search(int[] nums, int target) {
      int left = 0;
      int right = nums.length;

      while (left < right) {
        int mid = (left + right) / 2;
        if (nums[mid] > target) {
          right = mid; // 答案是right或者在right左边
        } else {
          left = mid + 1; // 答案在mid右边
        }
      }
      // 如果找不到比target大的下标，说明数组元素都比target小，将nums.length返回
      return right;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}