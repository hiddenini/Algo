package com.xz.Map;

import com.xz.BinaryTree.printer.BinaryTreeInfo;
import com.xz.BinaryTree.printer.BinaryTrees;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

public class HashMap<K, V> implements Map<K, V> {
    private static final boolean RED = false;
    private static final boolean BLACK = true;

    private int size;
    private Node<K, V>[] table;
    private static final int DEFAULT_CAPACITY = 1 << 4;

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        size = 0;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
    }

    @Override
    public V put(K key, V value) {
        int index = index(key);
        //取出index位置的红黑树的root节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = new Node<>(key, value, null);
            table[index] = root;
            size++;
            //修复红黑树性质
            afterPut(root);
            return null;
        }
        //添加新的节点到红黑树上
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        int h1 = key == null ? 0 : key.hashCode();
        while (node != null) {
            cmp = compare(key, node.key, h1, node.hash);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等,如果是基础数据类型可以什么都不做,如果是自定义数据(Person)是否需要覆盖，看你自己吧
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        // 新添加节点之后的处理
        afterPut(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = node(key);
        return node != null ? node.value : null;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        if (size == 0) return false;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Queue<Node<K, V>> queue = new LinkedList<>();
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (Objects.equals(value, node.value)) return true;
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.left != null) {
                    queue.offer(node.left);
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        if (size == 0 || visitor == null) return;
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Queue<Node<K, V>> queue = new LinkedList<>();
            queue.offer(table[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.poll();
                if (visitor.visit(node.key, node.value)) return;

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
    }

    /**
     * 打印出每一颗红黑树
     */
    public void print() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            final Node<K, V> root = table[i];
            System.out.println("【index = " + i + "】");
            BinaryTrees.println(new BinaryTreeInfo() {
                @Override
                public Object string(Object node) {
                    return node;
                }

                @Override
                public Object root() {
                    return root;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }
            });
            System.out.println("---------------------------------------------------");
        }
    }

    private V remove(Node<K, V> node) {
        if (node == null) return null;
        ;
        V oldValue = node.value;
        size--;
        //度为2的节点
        if (node.hasTwoChildren()) {
            //找到后继节点
            Node<K, V> s = successor(node);
            //用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            //删除后继节点
            node = s;
        }
        //删除node节点(node的度必然是1或者0)
        Node<K, V> replace = node.left != null ? node.left : node.right;
        int index = index(node);
        if (replace != null) {
            //node为度为1的节点
            //更改parent
            replace.parent = node.parent;
            //更改parent的left or right的指向
            if (node.parent == null) {
                //node是度为1的节点并且是根节点
                table[index] = replace;
            } else if (node == node.parent.left) {
                node.parent.left = replace;
            } else {
                node.parent.right = replace;
            }
            //删除节点之后的处理,node被删除时node的parent指针还是存在的
            afterRemove(node, replace);
        } else if (node.parent == null) {
            //node为度为0的节点,且为根节点
            table[index] = null;
            //删除节点之后的处理
            afterRemove(node, null);
        } else {
            //node为度为0的节点,但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else {
                node.parent.right = null;
            }
            //删除节点之后的处理
            afterRemove(node, null);
        }
        return oldValue;
    }


    protected void afterRemove(Node<K, V> node, Node<K, V> replace) {
        //如果被删除的节点是红色
        if (isRed(node)) return;

        //用于取代node的节点是红色
        if (isRed(replace)) {
            black(replace);
            return;
        }
        Node<K, V> parent = node.parent;
        //删除根节点
        if (parent == null) return;
        ;

        //删除的黑色叶子节点
        /**
         * 这种判断是错误的,因为如果来到这里说明删除的一定是叶子节点，所以parent的left或者right被置为null了
         *
         * 可以通过判断被删除的node是左还是右
         */
        //Node<E> sibling = node.sibling();
        //可能是叶子节点，也可能是持续下溢的节点,所以2个判断
        Boolean left = parent.left == null || node.isLeftChild();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { //和else完全对称(方向)
            //被删除节点在左边,兄弟节点在右边
            if (isRed(sibling)) {
                //兄弟节点是红色,要转换成黑色的情况
                black(sibling);
                red(parent);
                rotateLeft(parent);
                //更换兄弟节点
                sibling = parent.right;
            }
            //能来到这里，说明兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点,父节点要向下跟兄弟节点合并(下溢)
                boolean parentIsBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentIsBlack) {
                    //持续下溢
                    afterRemove(parent, null);
                }
            } else {
                //至少有一个红色子节点,需要向兄弟节点借元素
                if (isBlack(sibling.right)) {
                    //先对兄弟进行左旋转(时期演变成后面两种情况)
                    rotateRight(sibling);
                    //重要,需要切换兄弟节点
                    sibling = parent.right;
                }
                //继承parent的颜色
                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                rotateLeft(parent);

            }
        } else {
            //被删除节点在右边,兄弟节点在左边
            if (isRed(sibling)) {
                //兄弟节点是红色,要转换成黑色的情况
                black(sibling);
                red(parent);
                rotateRight(parent);
                //更换兄弟节点
                sibling = parent.left;
            }
            //能来到这里，说明兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                //兄弟节点没有一个红色子节点,父节点要向下跟兄弟节点合并(下溢)
                boolean parentIsBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentIsBlack) {
                    //持续下溢
                    afterRemove(parent, null);
                }
            } else {
                //至少有一个红色子节点,需要向兄弟节点借元素
                if (isBlack(sibling.left)) {
                    //先对兄弟进行左旋转(时期演变成后面两种情况)
                    rotateLeft(sibling);
                    //重要,需要切换兄弟节点
                    sibling = parent.left;
                }
                rotateRight(parent);
                //继承parent的颜色
                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);

            }

        }

    }


    /**
     * 获取节点的后继节点
     *
     * @param node
     * @return
     */
    private Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;
        /**
         * 如果节点的右子树不为空，则找到右节点，一直往左，直到为空
         */
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }
        //node.parent==null or node=node.parent.left;两种情况都是直接返回node.parent
        return node.parent;
    }


    /**
     * 根据key找到对应的节点
     *
     * @param key
     * @return
     */
    private Node<K, V> node(K key) {
        Node<K, V> node = table[index(key)];
        int h1 = key == null ? 0 : key.hashCode();
        while (node != null) {
            int cmp = compare(key, node.key, h1, node.hash);
            if (cmp == 0) return node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node = node.right;
            }
        }
        return null;
    }

    /**
     * 根据key生成对应的索引(在table中的位置)
     *
     * @param key
     * @return
     */
    private int index(K key) {
        if (key == null) return 0;
        int hash = key.hashCode();
        hash = hash ^ (hash >>> 16);
        return hash & (table.length - 1);
    }

    private int index(Node<K, V> node) {
        return (node.hash ^ (node.hash >>> 16)) & (table.length - 1);
    }

    /**
     * @param k1
     * @param k2
     * @param h1 k1的hashCode
     * @param h2 k2的hashCode
     * @return
     */
    private int compare(K k1, K k2, int h1, int h2) {
        /**
         * 首先直接拿hashCode比较，但是hashCode相同不一定是同一个key,只有equals相等的才是相同的key
         */
        int result = h1 - h2;
        if (result != 0) return result;
        /**
         * 来到这里，说明hashCode相同,则比较equals
         */
        if (Objects.equals(k1, k2)) return 0;
        /**
         * 来到这里说明哈希值相等,但是并不equals
         */
        //比较类名
        if (k1 != null && k2 != null) {
            String name1 = k1.getClass().getName();
            String name2 = k2.getClass().getName();
            result = name1.compareTo(name2);
            if (result != 0) return result;
            /**
             *来到这里说明是同一种类型
             */
            if (k1 instanceof Comparable) {
                return ((Comparable) k1).compareTo(k2);
            }
        }
        /**
         *  同一种类型，但是不具备可比较性
         *  或者或k1和k2有一个为空
         *
         *  使用对象的内存地址来比较
         */
        /**
         * 如果下面直接 return System.identityHashCode(k1) - System.identityHashCode(k2);
         *
         * 那么会导致test4()
         *           这个地方会返回null,不稳定，因为compare最后一定走到比较内存地址，但是下面的new Key(1) 和上面循环中的new Key(i)是不同非
         *
         *          对象，所以内存地址是随机的,不稳定.
         */
        return System.identityHashCode(k1) - System.identityHashCode(k2);
    }

    /**
     * 红黑树节点
     *
     * @param <K>
     * @param <V>
     */
    private static class Node<K, V> {
        Boolean color = RED;
        int hash;
        K key;
        V value;
        Node<K, V> left;
        Node<K, V> right;
        Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.key = key;
            this.hash = key == null ? 0 : key.hashCode();
            this.value = value;
            this.parent = parent;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public boolean hasTwoChildren() {
            return left != null && right != null;
        }

        public boolean isLeftChild() {
            return parent != null && this == parent.left;
        }

        public boolean isRightChild() {
            return parent != null && this == parent.right;
        }

        /**
         * 节点的兄弟节点
         *
         * @return
         */
        public Node<K, V> sibling() {
            if (isLeftChild()) {
                return parent.right;
            }
            if (isRightChild()) {
                return parent.left;
            }
            //如果是root节点,则没有兄弟节点
            return null;
        }

        @Override
        public String toString() {
            return "Node_" + key + "_" + value;
        }
    }

    /**
     * 红黑树相关操作
     */
    private void afterPut(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        //如果是root节点，则染黑
        if (parent == null) {
            black(node);
            return;
        }
        //如果父节点是黑色,则直接return(4种)
        if (isBlack(parent)) {
            return;
        }
        //uncle节点
        Node<K, V> uncle = parent.sibling();
        //grand
        Node<K, V> grand = parent.parent;
        //如果uncle是红色(4种)
        if (isRed(uncle)) {
            //父节点和叔父节点染黑
            black(parent);
            black(uncle);
            //把祖父节点当做新添加的节点
            afterPut(red(grand));
            return;
        }
        //叔父节点不是红色(4种)
        if (parent.isLeftChild()) {//L
            //grand染成红色
            red(grand);
            if (node.isLeftChild()) {//LL
                //parent染成黑色
                black(parent);
            } else {//LR
                //自己染成黑色
                black(node);
                rotateLeft(parent);
            }
            //grand右旋转
            rotateRight(grand);
        } else {//R
            //grand染成红色
            red(grand);
            if (node.isLeftChild()) {//RL
                //自己染成黑色
                black(node);
                rotateRight(parent);
            } else {//RR
                //parent染成黑色
                black(parent);
            }
            //grand左旋转
            rotateLeft(grand);
        }
    }

    /**
     * 对节点进行染色，并返回该节点
     *
     * @param node
     * @param color
     * @return
     */
    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return node;
        node.color = color;
        return node;
    }

    /**
     * 将节点染成红色
     *
     * @param node
     * @return
     */
    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    /**
     * 将节点染成黑色
     *
     * @param node
     * @return
     */
    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    /**
     * 返回节点的颜色
     *
     * @param node
     * @return
     */
    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    /**
     * 节点是否是黑色
     *
     * @param node
     * @return
     */
    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    /**
     * 节点是否是红色
     *
     * @param node
     * @return
     */
    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }

    /**
     * g──┐
     * │
     * p──┐
     * │
     * n
     */
    private void rotateLeft(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        Node<K, V> child = parent.left;
        grand.right = child;
        parent.left = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * ┌──g
     * │
     * ┌─p
     * │
     * n
     *
     * @param grand
     */
    private void rotateRight(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        Node<K, V> child = parent.right;
        grand.left = child;
        parent.right = grand;
        afterRotate(grand, parent, child);
    }

    /**
     * 旋转之后需要修改parent,更新高度
     *
     * @param grand
     * @param parent
     * @param child
     */
    private void afterRotate(Node<K, V> grand, Node<K, V> parent, Node<K, V> child) {
        // 让parent称为子树的根节点
        parent.parent = grand.parent;
        if (grand.isLeftChild()) {
            grand.parent.left = parent;
        } else if (grand.isRightChild()) {
            grand.parent.right = parent;
        } else { // grand是root节点
            //root = parent;
            //用当前红黑树的root节点
            table[index(grand)] = parent;
        }

        // 更新child的parent
        if (child != null) {
            child.parent = grand;
        }

        // 更新grand的parent
        grand.parent = parent;
    }

}
