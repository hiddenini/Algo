package com.xz.sequence;

import com.xz.SkipList.tools.Asserts;

public class Test {
    public static void main(String[] args) {
        Asserts.test(BruteForce.indexOf("Hello World!", "or") == 7);
        Asserts.test(BruteForce.indexOf("Hello World!", "abc") == -1);
        Asserts.test(BruteForce.indexOf1("Hello World!", "abc") == -1);
        Asserts.test(BruteForce.indexOf2("Hello World!", "or") == 7);

        Asserts.test(Kmp.indexOf("Hello World!", "or") == 7);
        Asserts.test(Kmp.indexOf("Hello World!", "abc") == -1);
        Asserts.test(Kmp.indexOf("Hello World!", "abc") == -1);
        Asserts.test(Kmp.indexOf("Hello World!", "or") == 7);
    }

    ;
}
