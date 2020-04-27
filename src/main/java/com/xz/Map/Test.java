package com.xz.Map;

import com.xz.Map.model.Key;
import com.xz.Map.model.Person;
import com.xz.Map.model.SubKey1;
import com.xz.Map.model.SubKey2;
import com.xz.Set.file.FileInfo;
import com.xz.Set.file.Files;

public class Test {

/*    static void test1() {
        Map<String, Integer> map = new TreeMap<>();
        map.put("c", 2);
        map.put("a", 5);
        map.put("b", 6);
        map.put("a", 8);
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    public static void test2() {
        FileInfo fileInfo = Files.read("D:\\software\\sources\\java\\util\\concurrent\\locks", new String[]{"java"});
        int files = fileInfo.getFiles();
        int lines = fileInfo.getLines();
        String[] words = fileInfo.words();

        Map<String, Integer> map = new TreeMap<>();
        for (String word : words) {
            Integer count = map.get(word);
            count = count == null ? 1 : count + 1;
            map.put(word, count);
        }
        System.out.println(map.size());
        map.traversal(new Map.Visitor<String, Integer>() {
            @Override
            public boolean visit(String key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
    }

    public static void test3() {
        Person p1 = new Person(10, 1.67f, "jack");
        Person p2 = new Person(10, 1.67f, "jack");
        Map<Object, Integer> map = new HashMap<>();
        map.put(p1, 1);
        map.put(p2, 2);
        map.put("jack", 2);
        map.put("rose", 4);
        map.put("jack", 5);
        map.put(null, 6);
        System.out.println("size:" + map.size());

        map.traversal(new Map.Visitor<Object, Integer>() {

            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });
*//*        System.out.println(map.remove("jack"));
        System.out.println(map.get("jack"));

        System.out.println("size:"+map.size());*//*

    }

    public static void test4(HashMap<Object, Integer> map) {
        for (int i = 1; i <= 19; i++) {
            map.put(new Key(i), i);
        }
        map.put(new Key(4), 100);
        Asserts.test(map.size() == 19);
        *//**
         * 这个地方会返回null不稳定，因为compare最后一定走到比较内存地址，但是下面的new Key(1) 和上面循环中的new Key(i)是不同非
         *
         * 对象，所以内存地址是随机的,不稳定.
         *//*
        Asserts.test(map.get(new Key(4)) == 100);
        //map.print();
    }

    public static void test5() {
        Person p1 = new Person(10, 1.7f, "jack");
        Person p2 = new Person(10, 1.8f, "rose");
        System.out.println(p1.equals(p2));
        //compareTo只是大小相等，不代表2个对象相等,之前的avl或者红黑树就是比较大小
        System.out.println(p1.compareTo(p2));
    }

    public static void test6() {
        HashMap<Object ,Integer> hashMap =new HashMap<>();
        SubKey1 subKey1=new SubKey1(1);
        SubKey2 subKey2=new SubKey2(1);
        hashMap.put(subKey1,1);
        hashMap.put(subKey2,2);
        System.out.println(hashMap.size());
        System.out.println(hashMap.get(subKey1));
    }*/

    static void test1() {
        String filepath = "D:\\software\\sources\\java\\util";
        FileInfo fileInfo = Files.read(filepath, null);
        String[] words = fileInfo.words();

        System.out.println("总行数：" + fileInfo.getLines());
        System.out.println("单词总数：" + words.length);
        System.out.println("-------------------------------------");
        Map<String, Integer> map=new HashMap<>();
        //java.util.HashMap<String, Integer> map=new java.util.HashMap<String, Integer>();
        Times.test(map.getClass().getName(), new Times.Task() {
            @Override
            public void execute() {
                for (String word : words) {
                    Integer count = map.get(word);
                    count = count == null ? 0 : count;
                    map.put(word, count + 1);
                }
                System.out.println(map.size()); // 17188

                int count = 0;
                for (String word : words) {
                    Integer i = map.get(word);
                    count += i == null ? 0 : i;
                    map.remove(word);
                }
                Asserts.test(count == words.length);
                Asserts.test(map.size() == 0);
            }
        });
    }

    static void test2(HashMap<Object, Integer> map) {
        for (int i = 1; i <= 20; i++) {
            map.put(new Key(i), i);
        }
        for (int i = 5; i <= 7; i++) {
            map.put(new Key(i), i + 5);
        }
        Asserts.test(map.size() == 20);
        Asserts.test(map.get(new Key(4)) == 4);
        Asserts.test(map.get(new Key(5)) == 10);
        Asserts.test(map.get(new Key(6)) == 11);
        Asserts.test(map.get(new Key(7)) == 12);
        Asserts.test(map.get(new Key(8)) == 8);
    }

    static void test3(HashMap<Object, Integer> map) {
        map.put(null, 1); // 1
        map.put(new Object(), 2); // 2
        map.put("jack", 3); // 3
        map.put(10, 4); // 4
        map.put(new Object(), 5); // 5
        map.put("jack", 6);
        map.put(10, 7);
        map.put(null, 8);
        map.put(10, null);
        Asserts.test(map.size() == 5);
        Asserts.test(map.get(null) == 8);
        Asserts.test(map.get("jack") == 6);
        Asserts.test(map.get(10) == null);
        Asserts.test(map.get(new Object()) == null);
        Asserts.test(map.containsKey(10));
        Asserts.test(map.containsKey(null));
        Asserts.test(map.containsValue(null));
        Asserts.test(map.containsValue(1) == false);
    }

    static void test4(HashMap<Object, Integer> map) {
        map.put("jack", 1);
        map.put("rose", 2);
        map.put("jim", 3);
        map.put("jake", 4);
        for (int i = 1; i <= 10; i++) {
            map.put("test" + i, i);
            map.put(new Key(i), i);
        }
        for (int i = 1; i <= 3; i++) {
            map.put(new Key(i), i + 5);
        }
        map.print();
        System.out.println(map.get("jack"));
/*        map.traversal(new Map.Visitor<Object, Integer>() {
            public boolean visit(Object key, Integer value) {
                System.out.println(key + "_" + value);
                return false;
            }
        });*/
    }

    static void test5(HashMap<Object, Integer> map) {
        for (int i = 1; i <= 20; i++) {
            map.put(new SubKey1(i), i);
        }
        map.put(new SubKey2(1), 5);
        Asserts.test(map.get(new SubKey1(1)) == 5);
        Asserts.test(map.get(new SubKey2(1)) == 5);
        Asserts.test(map.size() == 20);
    }

    public static void main(String[] args) {
        //test4(new HashMap<>());
        //test5();
/*
        test2(new HashMap<>());
        test2(new HashMap<>());
        test3(new HashMap<>());
        test4(new HashMap<>());
        test5(new HashMap<>());
*/
        test1();
    }

}
