package com.data.offer02.code;

// 山峰数组的顶部
public class Solution69 {

  // 山峰数组的顶部是唯一一个比它左边大，比它右边也大的数字
  public int peakIndexInMountainArray(int[] arr) {
    // 题目说明arr.length >= 3,顶部一定在[1, arr.length - 2]之间
    int left = 1;
    int right = arr.length - 2;

    while (left <= right) {
      int mid = (left + right) / 2;
      if (arr[mid] > arr[mid - 1] && arr[mid] > arr[mid + 1]) {
        return mid;
      }
      if (arr[mid] > arr[mid - 1]) { // 说明mid在上升部分
        left = mid + 1;
      } else { // 在下降部分
        right = mid - 1;
      }
    }
    return -1;
  }
}
