package com.data.offer02.code;

import com.data.entity.TreeNode;
import java.util.HashMap;
import java.util.Map;

// 向下的路径节点之和：节点和为targetSum的路径个数
public class Solution50 {

  public int pathSum(TreeNode root, int targetSum) {
    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1);

    return dfs(root, targetSum, 0, map);
  }

  // 节点和为targetSum的路径个数: 先序遍历 + 前缀和
  private int dfs(TreeNode root, int targetSum, int path, Map<Integer, Integer> map) {
    // base case
    if (root == null) {
      return 0;
    }

    // 先序遍历
    path += root.val;
    int count = map.getOrDefault(path - targetSum, 0);
    map.put(path, map.getOrDefault(path, 0) + 1);

    // 左右子树
    count += dfs(root.left, targetSum, path, map);
    count += dfs(root.left, targetSum, path, map);

    // 遍历完左右子树后，再次来到了root，将path的数据减去
    map.put(path, map.get(path) - 1);
    return count;
  }

}
