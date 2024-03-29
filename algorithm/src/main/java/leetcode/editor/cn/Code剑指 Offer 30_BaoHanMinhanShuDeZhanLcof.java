//定义栈的数据结构，请在该类型中实现一个能够得到栈的最小元素的 min 函数在该栈中，调用 min、push 及 pop 的时间复杂度都是 O(1)。 
//
// 
//
// 示例: 
//
// MinStack minStack = new MinStack();
//minStack.push(-2);
//minStack.push(0);
//minStack.push(-3);
//minStack.min();   --> 返回 -3.
//minStack.pop();
//minStack.top();      --> 返回 0.
//minStack.min();   --> 返回 -2.
// 
//
// 
//
// 提示： 
//
// 
// 各函数的调用总次数不超过 20000 次 
// 
//
// 
//
// 注意：本题与主站 155 题相同：https://leetcode-cn.com/problems/min-stack/ 
// Related Topics 栈 设计 👍 235 👎 0

package leetcode.editor.cn;

import java.util.Stack;

// 剑指 Offer 30.包含min函数的栈
class Code_Offer_BaoHanMinhanShuDeZhanLcof {

  //leetcode submit region begin(Prohibit modification and deletion)
  class MinStack {

    // 用一个栈来记录前缀最小值
    Stack<Integer> preMin = new Stack<>();
    Stack<Integer> stack = new Stack<>();

    public MinStack() {

    }

    public void push(int x) {
      stack.push(x);
      // pre记录的是前缀最小值
      if (preMin.empty()) {
        preMin.push(x);
      } else {
        preMin.push(Math.min(preMin.peek(), x));
      }
    }

    public void pop() {
      preMin.pop();
      stack.pop();
    }

    public int top() {
      return stack.peek();
    }

    public int min() {
      return preMin.peek();
    }
  }

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.min();
 */
//leetcode submit region end(Prohibit modification and deletion)

}