package com.data.offer02.demo;

// 排序数组
public class SortArray {

  int[] temp;

  // 1. 归并排序
  public int[] sortArray1(int[] nums) {
    temp = new int[nums.length];
    mergeSort(nums, 0, nums.length - 1);
    return nums;
  }

  // 对数组nums在[left,right]范围内进行排序
  private void mergeSort(int[] nums, int left, int right) {
    // base case
    if (left == right) {
      return;
    }

    // 找到数组的中间位置
    int mid = (left + right) / 2;
    // 分别对左右两部分排序
    mergeSort(nums, left, mid);
    mergeSort(nums, mid + 1, right);

    // 对于后序遍历而言，我们始终相信递归函数是对的，如见：左右两部分数组已经排好序了
    // 使用两个指针，将左右两部分排好序的数组整合到临时数组中
    int i = 0; // 临时数组中的下标
    int p1 = left;
    int p2 = mid + 1;

    while (p1 <= mid && p2 <= right) {
      temp[i++] = nums[p1] <= nums[p2] ? nums[p1++] : nums[p2++];
    }
    // 左右两部分数组，最多还有一个还没有遍历完
    while (p1 <= mid) {
      temp[i++] = nums[p1++];
    }

    while (p2 <= right) {
      temp[i++] = nums[p2++];
    }
    // 此时temp数组就已经是排好序的了，将temp数组copy到原数组num[left,right]
    for (int j = 0; j < right - left + 1; j++) {
      nums[left + j] = temp[j];
    }
  }

  // 快速排序
  public int[] sortArray2(int[] nums) {

    return nums;
  }

}
