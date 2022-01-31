
package com.data.geektime.week_02;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Solution {

  // 01、两数之和
  public int[] twoSum(int[] nums, int target) {
    HashMap<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < nums.length; i++) {
      if (map.containsKey(target - nums[i])) {
        return new int[]{map.get(target - nums[i]), i};
      }
      map.put(nums[i], i);
    }
    return new int[]{0};
  }

  // 49、字母异位词分组: 哈希表 + 排序
  public List<List<String>> groupAnagrams(String[] strs) {
    HashMap<String, List<String>> map = new HashMap<>();

    for (String str : strs) {
      char[] chars = str.toCharArray();
      Arrays.sort(chars);

      String key = new String(chars);
      List<String> list = map.getOrDefault(key, new LinkedList<>());
      list.add(str);

      map.put(key, list);
    }

    return new LinkedList<>(map.values());
  }

  // 1248、统计优美子数组:奇数个数为k的子数组
  public int numberOfSubarrays(int[] nums, int k) {
    // 存储前缀和以及次数
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    int count = 0;
    int sum = 0;

    for (int num : nums) {
      // Si - Sj-1 = k,即固定i位置时，看前缀和数组中多少个前缀和为sum - k
      sum += num % 2 == 1 ? 1 : 0;
      count += map.getOrDefault(sum - k, 0);
      map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return count;
  }

  // 53. 最大子序和:动态规划
  public int maxSubArray(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    int n = nums.length;
    // dp[i]表示在i位置时的最大子序和
    int[] dp = new int[n];
    dp[0] = nums[0];
    int maxSum = nums[0];

    for (int i = 1; i < n; i++) {
      // 在i位置时，有两个选择：要或者不要i之前的元素
      dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
      maxSum = Math.max(maxSum, dp[i]);
    }
    return maxSum;
  }

  public int maxSubArray2(int[] nums) {
    if (nums == null || nums.length == 0) {
      return 0;
    }

    int n = nums.length;
    // 前缀和数组
    int[] s = new int[n + 1];
    // 前缀最小值数组
    int[] preMin = new int[n + 1];

    for (int i = 1; i <= n; i++) {
      s[i] = s[i - 1] + nums[i - 1];
      preMin[i] = Math.min(preMin[i - 1], s[i]);
    }

    int ans = Integer.MIN_VALUE;
    for (int i = 1; i <= n; i++) {
      ans = Math.max(ans, s[i] - preMin[i - 1]);
    }
    return ans;
  }


}
