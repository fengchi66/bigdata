//给你二叉树的根结点 root ，请你将它展开为一个单链表： 
//
// 
// 展开后的单链表应该同样使用 TreeNode ，其中 right 子指针指向链表中下一个结点，而左子指针始终为 null 。 
// 展开后的单链表应该与二叉树 先序遍历 顺序相同。 
// 
//
// 
//
// 示例 1： 
//
// 
//输入：root = [1,2,5,3,4,null,6]
//输出：[1,null,2,null,3,null,4,null,5,null,6]
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
//输入：root = [0]
//输出：[0]
// 
//
// 
//
// 提示： 
//
// 
// 树中结点数在范围 [0, 2000] 内 
// -100 <= Node.val <= 100 
// 
//
// 
//
// 进阶：你可以使用原地算法（O(1) 额外空间）展开这棵树吗？ 
// Related Topics 栈 树 深度优先搜索 链表 二叉树 👍 982 👎 0

package leetcode.editor.cn;

// 114.二叉树展开为链表
public class Code114_FlattenBinaryTreeToLinkedList {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    // 递归函数：将二叉树展开为链表
    public void flatten(TreeNode root) {
      // base case
      if (root == null) {
        return;
      }

      // 1.将左右子树展开为链表
      flatten(root.left);
      flatten(root.right);

      // 先记住root的左右链表
      TreeNode leftNode = root.left;
      TreeNode rightNode = root.right;

      // 2.先将left接到root的右边
      root.left = null;
      root.right = leftNode;

      // 3.再将right接到后面
      TreeNode cur = root;
      while (cur.right != null) {
        cur = cur.right;
      }
      cur.right = rightNode;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}