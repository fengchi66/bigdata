package com.data.offer02.code;

import com.data.entity.TreeNode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 二叉树每层的最大值
public class Solution44 {

  // 使用一个队列实现二叉树的层序遍历(需要知道每一层的开始和结束)
  public List<Integer> largestValues(TreeNode root) {
    ArrayList<Integer> ans = new ArrayList<>();
    if (root == null) {
      return ans;
    }

    Queue<TreeNode> queue = new LinkedList<>();
    int current = 0; // 当前层在队列中的节点数量
    int next = 0; // 下一层在二叉树中的节点数量
    int maxValue = Integer.MIN_VALUE;

    queue.offer(root);
    current = 1;

    while (!queue.isEmpty()) {
      TreeNode node = queue.poll();
      current--;
      maxValue = Math.max(maxValue, node.val);

      if (node.left != null) {
        queue.offer(node.left);
        next++;
      }
      if (node.right != null) {
        queue.offer(node.right);
        next++;
      }

      if (current == 0) { // 说明当前层遍历完了
        ans.add(maxValue);
        maxValue = Integer.MIN_VALUE;
        current = next;
        next = 0;
      }
    }
    return ans;
  }

  // 使用2个队列实现二叉树的层序遍历
  public List<Integer> largestValues2(TreeNode root) {
    ArrayList<Integer> ans = new ArrayList<>();
    if (root == null) {
      return ans;
    }

    Queue<TreeNode> queue1 = new LinkedList<>(); // 当前层
    Queue<TreeNode> queue2 = new LinkedList<>(); // 下一层
    int maxValue = Integer.MIN_VALUE;

    queue1.offer(root);

    while (!queue1.isEmpty()) {
      TreeNode node = queue1.poll();
      maxValue = Math.max(maxValue, node.val);

      if (node.left != null) {
        queue2.offer(node.left);
      }
      if (node.right != null) {
        queue2.offer(node.right);
      }

      if (queue1.isEmpty()) { // 说明当前层遍历完了
        ans.add(maxValue);
        maxValue = Integer.MIN_VALUE;
        queue1 = queue2;
        queue2 = new LinkedList<>();
      }
    }
    return ans;
  }

}
