package com.data.offer02.code;

import com.data.entity.DoubleNode;

import java.util.HashMap;
import java.util.Map;

public class Solution31 {
    private DoubleNode head; // 虚拟头节点
    private DoubleNode tail; // 虚拟尾节点
    private Map<Integer, DoubleNode> map;
    int capacity;

    public Solution31(int capacity) {
        map = new HashMap<>();
        head = new DoubleNode(-1, -1);
        tail = new DoubleNode(-1, -1);
        head.next = tail;
        tail.prev = head;

        this.capacity = capacity;
    }

    public int get(int key) {
        DoubleNode node = map.get(key);
        if (node == null)
            return -1; // 当缓存中不存在该key时，返回-1

        moveToTail(node, node.value);
        return node.value;
    }

    public void put(int key, int value) {
        if (map.containsKey(key)) {
            moveToTail(map.get(key), value);
        } else { // map中不包含该key
            if (map.size() == capacity) { // map已经满，需要将链表头元素移除,同时map中移除对应的key
                DoubleNode toBeDeleted = head.next;
                deleteNode(toBeDeleted);
                map.remove(toBeDeleted.key);
            }
            DoubleNode node = new DoubleNode(key, value);
            insertToTail(node);
            map.put(key, node);
        }
    }

    // 首先把它从当前位置删除，然后添加到链表的尾部
    private void moveToTail(DoubleNode node, int newValue) {
        deleteNode(node);
        node.value = newValue;
        insertToTail(node);
    }

    private void deleteNode(DoubleNode node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insertToTail(DoubleNode node) {
        tail.prev.next = node;
        node.prev = tail.prev;
        node.next = tail;
        tail.prev = node;
    }

}
