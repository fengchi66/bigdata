//给定一个二叉树的 根节点 root，想象自己站在它的右侧，按照从顶部到底部的顺序，返回从右侧所能看到的节点值。 
//
// 
//
// 示例 1: 
//
// 
//
// 
//输入: [1,2,3,null,5,null,4]
//输出: [1,3,4]
// 
//
// 示例 2: 
//
// 
//输入: [1,null,3]
//输出: [1,3]
// 
//
// 示例 3: 
//
// 
//输入: []
//输出: []
// 
//
// 
//
// 提示: 
//
// 
// 二叉树的节点个数的范围是 [0,100] 
// -100 <= Node.val <= 100 
// 
// Related Topics 树 深度优先搜索 广度优先搜索 二叉树 👍 573 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 199.二叉树的右视图
public class Code199_BinaryTreeRightSideView {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    List<Integer> ans = new LinkedList<>();

    // 每一层最右边的一个数
    public List<Integer> rightSideView(TreeNode root) {
      if (root == null) {
        return ans;
      }
      // 层序遍历：当前层遍历完的时候，此时遍历的元素就是随后一个元素
      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root);
      int cur = 1;
      int next = 0;

      while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        cur--;

        if (node.left != null) {
          queue.offer(node.left);
          next++;
        }
        if (node.right != null) {
          queue.offer(node.right);
          next++;
        }

        if (cur == 0) {
          cur = next;
          next = 0;
          ans.add(node.val);
        }
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}