package com.data.geektime.week_01;

// 合并两个有序数组
public class Solution88 {

  // 其实就是归并排序的merge过程
  public void merge(int[] nums1, int m, int[] nums2, int n) {
    int[] nums = new int[m + n];
    int index = 0;
    int i = 0;
    int j = 0;

    while (i < m && j < n) {
      nums[index++] = nums1[i] <= nums2[j] ? nums1[i++] : nums2[j++];
    }

    // num1或者num2没有遍历完
    while (i < m) {
      nums[index++] = nums1[i++];
    }
    while (j < n) {
      nums[index++] = nums2[j++];
    }

    // 把nums赋值给nums1
    for (int k = 0; k < nums.length; k++) {
      nums1[k] = nums[k];
    }
  }
}
