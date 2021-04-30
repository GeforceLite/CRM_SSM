package com.bjpowernode.settings.test;

public class test {
    public static void main(String[] args) {
        //冒泡排序算法
        int array[] = {2, 3, 22, 31, 245};
        int value = 20;
        test test1 = new test();
        System.out.println(test1.Go(array, value));
    }
    public int Go(int array[],int value){
        int array1[] = array;
        int value1 = value;
        int low = 0;
        int high = array1.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (array1[mid]==value1){
                return mid;
            }
            if(value1<array1[mid]){
                high =mid-1;
            }
            if (value1 > array1[mid]) {
                low =mid+1;
            }
        }
        return -1;
    }
}
