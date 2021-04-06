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
        Asserts.test(Kmp.indexOf("mississippi", "issip") == 4);

        Asserts.test(strStr("mississippi", "issip") == 4);

    }

    public static int strStr(String haystack, String needle) {
        if (haystack == null || needle == null) return -1;
        char[] patternChars = needle.toCharArray();
        int pLen = patternChars.length;
        if (pLen == 0) return 0;

        char[] textChars = haystack.toCharArray();
        int tLen = textChars.length;
        if (tLen == 0) return -1;


        if (tLen < pLen) return -1;

        int pi = 0, ti = 0, lenDelta = tLen - pLen;
        //int[] next = next(pattern);
        int[] next = next1(needle);
        while (pi < pLen && ti - pi <= lenDelta) {
            if (pi < 0 || textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                //失配时，使用next数组
                pi = next[pi];
            }
        }
        return (pi == pLen) ? (ti - pi) : -1;
    }

    private static int[] next1(String pattern) {
        char[] chars = pattern.toCharArray();
        int length = chars.length;
        int[] next = new int[length];

        next[0] = -1;
        int i = 0;
        int n = -1;
        int iMax = length - 1;
        while (i < iMax) {
            if (n < 0 || chars[i] == chars[n]) {
                ++i;
                ++n;
                if (chars[i] == chars[n]) {
                    //如果i位置失配并且chars[i] == chars[n] 那么n位置也一定失配所以直接将
                    //next[n] k的值赋值给next[i]
                    next[i] = next[n];
                } else {
                    //即上面的next[++i] = ++n;
                    next[i] = n;
                }
            } else {
                n = next[n];
            }
        }
        return next;
    }

}
