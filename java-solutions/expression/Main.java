package com.example.calculator;

public class Main {
    public static void main(String[] args) {
        int[] arr = new int[]{1, 2, 3, 4, 5};
        int[] brr = new int[]{6, 7, 8, 9, 10};
        System.arraycopy(arr, 0, brr, 2, 2);
        for (int j : brr) System.out.println(j);
    }
}
// 1073741849