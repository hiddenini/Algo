package com.xz.Set;

import com.xz.Set.file.FileInfo;
import com.xz.Set.file.Files;

public class Test {
    public static void main(String[] args) {
        test4();
    }

    public static void test1() {
        Set<Integer> listSet = new ListSet<>();
        listSet.add(10);
        listSet.add(11);
        listSet.add(11);
        listSet.add(12);
        listSet.add(10);
        listSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void test2() {
        Set<Integer> treeSet = new TreeSet<>();
        treeSet.add(10);
        treeSet.add(11);
        treeSet.add(11);
        treeSet.add(12);
        treeSet.add(10);
        System.out.println(treeSet);
        treeSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }

    public static void test3() {
        FileInfo fileInfo=Files.read("D:\\software\\sources\\java\\util\\concurrent",new String[]{"java"});
        int files = fileInfo.getFiles();
        int lines = fileInfo.getLines();
        String[] words = fileInfo.words();
        System.out.println("files size:"+files);
        System.out.println("lines size:"+lines);
        System.out.println("words size:"+words.length);

/*        Times.test(("ListSet"), () ->{
            testSet(new ListSet<>(), words);
        });*/

        Times.test(("treeSet"), () ->{
            testSet(new TreeSet<>(), words);
        });
    }

    static void testSet(Set<String> set, String[] words) {
        for (int i = 0; i < words.length; i++) {
            set.add(words[i]);
        }
        System.out.println("real size:"+set.size());
        for (int i = 0; i < words.length; i++) {
            set.contains(words[i]);
        }
        for (int i = 0; i < words.length; i++) {
            set.remove(words[i]);
        }
    }

    public static void test4() {
        Set<Integer> treeSet = new TreeSetByTreeMap<>();
        treeSet.add(10);
        treeSet.add(11);
        treeSet.add(11);
        treeSet.add(12);
        treeSet.add(10);
        System.out.println(treeSet);
        treeSet.traversal(new Set.Visitor<Integer>() {
            @Override
            public boolean visit(Integer element) {
                System.out.println(element);
                return false;
            }
        });
    }
}
