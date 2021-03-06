package com.xz.BinaryTree;

import com.xz.BinaryTree.printer.BinaryTrees;

public class Test {
    public static void main(String[] args) {

        test1();

    }

   static void  test1(){
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 1
        };

       Integer data1[] = new Integer[] {
               7, 4, 9, 2, 1
       };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

       BST<Integer> bst1 = new BST<>();
       for (int i = 0; i < data1.length; i++) {
           bst1.add(data1[i]);
       }

        //BinaryTrees.println(bst);
       BinaryTrees.println(bst1);
        //bst.preOrderTraversal();
        //bst.inOrderTraversal();
       //bst.postOrderTraversal();
       //bst.levelOrderTraversal();
       bst.levelOrderTraversal(new BinaryTree.Visitor() {
           @Override
           public boolean visit(Object element) {
               System.out.print("_"+element+"_");
               return false;
           }
       });

       System.out.println();

       System.out.println("the height of the bst is : "+bst.height());

       //之前的旧方法会认为这是一棵完全二叉树
       System.out.println("the bst is complete : "+bst1.isCompleteOld());

       System.out.println("the bst is complete : "+bst1.isComplete());

    }

    static void  test2() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12,1
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
/*        bst.remove(1);
        bst.remove(3);
        bst.remove(12);
        BinaryTrees.println(bst);*/

        //bst.remove(5);
        //bst.remove(11);
        //bst.remove(9);
        bst.remove(7);
        BinaryTrees.println(bst);
    }

    /**
     * 测试调整结构之后的bst
     */
    static void  test3() {
        Integer data[] = new Integer[]{
                7, 4, 9, 2, 5, 8, 11, 3, 12,1
        };

        BST<Integer> bst = new BST<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
        bst.remove(1);
        //bst.remove(4);
        BinaryTrees.println(bst);
    }

    /**
     * 测试调AVL树add
     */
    static void  test4() {
        Integer data[] = new Integer[]{
                1,2,3,4,5,6,7,8,9
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);
        }

        BinaryTrees.println(avl);
    }

    /**
     * 测试调AVL树remove
     */
    static void  test5() {
        Integer data[] = new Integer[]{
                85,19,69,3,7,99,95
        };

        AVLTree<Integer> avl = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            avl.add(data[i]);
            System.out.println("["+data[i]+"]");
            System.out.println("==========================");
            BinaryTrees.println(avl);
        }
        BinaryTrees.println(avl);
        avl.remove(99);
        avl.remove(85);
        avl.remove(95);
        BinaryTrees.println(avl);
    }

    /**
     * 测试RBTree add
     */
    static void  test6() {
        Integer data[] = new Integer[]{
                55,87,56,74,96,22,62,20,70,68,90,50
        };

        RBTree<Integer> rbTree = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rbTree.add(data[i]);
            System.out.println("["+data[i]+"]");
            System.out.println("==========================");
            BinaryTrees.println(rbTree);
        }
        BinaryTrees.println(rbTree);
    }

    /**
     * 测试RBTree remove
     *
     */
    static void  test7() {
        Integer data[] = new Integer[]{
                55,87,56,74,96,22,62,20,70,68,90,50
        };

        RBTree<Integer> rbTree = new RBTree<>();
        for (int i = 0; i < data.length; i++) {
            rbTree.add(data[i]);
        }

        BinaryTrees.println(rbTree);

        for (int i = 0; i < data.length; i++) {
            rbTree.remove(data[i]);
            System.out.println("["+data[i]+"]");
            System.out.println("==========================");
            BinaryTrees.println(rbTree);
        }

        BinaryTrees.println(rbTree);
    }
}

