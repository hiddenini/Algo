package com.xz.BinaryTree;

import java.util.Comparator;

public class AVLTree<E> extends BBST<E> {
	public AVLTree() {
		this(null);
	}
	
	public AVLTree(Comparator<E> comparator) {
		super(comparator);
	}
	
	@Override
	protected void afterAdd(Node<E> node) {
		/**
		 * 从当前节点向上找祖先节点,只有这些节点可能不平衡(父节点和非祖先节点不可能失衡 ),实际上只需要找到高度最低的那个祖先节点即可
		 */
		while ((node = node.parent) != null) {
			if (isBalanced(node)) {
				// 更新高度
				updateHeight(node);
			} else {
				// 恢复平衡
				reBalance(node);
                //reBalance1(node);
				// 整棵树恢复平衡
				break;
			}
		}
	}

    /**
     * 删除之后的reBalance操作可能导致更上层的节点失衡，所以这里的没有break
     * @param node 新添加的节点
     */
    @Override
    protected void afterRemove(Node<E> node,Node<E> replace) {
        while ((node = node.parent) != null) {
            if (isBalanced(node)) {
                // 更新高度
                updateHeight(node);
            } else {
                // 恢复平衡
                //reBalance(node);
                reBalance(node);
            }
        }
    }

    @Override
	protected Node<E> createNode(E element, Node<E> parent) {
		return new AVLNode<>(element, parent);
	}

	/**
	 * 恢复平衡
	 * @param grand 高度最低的那个不平衡节点(g)
	 */
	@SuppressWarnings("unused")
	private void reBalance(Node<E> grand) {
		Node<E> parent = ((AVLNode<E>)grand).tallerChild();
		Node<E> node = ((AVLNode<E>)parent).tallerChild();
		if (parent.isLeftChild()) { // L
			if (node.isLeftChild()) { // LL
				rotateRight(grand);
			} else { // LR
				rotateLeft(parent);
				rotateRight(grand);
			}
		} else { // R
			if (node.isLeftChild()) { // RL
				rotateRight(parent);
				rotateLeft(grand);
			} else { // RR
				rotateLeft(grand);
			}
		}
	}

    /**
     * 恢复平衡
     * @param grand 高度最低的那个不平衡节点(g)
     */
    @SuppressWarnings("unused")
    private void reBalance1(Node<E> grand) {
        Node<E> parent = ((AVLNode<E>)grand).tallerChild();
        Node<E> node = ((AVLNode<E>)parent).tallerChild();
        if (parent.isLeftChild()) { // L
            if (node.isLeftChild()) { // LL
               rotate(grand,node.left,node,node.right,parent,parent.right,grand,grand.right);
            } else { // LR
                rotate(grand,parent.left,parent,node.left,node,node.right,grand,grand.right);
            }
        } else { // R
            if (node.isLeftChild()) { // RL
                rotate(grand,grand.left,grand,node.left,node,node.right,parent,parent.right);
            } else { // RR
                rotate(grand,grand.left,grand,parent.left,parent,node.left,node,node.right);
            }
        }
    }

	@Override
	protected void afterRotate(Node<E> grand, Node<E> parent, Node<E> child) {
		super.afterRotate(grand, parent, child);
		// 更新高度
		updateHeight(grand);
		updateHeight(parent);
	}

	@Override
	protected void rotate(Node<E> r, Node<E> a, Node<E> b, Node<E> c, Node<E> d, Node<E> e, Node<E> f, Node<E> g) {
		super.rotate(r, a, b, c, d, e, f, g);
		//更新b的高度
		updateHeight(b);
		//更新f的高度
		updateHeight(f);
		//更新d的高度
		updateHeight(d);
	}

	/**
     * 返回该节点是否平衡
     * @param node
     * @return
     */
	private boolean isBalanced(Node<E> node) {
		return Math.abs(((AVLNode<E>)node).balanceFactor()) <= 1;
	}

    /**
     * 更新该节点的
     * @param node
     */
	private void updateHeight(Node<E> node) {
		((AVLNode<E>)node).updateHeight();
	}
	
	private static class AVLNode<E> extends Node<E> {
		int height = 1;
		
		public AVLNode(E element, Node<E> parent) {
			super(element, parent);
		}

        /**
         * 返回该节点的平衡因子
         * @return
         */
		public int balanceFactor() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			return leftHeight - rightHeight;
		}

        /**
         * 更新该节点的高度为当前节点的左右子树中最高的那个+1
         */
		public void  updateHeight() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			height = 1 + Math.max(leftHeight, rightHeight);
		}

        /**
         * 根据g找到p 根据p找到n
         * @return 当前节点的左右子树中最高的那个节点
         */
		public Node<E> tallerChild() {
			int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
			int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
			if (leftHeight > rightHeight) return left;
			if (leftHeight < rightHeight) return right;
            /**
             * 如果左右子树高度一样，返回和当前节点同方向的节点
             */
			return isLeftChild() ? left : right;
		}
		
		@Override
		public String toString() {
			String parentString = "null";
			if (parent != null) {
				parentString = parent.element.toString();
			}
			return element + "_p(" + parentString + ")_h(" + height + ")";
		}
	}
}
