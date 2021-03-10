package com.xz.sequence;

/**
 * 串的匹配算法，蛮力法
 * <p>
 * <p>
 * 最好情况
 * 只需一轮比较就完全匹配成功，比较 m 次（ m 是模式串的长度）
 * 时间复杂度为 O(m)
 * <p>
  * 最坏情况（字符集越大，出现概率越低）
 * 执行了 n – m + 1 轮比较（ n 是文本串的长度）
 * 每轮都比较至模式串的末字符后失败（ m – 1 次成功，1 次失败）
 * 时间复杂度为 O(m ∗ (n − m + 1))，由于一般 m 远小于 n，所以为 O(mn)
 */
public class BruteForce {
    public static int indexOf(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen == 0) return -1;
        if (tLen < pLen) return -1;

        int pi = 0;
        int ti = 0;
        while (pi < pLen && ti < tLen) {
            if (textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                /**
                 *如果某个位置不匹配了，pi此时是前面已经匹配的个数,那么ti-pi就是这一轮比较时text的第一个元素的位置,那么需要
                 * 移动到下一位 即ti=ti-pi+1;  即 ti -= pi - 1;
                 */
                ti -= pi - 1;
                pi = 0;
            }
        }
        return (pi == pLen) ? (ti - pi) : -1;
    }

    public static int indexOf1(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen == 0) return -1;
        if (tLen < pLen) return -1;

        int pi = 0;
        int ti = 0;
        /**
         * 优化 ti - pi 文本串当前匹配的子串的起始位置
         *
         * tLen - pLen 临界位置 再往后无需再进行匹配,因为text的长度已经不够了
         *
         * 所以 ti - pi<=tLen - pLen
         */
        while (pi < pLen && ti - pi <= tLen - pLen) {
            if (textChars[ti] == patternChars[pi]) {
                ti++;
                pi++;
            } else {
                /**
                 *如果某个位置不匹配了，pi此时是前面已经匹配的个数,那么ti-pi就是这一轮比较时text的第一个元素的位置,那么需要
                 * 移动到下一位 即ti=ti-pi+1;  即 ti -= pi - 1;
                 */
                ti -= pi - 1;
                pi = 0;
            }
        }
        return (pi == pLen) ? (ti - pi) : -1;
    }

    public static int indexOf2(String text, String pattern) {
        if (text == null || pattern == null) return -1;
        char[] textChars = text.toCharArray();
        int tLen = textChars.length;
        if (tLen == 0) return -1;
        char[] patternChars = pattern.toCharArray();
        int pLen = patternChars.length;
        if (pLen == 0) return -1;
        if (tLen < pLen) return -1;
        /**
         * ti是文本串每轮比较的初始位置
         *
         * ti+pi 每轮比较中的角标变化
         */
        int tiMax = tLen - pLen;
        for (int ti = 0; ti < tiMax; ti++) {
            int pi = 0;
            for (; pi < pLen; pi++) {
                if (textChars[ti + pi] != patternChars[pi]) break;
            }
            if (pi == pLen) return ti;
        }
        return -1;
    }

}
