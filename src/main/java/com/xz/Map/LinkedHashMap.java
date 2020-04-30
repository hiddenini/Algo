package com.xz.Map;

/**
 * @author xz
 * @date 2020/4/30 15:09
 **/

public class LinkedHashMap<K,V> extends HashMap<K,V> {
    LinkedNode<K, V> first;
    LinkedNode<K, V> last;

    /**
     * 红黑树节点
     *
     * @param <K>
     * @param <V>
     */
     private static class LinkedNode<K, V> extends HashMap.Node<K, V>{
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
        if (first==null){
            first=last=node;
        }else{
            last.next=node;
            node.prev=last;
            last=node;

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
