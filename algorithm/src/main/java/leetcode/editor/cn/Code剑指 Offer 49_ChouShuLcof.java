//我们把只包含质因子 2、3 和 5 的数称作丑数（Ugly Number）。求按从小到大的顺序的第 n 个丑数。 
//
// 
//
// 示例: 
//
// 输入: n = 10
//输出: 12
//解释: 1, 2, 3, 4, 5, 6, 8, 9, 10, 12 是前 10 个丑数。 
//
// 说明: 
//
// 
// 1 是丑数。 
// n 不超过1690。 
// 
//
// 注意：本题与主站 264 题相同：https://leetcode-cn.com/problems/ugly-number-ii/ 
// Related Topics 哈希表 数学 动态规划 堆（优先队列） 👍 249 👎 0

package leetcode.editor.cn;

import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

// 剑指 Offer 49.丑数
class Code_Offer_ChouShuLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class Solution {

    public int nthUglyNumber(int n) {
      int[] factors = {2, 3, 5};
      Set<Long> seen = new HashSet<Long>();
      PriorityQueue<Long> heap = new PriorityQueue<Long>();
      seen.add(1L);
      heap.offer(1L);
      int ugly = 0;
      for (int i = 0; i < n; i++) {
        long curr = heap.poll();
        ugly = (int) curr;
        for (int factor : factors) {
          long next = curr * factor;
          if (seen.add(next)) {
            heap.offer(next);
          }
        }
      }
      return ugly;
    }
  }
//leetcode submit region end(Prohibit modification and deletion)

}