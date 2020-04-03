package com.xz.utils;

/**
 * @author xz
 * @date 2020/2/16 16:49
 **/

public class Asserts {
    public static void test(boolean value) {
        try {
            if (!value) throw new Exception("测试未通过");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

