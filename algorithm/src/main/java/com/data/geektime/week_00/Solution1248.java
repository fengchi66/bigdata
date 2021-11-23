package com.data.geektime.week_00;

import java.util.HashMap;

// 优美子数组
public class Solution1248 {
    public int numberOfSubarrays(int[] nums, int k) {
        // map中存储前缀和以及次数
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int count = 0;
        int sum = 0;

        for (int num : nums) {
            // 奇数则sum += 1
            sum += num % 2 == 0 ? 0 : 1;
            // 和为k的子数组也就等于前缀和为sum-k的子数组个数
            count += map.getOrDefault(sum - k, 0);
            // 更新当前前缀和以及次数
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }
}
