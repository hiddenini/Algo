package com.xz.Sorting.cmpSort;

import com.xz.Sorting.Sort;

public class QuickSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        sort(0, array.length);
    }

    private void sort(int begin, int end) {
        if (end - begin < 2) return;

        // 确定轴点位置 O(n)
        int mid = pivotIndex(begin, end);
        // 对子序列进行快速排序
        sort(begin, mid);
        sort(mid + 1, end);
    }

    private int pivotIndex(int begin, int end) {
        /**
         *  随机选择一个元素跟begin位置进行交换这个地方是为了避免出现最坏的时间紧复杂度的情况
         *
         *  eg 7 6 5 4 3 2 1 如果是选择7作为基准，那么分割出来的子序列是极度不均匀的,会导致O(n^2)
         */
        swap(begin, begin + (int) (Math.random() * (end - begin)));

        // 备份begin位置的元素
        E pivot = array[begin];
        // end指向最后一个元素
        end--;

        /**
         * 三个while是必不可少的,一个是左右2个指针要一直走
         *
         * 下面两个是保证时而往左,时而往右
         */
        while (begin < end) {
            while (begin < end) {
                /**
                 * 从右边开始往左走,如果元素比基准要大,则直接end--
                 *
                 *  这个地方不能使用< =0,因为如果所有的元素都一样的时候如果加上等于也会导致分割出来的子序列是极度不均匀的,会导致O(n^2)
                 *
                 *  数据规模大的时候会导致java.lang.StackOverflowError 以为每次递归数据规模只减少1
                 *
                 *   eg 1(a),1(b),1(c),1(d),1(e)
                 *
                 *   如果加上等号之后第一次分割的结果是 left null  right 1(a),1(b),1(c),1(d),1(e)
                 *    162ms Integers.same(10000, 0)
                 *
                 *    不加=的时候 15ms
                 *
                 * 否则将array[end]赋值给array[begin]  begin++ break掉头从左边开始找
                 *
                 */
                if (cmp(pivot, array[end]) < 0) { // 右边元素 > 轴点元素
                    end--;
                } else { // 右边元素 <= 轴点元素
                    array[begin++] = array[end];
                    break;
                }
            }
            while (begin < end) {
                /**
                 * 从左边开始往右走,如果元素比基准要小,则直接 begin++
                 *
                 * 这个地方不能使用> =0 因为如果所有的元素都一样的时候如果加上等于也会导致分割出来的子序列是极度不均匀的,会导致O(n^2)
                 *
                 * 否则将array[begin]赋值给array[end]  end-- break掉头从右边开始找
                 */
                if (cmp(pivot, array[begin]) > 0) { // 左边元素 < 轴点元素
                    begin++;
                } else { // 左边元素 >= 轴点元素
                    array[end--] = array[begin];
                    break;
                }
            }
        }

        // 将轴点元素放入最终的位置
        array[begin] = pivot;
        // 返回轴点元素的位置
        return begin;
    }


}
