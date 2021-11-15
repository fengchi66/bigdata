package com.data.offer02.code;

import com.data.entity.TreeNode;

// 序列化与反序列化二叉树
public class Solution48 {

  // 先序遍历序列化
  public String serialize(TreeNode root) {
    if (root == null) {
      return "#";
    }
    String leftSer = serialize(root.left);
    String rightSer = serialize(root.right);
    // 拼接
    return root.val + "," + leftSer + "," + rightSer;
  }

  // 先序遍历反序列化
  public TreeNode deserialize(String data) {
    String[] nodeStrs = data.split(",");
    int[] i = {0};
    return dfs(nodeStrs, i);
  }

  private TreeNode dfs(String[] nodeStrs, int[] i) {
    String nodeStr = nodeStrs[i[0]];
    i[0]++;

    if ("#".equals(nodeStr)) {
      return null;
    }

    TreeNode node = new TreeNode(Integer.parseInt(nodeStr));
    node.left = dfs(nodeStrs, i);
    node.right = dfs(nodeStrs, i);

    return node;
  }
}
