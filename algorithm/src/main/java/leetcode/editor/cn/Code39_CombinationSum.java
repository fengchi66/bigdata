//给定一个无重复元素的正整数数组 candidates 和一个正整数 target ，找出 candidates 中所有可以使数字和为目标数 target 的
//唯一组合。 
//
// candidates 中的数字可以无限制重复被选取。如果至少一个所选数字数量不同，则两种组合是唯一的。 
//
// 对于给定的输入，保证和为 target 的唯一组合数少于 150 个。 
//
// 
//
// 示例 1： 
//
// 
//输入: candidates = [2,3,6,7], target = 7
//输出: [[7],[2,2,3]]
// 
//
// 示例 2： 
//
// 
//输入: candidates = [2,3,5], target = 8
//输出: [[2,2,2,2],[2,3,3],[3,5]] 
//
// 示例 3： 
//
// 
//输入: candidates = [2], target = 1
//输出: []
// 
//
// 示例 4： 
//
// 
//输入: candidates = [1], target = 1
//输出: [[1]]
// 
//
// 示例 5： 
//
// 
//输入: candidates = [1], target = 2
//输出: [[1,1]]
// 
//
// 
//
// 提示： 
//
// 
// 1 <= candidates.length <= 30 
// 1 <= candidates[i] <= 200 
// candidate 中的每个元素都是独一无二的。 
// 1 <= target <= 500 
// 
// Related Topics 数组 回溯 👍 1634 👎 0

package leetcode.editor.cn;

import java.util.LinkedList;
import java.util.List;

public class Code39_CombinationSum {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
      List<List<Integer>> ans = new LinkedList<>();
      LinkedList<Integer> subset = new LinkedList<>();

      dfs(ans, subset, candidates, 0, target);
      return ans;
    }

    // 在数组中索引为index位置开始寻找和为target的数字可以重复的组合
    private void dfs(List<List<Integer>> ans, LinkedList<Integer> subset, int[] candidates,
        int index, int target) {
      // base case,已经凑满了和为target了
      if (target == 0) {
        ans.add(new LinkedList<>(subset));
      } else if (index < candidates.length && target > 0) {
        // 还要继续寻找元素
        // 不要当前的元素
        dfs(ans, subset, candidates, index + 1, target);

        // 要当前的元素
        subset.add(candidates[index]);
        dfs(ans, subset, candidates, index, target - candidates[index]);
        subset.removeLast();
      }
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}