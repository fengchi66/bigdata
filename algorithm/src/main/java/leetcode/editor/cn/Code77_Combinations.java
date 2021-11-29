//给定两个整数 n 和 k，返回范围 [1, n] 中所有可能的 k 个数的组合。 
//
// 你可以按 任何顺序 返回答案。 
//
// 
//
// 示例 1： 
//
// 
//输入：n = 4, k = 2
//输出：
//[
//  [2,4],
//  [3,4],
//  [2,3],
//  [1,2],
//  [1,3],
//  [1,4],
//] 
//
// 示例 2： 
//
// 
//输入：n = 1, k = 1
//输出：[[1]] 
//
// 
//
// 提示： 
//
// 
// 1 <= n <= 20 
// 1 <= k <= n 
// 
// Related Topics 数组 回溯 👍 781 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

// 77.组合
public class Code77_Combinations {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<List<Integer>> combine(int n, int k) {
      List<List<Integer>> ans = new LinkedList<>();
      LinkedList<Integer> subset = new LinkedList<>();

      // 从数字1开始选择,找长度为2的组合数
      dfs(n, 1, k, ans, subset);
      return ans;
    }

    //
    private void dfs(int n, int index, int k, List<List<Integer>> ans, LinkedList<Integer> subset) {
      // base case
      if (subset.size() == k) {
        ans.add(new LinkedList<>(subset));
      } else if (index <= n && subset.size() < k) {
        // 不要当前的数
        dfs(n, index + 1, k, ans, subset);

        //要当前的数
        subset.add(index);
        dfs(n, index + 1, k, ans, subset);
        subset.removeLast();
      }

    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}