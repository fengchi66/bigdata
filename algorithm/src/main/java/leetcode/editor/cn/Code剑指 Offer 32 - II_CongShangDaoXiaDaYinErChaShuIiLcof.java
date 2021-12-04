//从上到下按层打印二叉树，同一层的节点按从左到右的顺序打印，每一层打印到一行。 
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
// 返回其层次遍历结果： 
//
// [
//  [3],
//  [9,20],
//  [15,7]
//]
// 
//
// 
//
// 提示： 
//
// 
// 节点总数 <= 1000 
// 
//
// 注意：本题与主站 102 题相同：https://leetcode-cn.com/problems/binary-tree-level-order-
//traversal/ 
// Related Topics 树 广度优先搜索 二叉树 👍 160 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// 剑指 Offer 32 - II.从上到下打印二叉树 II
class Code_Offer_CongShangDaoXiaDaYinErChaShuIiLcof {
  //leetcode submit region begin(Prohibit modification and deletion)

  class Solution {

    // 使用一个队列来实现层序遍历
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