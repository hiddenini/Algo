package com.xz.Bst;

import com.xz.Bst.printer.BinaryTrees;

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

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

       BinarySearchTree<Integer> bst1 = new BinarySearchTree<>();
       for (int i = 0; i < data1.length; i++) {
           bst1.add(data1[i]);
       }

        //BinaryTrees.println(bst);
       BinaryTrees.println(bst1);
        //bst.preOrderTraversal();
        //bst.inOrderTraversal();
       //bst.postOrderTraversal();
       //bst.levelOrderTraversal();
       bst.levelOrderTraversal(new BinarySearchTree.Vistor() {
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

}
