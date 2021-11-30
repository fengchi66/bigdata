//根据一棵树的中序遍历与后序遍历构造二叉树。 
//
// 注意: 
//你可以假设树中没有重复的元素。 
//
// 例如，给出 
//
// 中序遍历 inorder = [9,3,15,20,7]
//后序遍历 postorder = [9,15,7,20,3] 
//
// 返回如下的二叉树： 
//
//     3
//   / \
//  9  20
//    /  \
//   15   7
// 
// Related Topics 树 数组 哈希表 分治 二叉树 👍 623 👎 0

package leetcode.editor.cn;

// 106.从中序与后序遍历序列构造二叉树
public class Code106_ConstructBinaryTreeFromInorderAndPostorderTraversal {
  //leetcode submit region begin(Prohibit modification and deletion)

  /**
   * Definition for a binary tree node. public class TreeNode { int val; TreeNode left; TreeNode
   * right; TreeNode() {} TreeNode(int val) { this.val = val; } TreeNode(int val, TreeNode left,
   * TreeNode right) { this.val = val; this.left = left; this.right = right; } }
   */
  class Solution {

    int[] inorder;
    int[] postorder;

    public TreeNode buildTree(int[] inorder, int[] postorder) {
      this.inorder = inorder;
      this.postorder = postorder;

      return build(0, inorder.length - 1, 0, postorder.length - 1);
    }

    // 由inorder[l1...r1]和postorder[l2...r2]来创建二叉树
    private TreeNode build(int l1, int r1, int l2, int r2) {
      if (l2 > r2) {
        return null;
      }

      TreeNode root = new TreeNode(postorder[r2]);

      // 找root在inorder中的位置
      int mid = l1;
      while (inorder[mid] != root.val) {
        mid++;
      }

      // 构建左右子树
      root.left = build(l1, mid - 1, l2, l2 + mid - l1 - 1);
      root.right = build(mid + 1, r1, l2 + mid - l1, r2 - 1);
      return root;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}