package com.data.offer02.code;

import java.util.LinkedList;
import java.util.Queue;

// 窗口最近请求次数
public class Solution42 {

  Queue<Integer> times;
  int windowSize;

  public Solution42() {
    times = new LinkedList<>();
    windowSize = 3000;
  }

  public int ping(int t) {
    times.offer(t);
    // 添加新的元素后，要始终维持窗口间隔不大于3000
    while (times.peek() + windowSize < t) {
      times.poll();
    }
    // 返回窗口内元素个数
    return times.size();
  }

}
