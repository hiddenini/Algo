package com.xz.Trie;

import java.util.HashMap;

public class Trie<V> {
    private int size;
    /**
     * 根节点
     */
    private Node<V> root;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        size = 0;
        root = null;
    }

    public V get(String key) {
        Node<V> node = node(key);
        return node != null && node.word ? node.value : null;
    }

    public boolean contains(String key) {
        Node<V> node = node(key);
        return node != null && node.word;
    }

    public V add(String key, V value) {
        keyCheck(key);
        /**
         * 创建root节点
         */
        if (root == null) {
            root = new Node<>(null);
        }
        Node<V> node = root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            Character c = key.charAt(i);
            boolean emptyChildren = node.children == null;
            Node<V> childNode = emptyChildren ? null : node.children.get(c);
            if (childNode == null) {
                childNode = new Node<>(node);
                childNode.character = c;
                node.children = emptyChildren ? new HashMap<>() : node.children;
                node.children.put(c, childNode);
            }
            node = childNode;
        }

        /**
         * 如果之前就存在该单词
         */
        if (node.word) {
            V oldValue = node.value;
            node.value = value;
            return oldValue;
        }

        node.word = true;
        node.value = value;
        size++;
        return null;
    }


    public V remove(String key) {
        //找到改key对应的最后一个节点
        Node<V> node = node(key);
        //如果node不是单词结尾
        if (node == null || !node.word) return null;
        //往下走一定会删除了,size先减一
        size--;
        V oldValue = node.value;
        /**
         * 如果还有子节点,那么将当节点的word属性置为false,将value清空
         */
        if (node.children != null && !node.children.isEmpty()) {
            node.word = false;
            node.value = null;
            return oldValue;
        }

        /**
         * 如果没有子节点
         */
        Node<V> parent = null;
        while ((parent = node.parent) != null) {
            parent.children.remove(node.character);
            //如果父节点删除该节点之后还有其他子节点或者父节点是某个单词的结尾，那么直接break
            if (!parent.children.isEmpty() || parent.word) break;
            node = parent;
        }
        return oldValue;
    }

    /**
     * 是否有prefix前缀的单词
     *
     * @param prefix
     * @return
     */
    public boolean startsWith(String prefix) {
        return node(prefix) != null;
    }

    /**
     * 根据给定的key找到对应的节点
     *
     * @param key
     * @return
     */
    private Node<V> node(String key) {
        keyCheck(key);
        Node<V> node = root;
        int length = key.length();
        for (int i = 0; i < length; i++) {
            if (node == null || node.children == null || node.children.isEmpty()) return null;
            Character c = key.charAt(i);
            node = node.children.get(c);

        }
        return node;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("key must not be empty");
        }
    }

    private static class Node<V> {
        Character character;
        Node<V> parent;
        HashMap<Character, Node<V>> children;
        V value;
        boolean word; // 是否为单词的结尾（是否为一个完整的单词）

        public Node(Node<V> parent) {
            this.parent = parent;
        }
    }
}
