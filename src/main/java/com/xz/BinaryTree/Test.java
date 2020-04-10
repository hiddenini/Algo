package com.xz.BinaryTree;

import com.xz.BinaryTree.printer.BinaryTrees;

public class Test {
    public static void main(String[] args) {

        test4();

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
       bst.levelOrderTraversal(new BST.Vistor() {
           @Override
           public void visit(Object element) {
               System.out.print("_"+element+"_");
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
        bst.remove(4);
        BinaryTrees.println(bst);
    }

    /**
     * 测试调整结构之后的bst
     */
    static void  test4() {
        Integer data[] = new Integer[]{
                1,2,3,4,5,6,7
        };

        AVLTree<Integer> bst = new AVLTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
    }
}

