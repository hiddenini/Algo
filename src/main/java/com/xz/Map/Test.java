package com.xz.Map;

import com.xz.Set.file.FileInfo;
import com.xz.Set.file.Files;

public class Test {
    public static void main(String[] args) {
        test3();
    }

    static void test1() {
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
        Person p1=new Person(10,1.67f,"jack");
        Person p2=new Person(10,1.67f,"jack");
        Map<Object, Integer> map = new HashMap<>();
        map.put(p1,1);
        map.put(p2,2);
        map.put("jack",2);
        map.put("rose",4);
        map.put("jack",5);
        map.put(null,6);
        System.out.println("size:"+map.size());

        map.traversal(new Map.Visitor<Object, Integer>() {

            @Override
            public boolean visit(Object key, Integer value) {
                System.out.println(key+"_"+value);
                return false;
            }
        });

/*        System.out.println(map.remove("jack"));
        System.out.println(map.get("jack"));

        System.out.println("size:"+map.size());*/

    }
}
