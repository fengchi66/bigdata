package com.data.offer02.code;

import java.util.ArrayList;
import java.util.List;

// 字符串中的所有变位词
public class Solution15 {

  public List<Integer> findAnagrams(String s1, String s2) {
    List<Integer> list = new ArrayList<>();
    if (s1.length() < s2.length()) {
      return list;
    }

    int[] counts = new int[26];
    for (int i = 0; i < s2.length(); i++) {
      counts[s2.charAt(i) - 'a']++;
      counts[s1.charAt(i) - 'a']--;
    }
    if (check(counts)) {
      list.add(0);
    }

    for (int i = s2.length(); i < s1.length(); i++) {
      counts[s2.charAt(i) - 'a']--;
      counts[s2.charAt(i - s1.length()) - 'a']++;
      if (check(counts)) {
        list.add(i - 1);
      }
    }
    return list;
  }

  private boolean check(int[] counts) {
    for (int count : counts) {
      if (count != 0) {
        return false;
      }
    }
    return true;
  }

}
