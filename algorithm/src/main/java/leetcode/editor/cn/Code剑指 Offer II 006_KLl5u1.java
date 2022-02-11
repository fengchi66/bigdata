/**
 * <p>English description is not available for the problem. Please switch to Chinese.</p>
 * <div><div>Related Topics</div><div><li>数组</li><li>双指针</li><li>二分查找</li></div></div><br><div><li>👍 17</li><li>👎 0</li></div>
 */
package leetcode.editor.cn;

// 剑指 Offer II 006.排序数组中两个数字之和
class Code_Offer_KLl5u1 {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    // 双指针
    public int[] twoSum(int[] numbers, int target) {
      int left = 0;
      int right = numbers.length - 1;

      while (left < right) {
        if (numbers[left] + numbers[right] == target) {
          return new int[]{left, right};
        } else if (numbers[left] + numbers[right] < target) {
          left++;
        } else {
          right--;
        }
      }
      return null;

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}