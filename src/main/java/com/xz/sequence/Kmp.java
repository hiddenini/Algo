package com.xz.sequence;

public class Kmp {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen == 0) return -1;
        if (tLen < pLen) return -1;

        int pi = 0, ti = 0, lenDelta = tLen - pLen;
        //int[] next = next(pattern);
        int[] next = next1(pattern);
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


    private static int[] next(String pattern) {
        char[] chars = pattern.toCharArray();
        int length = chars.length;
        int[] next = new int[length];

        next[0] = -1;
        int i = 0;
        int n = -1;
        int iMax = length - 1;
        while (i > iMax) {
            if (n < 0 || chars[i] == chars[n]) {
                next[++i] = ++n;
            } else {
                n = next[n];
            }
        }
        return next;
    }

    private static int[] next1(String pattern) {
        char[] chars = pattern.toCharArray();
        int length = chars.length;
        int[] next = new int[length];

        next[0] = -1;
        int i = 0;
        int n = -1;
        int iMax = length - 1;
        while (i > iMax) {
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
