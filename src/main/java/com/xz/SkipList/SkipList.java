package com.xz.SkipList;

import java.util.Comparator;

/**
 * 跳表
 *
 * @param <K>
 * @param <V>
 */
public class SkipList<K, V> {
    /**
     * 最大层数,类似redis
     */
    private static final int MAX_LEVEL = 32;

    /**
     * 每个新节点的层数,随机决定，系数取redis中的0.25
     */
    private static final double P = 0.25;

    private Comparator<K> comparator;

    //有效层数 第0层，第1层...
    private int level;

    /**
     * 类似头节点，不存放K-V
     */
    private Node<K, V> first;

    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;
        first = new Node<>(null, null, MAX_LEVEL);
        first.nexts = new Node[MAX_LEVEL];
    }

    private int size;

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public V put(K key, V value) {
        keyCheck(key);
        Node<K, V> node = first;
        Node<K, V>[] prevs = new Node[level];
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {
                //节点是存在的,则覆盖value
                V oldV = node.nexts[i].value;
                node.nexts[i].value = value;
                //返回原节点的value
                return oldV;
            }
            //保存每一层的前驱节点
            prevs[i] = node;

        }
        int newLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newLevel);
        //设置新节点的前驱和后继
        for (int i = 0; i < newLevel; i++) {
            //如果新节点随机得到的层数大于现有的有效层数,那么直接将头节点指向新节点,头节点初始化时level=MAX_LEVEL
            if (i >= level) {
                first.nexts[i] = newNode;
            } else {
                //将新节点的后继指向前驱的后继
                newNode.nexts[i] = prevs[i].nexts[i];
                //将前驱的后继指向新节点
                prevs[i].nexts[i] = newNode;
            }

        }
        //节点数量加1
        size++;
        //设置有效层数
        level = Math.max(level, newLevel);
        return null;
    }

    /**
     * ① 从顶层链表的首元素开始，从左往右搜索，直至找到一个大于或等于目标的元素，或者到达当前层链表的尾部
     * ② 如果该元素等于目标元素，则表明该元素已被找到
     * ③ 如果该元素大于目标元素或已到达链表的尾部，则退回到当前层的前一个元素，然后转入下一层进行搜索
     *
     * @param key
     * @return
     */
    public V get(K key) {
        keyCheck(key);
        Node<K, V> node = first;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) return node.nexts[i].value;
        }
        return null;
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL) {
            level++;
        }
        return level;
    }

    public V remove(K key) {
        keyCheck(key);
        Node<K, V> node = first;
        Node<K, V>[] prevs = new Node[level];
        boolean exist = false;
        for (int i = level - 1; i >= 0; i--) {
            int cmp = -1;
            while (node.nexts[i] != null && (cmp = compare(key, node.nexts[i].key)) > 0) {
                node = node.nexts[i];
            }
            //保存每一层的前驱节点，找到key时也继续循环,找到所有的前驱节点
            prevs[i] = node;
            if (cmp == 0) exist = true;
        }
        //如果key不存在
        if (!exist) return null;

        //需要删除的节点
        Node<K, V> removedNode = node.nexts[0];

        size--;

        //设置后继
        for (int i = 0; i < removedNode.nexts.length; i++) {
            prevs[i].nexts[i] = removedNode.nexts[i];
        }
        //更新有效层数
        int newLevel = level;
        while (--newLevel >= 0 && first.nexts[newLevel] == null) {
            level = newLevel;
        }
        return removedNode.value;

    }

    private void keyCheck(K key) {
        if (key == null) throw new IllegalArgumentException("key must not be null");
    }

    private int compare(K k1, K k2) {
        return comparator != null ? comparator.compare(k1, k2) : ((Comparable<K>) k1).compareTo(k2);
    }

    private static class Node<K, V> {
        K key;
        V value;
        int level;
        //跳表可能有多个next,层次
        private Node<K, V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }

        @Override
        public String toString() {
            return key + ":" + value + "_" + nexts.length;
        }

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共" + level + "层").append("\n");
        for (int i = level - 1; i >= 0; i--) {
            Node<K, V> node = first;
            while (node.nexts[i] != null) {
                sb.append(node.nexts[i]);
                sb.append(" ");
                node = node.nexts[i];
            }
            sb.append("\n");
        }
        return sb.toString();
    }

}
