package com.xz.Map;

import java.util.Objects;

/**
 * @author xz
 * @date 2020/4/30 15:09
 **/

public class LinkedHashMap<K, V> extends HashMap<K, V> {
    LinkedNode<K, V> first;
    LinkedNode<K, V> last;

    @Override
    public void clear() {
        super.clear();
        first = null;
        last = null;
    }

    @Override
    public boolean containsValue(V value) {
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (Objects.equals(value,node.value)) return true;
            node = node.next;
        }
        return false;
    }

    @Override
    protected void afterRemove(Node<K, V> willNode, Node<K, V> removedNode) {
        LinkedNode<K, V> node1 = (LinkedNode<K, V>) willNode;
        LinkedNode<K, V> node2 = (LinkedNode<K, V>) removedNode;

        if (node1 != node2) {
            //交换2个node在链表中的位置

            //先交换prev
            LinkedNode<K, V> temp=node1.prev;
            node1.prev=node2.prev;
            node2.prev=temp;
            /**
             * 交换后需要判断下first指针是否需要变化
             */
            if (node1.prev==null){
                first=node1;
            }else{
                node1.prev.next=node1;
            }
            if (node2.prev==null){
                first=node2;
            }else{
                node2.prev.next=node2;
            }


            //再交换next
            temp=node1.next;
            node1.next=node2.next;
            node2.next=temp;
            /**
             * 交换后需要判断下last指针是否需要变化
             */
            if (node1.next==null){
                last=node1;
            }else{
                node1.next.prev=node1;
            }
            if (node2.next==null){
                last=node2;
            }else{
                node2.next.prev=node2;
            }
        }
        LinkedNode<K, V> prev = node2.prev;
        LinkedNode<K, V> next = node2.next;
        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
        }
        if (next == null) {
            last = prev;
        } else {
            next.prev = prev;
        }
    }

    /**
     * 红黑树节点
     *
     * @param <K>
     * @param <V>
     */
    private static class LinkedNode<K, V> extends HashMap.Node<K, V> {
        LinkedNode<K, V> prev;
        LinkedNode<K, V> next;

        public LinkedNode(K key, V value, HashMap.Node<K, V> parent) {
            super(key, value, parent);
        }
    }

    @Override
    protected Node<K, V> createNode(K key, V value, Node<K, V> parent) {
        LinkedNode node = new LinkedNode(key, value, parent);
        /**
         * 将链表串起来
         */
        if (first == null) {
            first = last = node;
        } else {
            last.next = node;
            node.prev = last;
            last = node;

        }
        return node;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (visitor == null) return;
        LinkedNode<K, V> node = first;
        while (node != null) {
            if (visitor.visit(node.key, node.value)) return;
            node = node.next;
        }
    }


}
