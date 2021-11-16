package com.data.offer02.demo;

public class BinarySearch {

  // 二分查找
  public int search(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;

    while (left <= right) {
      int mid = (left + right) / 2;
      if (nums[mid] == target) {
        return mid;
      }
      if (nums[mid] > target) {
        right = mid - 1;
      } else {
        left = mid + 1;
      }
    }
    return -1;
  }

  // 二分查找的递归实现
  public int search2(int[] nums, int target) {

  }

  // 递归函数:在[left,right]范围内查找
  private int search(int[] nums, int target, int left, int right) {
    // base case
    if (left > right) {
      return -1;
    }

    int mid = (left + right) / 2;
    if (nums[mid] == target) {
      return mid;
    } else if (nums[mid] > target) {
      return search(nums, target, left, mid - 1);
    } else {
      return search(nums, target, left + 1, mid);
    }
  }

}
