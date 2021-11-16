//给定一个二叉树的根节点 root ，返回它的 中序 遍历。 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,null,2,3]
//输出：[1,3,2]
// 
//
// 示例 2： 
//
// 
//输入：root = []
//输出：[]
// 
//
// 示例 3： 
//
// 
//输入：root = [1]
//输出：[1]
// 
//
// 示例 4： 
//
// 
//输入：root = [1,2]
//输出：[2,1]
// 
//
// 示例 5： 
//
// 
//输入：root = [1,null,2]
//输出：[1,2]
// 
//
// 
//
// 提示： 
//
// 
// 树中节点数目在范围 [0, 100] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶: 递归算法很简单，你可以通过迭代算法完成吗？ 
// Related Topics 栈 树 深度优先搜索 二叉树 👍 1163 👎 0

package leetcode.editor.cn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Code94_BinaryTreeInorderTraversal {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    public List<Integer> inorderTraversal(TreeNode root) {
      ArrayList<Integer> ans = new ArrayList<>();
      TreeNode cur = root;
      Stack<TreeNode> stack = new Stack<>();

      while (cur != null || !stack.empty()) {
        while (cur != null) {
          // 将cur以及它的整个左子树加入到stack中
          stack.push(cur);
          cur = cur.left;
        }
        // 从stack中取出元素
        cur = stack.pop();
        ans.add(cur.val);
        cur = cur.right;
      }
      return ans;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}