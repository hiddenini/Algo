package com.xz.Bst;

import com.xz.Bst.printer.BinaryTrees;

public class Test {
    public static void main(String[] args) {
        test1();
    }

   static void  test1(){
        Integer data[] = new Integer[] {
                7, 4, 9, 2, 5, 8, 11, 3, 15, 1,14
        };

        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        for (int i = 0; i < data.length; i++) {
            bst.add(data[i]);
        }

        BinaryTrees.println(bst);
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
    }
}

