package com.data.offer02.code;

// 排序数组中只出现一次的数字
public class Solution70 {

  // 给定一个只包含整数的有序数组 nums ，每个元素都会出现两次，唯有一个数只会出现一次，请找出这个唯一的数字。
  public int singleNonDuplicate(int[] nums) {
    // 将数组每两个分为一组，如果有一个数只出现一次，那么会导致当前以及后面的组的数字都不一样
    int left = 0;
    int right = nums.length / 2;

    while (left <= right) {
      int mid = (left + right) / 2;
      int i = 2 * mid;

      if (i < nums.length - 1 && nums[i] != nums[i + 1]) { // 此时i位置是出现了组内元素不一致的组
        if (i == 0 || nums[i - 1] == nums[i - 2]) { // 是第一个组
          return nums[i];
        }
        // 不是第一个组
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    // 前面寻找的组不包括数组的最后一个元素
    return nums[nums.length - 1];
  }
}
