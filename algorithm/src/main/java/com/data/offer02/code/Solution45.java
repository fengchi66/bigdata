package com.data.offer02.code;

import com.data.entity.TreeNode;
import java.util.LinkedList;
import java.util.Queue;

// 二叉树最底层最左边的值
public class Solution45 {

  public int findBottomLeftValue(TreeNode root) {
    // 当每一层遍历完的时候，当前出队的元素的下一个元素就是最左边的值
    Queue<TreeNode> queue1 = new LinkedList<>();
    Queue<TreeNode> queue2 = new LinkedList<>();
    queue1.offer(root);
    int leftValue = root.val;

    while (!queue1.isEmpty()) {
      TreeNode node = queue1.poll();
      if (node.left != null) {
        queue2.offer(node.left);
      }
      if (node.right != null) {
        queue2.offer(node.right);
      }

      if (queue1.isEmpty()) { // 当前层遍历完了
        // queue2的第一个元素，就是下一层最左边的
        if (!queue2.isEmpty()) {
          TreeNode node1 = queue2.peek();
          leftValue = node1.val;
        }

        queue1 = queue2;
        queue2 = new LinkedList<>();
      }
    }
    return leftValue;
  }

}
