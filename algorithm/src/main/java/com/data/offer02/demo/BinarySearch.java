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
    return search(nums, target, 0, nums.length - 1);
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

  // 以下案例假设数组都是升序排列的，如果是降序排列，解决思路类似
  // 二分变体1：查找第一个值等于给定值的元素
  public int search3(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = (left + right) / 2;
      if (nums[mid] > target) {
        right = mid - 1;
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else { // 当nums[mid] == target时，判断此时的mid是不是第一个元素
        if (mid == 0 || nums[mid - 1] != nums[mid]) {
          return mid;
        } else { // mid不是值等于target的第一个元素
          right = mid - 1;
        }
      }
    }
    return -1;
  }

  // 二分变体2：查找最后一个值等于给定值的元素
  public int search4(int[] nums, int target) {
    int left = 0;
    int right = nums.length - 1;
    while (left <= right) {
      int mid = (left + right) / 2;
      if (nums[mid] > target) {
        right = mid - 1;
      } else if (nums[mid] < target) {
        left = mid + 1;
      } else { // 当nums[mid] == target时，判断此时的mid是不是最后一个元素
        if (mid == nums.length - 1 || nums[mid] != nums[mid + 1]) {
          return mid;
        } else { // 此时的mid不是最后一个元素
          left = mid + 1;
        }
      }
    }
    return -1;
  }

  // 二分变体3：查找第一个大于等于给定值的元素
  public int bsearch(int[] a, int n, int value) {
    int low = 0;
    int high = n - 1;
    while (low <= high) {
      int mid = low + ((high - low) >> 1);
      if (a[mid] >= value) {
        if ((mid == 0) || (a[mid - 1] < value)) {
          return mid;
        } else {
          high = mid - 1;
        }
      } else {
        low = mid + 1;
      }
    }
    return -1;
  }

  // 查找第一个大于等于target的元素
  public int search02(int[] nums, int target) {
    int left = 0;
    int right = nums.length;
    while (left < right) {
      int mid = (left + right) / 2;
      if (nums[mid] >= target) {
        right = mid;
      } else {
        left = mid - 1;
      }
    }
    return right;
  }


  // 二分变体4：查找最后一个小于等于给定值的元素
  public int bsearch7(int[] a, int n, int value) {
    int low = 0;
    int high = n - 1;
    while (low <= high) {
      int mid = low + ((high - low) >> 1);
      if (a[mid] > value) {
        high = mid - 1;
      } else {
        if ((mid == n - 1) || (a[mid + 1] > value)) {
          return mid;
        } else {
          low = mid + 1;
        }
      }
    }
    return -1;
  }

  // 查找最后一个小于等于target的元素
  public int search03(int[] nums, int target) {
    int left = -1;
    int right = nums.length - 1;
    while (left < right) {
      int mid = (left + right + 1) / 2;
      if (nums[mid] <= target) {
        left = mid;
      } else {
        right = mid - 1;
      }
    }
    return right;
  }

  public static void main(String[] args) {
    int[] nums = {1, 2, 3, 4, 5, 6, 7, 8};
    int target = 4;

    BinarySearch search = new BinarySearch();
    System.out.println(search.bsearch(nums, 8, target));
    System.out.println(search.search2(nums, target));

  }

}
