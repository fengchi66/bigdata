package com.data.offer02.code;

import java.util.LinkedList;
import java.util.List;

// 允许重复选择元素的组合
// 给定一个没有重复数字的正整数集合，请找出所有元素之和等于某个给定值的所有组合。同一个数字可以在组合中出现任意次。
// 例如，输入整数集合[2，3，5]，元素之和等于8的组合有3个，分别是[2，2，2，2]、[2，3，3]和[3，5]。
public class Solution81 {

  public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> ans = new LinkedList<>();
    LinkedList<Integer> subset = new LinkedList<>();

    // 在数组下标从0开始选择
    dfs(candidates, target, 0, ans, subset);
    return ans;
  }

  private void dfs(int[] candidates, int target, int index, List<List<Integer>> ans,
      LinkedList<Integer> subset) {
    // base case
    if (target == 0) {
      ans.add(new LinkedList<>(subset));
    } else if (target > 0 && index < candidates.length) {
      // 不要当前的元素
      dfs(candidates, target, index +1, ans, subset);
      // 要当前的元素
      subset.add(candidates[index]);
      dfs(candidates, target - candidates[index], index, ans, subset);
      subset.removeLast();
    }
  }
}
