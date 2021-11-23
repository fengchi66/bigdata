package com.data.offer02.code;

import java.util.LinkedList;
import java.util.List;

// 包含k个元素的组合
public class Solution80 {

  public List<List<Integer>> combine(int n, int k) {
    List<List<Integer>> ans = new LinkedList<>();
    LinkedList<Integer> subset = new LinkedList<>();

    // 在数字[1...n]中选出k和数，从1开始
    dfs(n, k, 1, ans, subset);
    return ans;
  }

  private void dfs(int n, int k, int index, List<List<Integer>> ans, LinkedList<Integer> subset) {
    // base case
    if (subset.size() == k) {
      ans.add(new LinkedList<>(subset));
    } else if (index <= n) {
      // 不要当前元素
      dfs(n, k, index + 1, ans, subset);

      // 要当前元素
      subset.add(index);
      dfs(n, k, index + 1, ans, subset);
      subset.removeLast();
    }
  }
}