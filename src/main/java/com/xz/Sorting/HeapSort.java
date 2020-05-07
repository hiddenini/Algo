package com.xz.Sorting;

import com.xz.Sorting.tools.Asserts;
import com.xz.Sorting.tools.Integers;

/**
 * 堆排序
 */
public class HeapSort extends Sort {
    private int heapSize;
    @Override
    protected void sort() {
        // 原地建堆
        heapSize = array.length;
        /**
         * 自下而上的下滤
         */
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }

        while (heapSize > 1) {
            // 交换堆顶元素和尾部元素
            swap(0, --heapSize);

            // 对0位置进行siftDown（恢复堆的性质）
            siftDown(0);
        }
    }

    private void siftDown(int index) {
        int element = array[index];

        int half = heapSize >> 1;
        while (index < half) { // index必须是非叶子节点
            // 默认是左边跟父节点比
            int childIndex = (index << 1) + 1;
            int child = array[childIndex];

            int rightIndex = childIndex + 1;
            // 右子节点比左子节点大
            if (rightIndex < heapSize &&
                    cmpElements(array[rightIndex], child) > 0) {
                child = array[childIndex = rightIndex];
            }

            // 大于等于子节点
            if (cmpElements(element, child) >= 0) break;

            array[index] = child;
            index = childIndex;
        }
        array[index] = element;
    }
}