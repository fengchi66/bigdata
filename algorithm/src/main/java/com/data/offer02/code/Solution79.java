package com.data.offer02.code;

import java.util.LinkedList;
import java.util.List;

// 所有子集
public class Solution79 {

  public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> ans = new LinkedList<>();
    if (nums.length == 0) {
      return ans;
    }
    dfs(nums, ans, 0, new LinkedList<Integer>());
    return ans;
  }

  /**
   * @param nums   数组
   * @param ans    答案
   * @param index  数组下标
   * @param subset 状态
   */
  private void dfs(int[] nums, List<List<Integer>> ans, int index, LinkedList<Integer> subset) {
    // base case
    if (index == nums.length) {
      ans.add(new LinkedList<>(subset));
    } else if (index < nums.length) {
      // 不选择当前的数字
      dfs(nums, ans, index + 1, subset);

      // 选择当前的数字
      subset.add(nums[index]);
      dfs(nums, ans, index + 1, subset);
      // 遍历完子树后回到当前位置
      subset.removeLast();
    }
  }
}
