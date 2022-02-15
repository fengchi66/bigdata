//给你二叉树的根节点 root ，返回其节点值的 层序遍历 。 （即逐层地，从左到右访问所有节点）。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [3,9,20,null,null,15,7]
//输出：[[3],[9,20],[15,7]]
// 
//
// 示例 2： 
//
// 
//输入：root = [1]
//输出：[[1]]
// 
//
// 示例 3： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [0, 2000] 内 
// -1000 <= Node.val <= 1000 
// 
// Related Topics 树 广度优先搜索 二叉树 👍 1177 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 102.二叉树的层序遍历
class Code_Offer_BinaryTreeLevelOrderTraversal {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    public List<List<Integer>> levelOrder(TreeNode root) {

      // 答案
      List<List<Integer>> ans = new LinkedList<>();
      if (root == null) {
        return ans;
      }

      // 状态
      LinkedList<Integer> subset = new LinkedList<>();
      // 层序遍历中用到的队列
      Queue<TreeNode> queue = new LinkedList<>();

      // 首先将root放到queue中
      queue.offer(root);

      int cur = 1; // 当前层在queue中的元素个数
      int next = 0; // 下一层在queue中的元素个数

      while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        subset.offer(node.val);
        cur--;

        if (node.left != null) {
          queue.offer(node.left);
          next++;
        }
        if (node.right != null) {
          queue.offer(node.right);
          next++;
        }

        if (cur == 0) { // 当前层遍历完了,开始遍历下一层
          ans.add(subset);
          cur = next;
          next = 0;
          // 初始化subset
          subset = new LinkedList<>();
        }
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}