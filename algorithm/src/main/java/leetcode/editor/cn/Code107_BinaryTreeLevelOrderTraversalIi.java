//给定一个二叉树，返回其节点值自底向上的层序遍历。 （即按从叶子节点所在层到根节点所在的层，逐层从左向右遍历） 
//
// 例如： 
//给定二叉树 [3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其自底向上的层序遍历为： 
//
// 
//[
//  [15,7],
//  [9,20],
//  [3]
//]
// 
// Related Topics 树 广度优先搜索 二叉树 👍 515 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

// 107.二叉树的层序遍历 II
public class Code107_BinaryTreeLevelOrderTraversalIi {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    public List<List<Integer>> levelOrderBottom(TreeNode root) {
      List<List<Integer>> ans = new LinkedList<>();
      Stack<List<Integer>> stack = new Stack<>();

      if (root == null) {
        return ans;
      }

      LinkedList<Integer> subset = new LinkedList<>();
      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root);

      int cur = 1; // 队列中当前层的元素个数
      int next = 0; // 队列中下一层的元素个数
      while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        subset.add(node.val);
        cur--;

        if (node.left != null) {
          queue.offer(node.left);
          next++;
        }
        if (node.right != null) {
          queue.offer(node.right);
          next++;
        }

        // 当前层已经遍历完了
        if (cur == 0) {
          stack.add(subset);
          cur = next;
          next = 0;
          subset = new LinkedList<>();
        }
      }

      // 把stack中的元素导出来
      while (!stack.isEmpty()) {
        ans.add(stack.pop());
      }

      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}