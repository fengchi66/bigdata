//给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。 
//
// 
//
// 示例： 
//二叉树：[3,9,20,null,null,15,7], 
//
// 
//    3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回其层序遍历结果： 
//
// 
//[
//  [3],
//  [9,20],
//  [15,7]
//]
// 
// Related Topics 树 广度优先搜索 二叉树 👍 1097 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 102.二叉树的层序遍历
public class Code102_BinaryTreeLevelOrderTraversal {
  //leetcode submit region begin(Prohibit modification and deletion)

  class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {
      List<List<Integer>> ans = new LinkedList<>();

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
          ans.add(subset);
          cur = next;
          next = 0;
          subset = new LinkedList<>();
        }
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)
}