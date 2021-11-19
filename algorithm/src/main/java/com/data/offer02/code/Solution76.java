package com.data.offer02.code;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;

// 数组中第k大的数字
public class Solution76 {

  public int findKthLargest(int[] nums, int k) {
    return find(nums, k, 0, nums.length - 1);
  }

  // 在数组nums[left,right]上寻找数组中第k大的数字
  private int find(int[] nums, int k, int left, int right) {
    if (left >= right) {
      return nums[left];
    }
    int mid = partition(nums, left, right);
    if (mid + 1 == k) {
      return nums[mid];
    } else if (mid + 1 < k) {
      return find(nums, k, mid + 1, right);
    } else {
      return find(nums, k, left, mid - 1);
    }
  }

  // 分区:降序排序
  private int partition(int[] nums, int left, int right) {
    int cur = nums[right];
    int i = left;
    for (int j = left; j < right; j++) {
      if (nums[j] >= cur) {
        swap(nums, i, j);
        i++;
      }
    }
    swap(nums, i, right);
    return i;
  }

  private void swap(int[] nums, int i, int j) {
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
  }
}
