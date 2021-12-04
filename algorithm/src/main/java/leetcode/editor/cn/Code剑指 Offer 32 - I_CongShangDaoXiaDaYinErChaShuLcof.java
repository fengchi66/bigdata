//从上到下打印出二叉树的每个节点，同一层的节点按照从左到右的顺序打印。 
//
// 
//
// 例如: 
//给定二叉树: [3,9,20,null,null,15,7], 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
//
// 返回： 
//
// [3,9,20,15,7]
// 
//
// 
//
// 提示： 
//
// 
// 节点总数 <= 1000 
// 
// Related Topics 树 广度优先搜索 二叉树 👍 143 👎 0

package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// 剑指 Offer 32 - I.从上到下打印二叉树
class Code_Offer_CongShangDaoXiaDaYinErChaShuLcof {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode(int x) { val = x; } }
   */
  class Solution {

    // 层序遍历
    public int[] levelOrder(TreeNode root) {
      ArrayList<Integer> ans = new ArrayList<>();
      if (root == null) {
        return new int[]{};
      }

      Queue<TreeNode> queue = new LinkedList<>();
      queue.offer(root);

      while (!queue.isEmpty()) {
        TreeNode node = queue.poll();
        ans.add(node.val);
        if (node.left != null) {
          queue.offer(node.left);
        }
        if (node.right != null) {
          queue.offer(node.right);
        }
      }

      int[] ints = new int[ans.size()];
      int i = 0;
      for (Integer an : ans) {
        ints[i] = an;
        i++;
      }
      return ints;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}