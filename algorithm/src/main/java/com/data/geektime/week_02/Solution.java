
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

}
