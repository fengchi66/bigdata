package com.data.geektime.week_02;

import java.util.HashMap;
import java.util.Map;

/**
 * 实现一个LRU缓存
 */
class LRUCache {

  // 保护节点
  private Node head;
  private Node tail;
  // map存储key以及Node
  private Map<Integer, Node> map;
  // 缓存大小
  private int capacity;

  public LRUCache(int capacity) {
    head = new Node();
    tail = new Node();
    head.next = tail;
    tail.prev = head;

    this.map = new HashMap<>();
    this.capacity = capacity;

  }

  // 将该key所对应的Node从链表中删除后加入到链表的尾部
  public int get(int key) {
    if (!map.containsKey(key)) {
      return -1;
    }

    Node node = map.get(key);
    // 将节点从链表和map中删除
    removeFromList(node);
    // 将该节点加入链表尾部并重新put进map中
    insertToTail(key, node.value);

    return node.value;
  }

  public void put(int key, int value) {
    if (map.containsKey(key)) {
      Node node = map.get(key);
      removeFromList(node);
    } else { // map中没有key
      if (map.size() == this.capacity) {
        // 删除链表头部
        removeHead(head.next);
        insertToTail(key, value);
      }
      insertToTail(key, value);
    }

  }

  // 将节点从链表和map中删除
  private void removeFromList(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
    this.map.remove(node.key);
  }

  // 加入到链表尾部
  private void insertToTail(int key, int value) {
    Node node = new Node(key, value);

    tail.prev.next = node;
    node.prev = tail.prev;
    node.next = tail;
    tail.prev = node;

    map.put(key, node);
  }

  private void removeHead(Node node) {
    node.prev.next = node.next;
    node.next.prev = node.prev;
  }

  private class Node {

    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
      this.key = key;
      this.value = value;
    }

    public Node() {
    }
  }
}
