package com.data.offer02.code;

// 查找插入位置
public class Solution68 {

  // 找到第一个值>=target的元素的位置
  public int searchInsert(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
      int mid = (left + right) / 2;
      if (nums[mid] >= target) { // 判断mid是不是第一个
        if (mid == 0 || nums[mid - 1] < target) { // 是第一个
          return mid;
        } else { // mid不是第一个
          right = mid - 1;
        }
      } else {
        left = mid + 1;
      }
    }
    // 如果在数组中没有找到第一个值>=target的元素的位置，说明数组元素都比target小，则返回nums.length
    return nums.length;
  }

}
