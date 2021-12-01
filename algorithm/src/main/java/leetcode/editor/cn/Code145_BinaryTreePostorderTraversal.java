//给定一个二叉树，返回它的 后序 遍历。 
//
// 示例: 
//
// 输入: [1,null,2,3]  
//   1
//    \
//     2
//    /
//   3 
//
//输出: [3,2,1] 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树 深度优先搜索 二叉树 👍 717 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

// 145.二叉树的后序遍历
public class Code145_BinaryTreePostorderTraversal {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    List<Integer> ans = new LinkedList<>();

    public List<Integer> postorderTraversal(TreeNode root) {
      dfs(root);
      return ans;
    }

    private void dfs(TreeNode root) {
      if (root == null) {
        return;
      }
      dfs(root.left);
      dfs(root.right);
      ans.add(root.val);
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}