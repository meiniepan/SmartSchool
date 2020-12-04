package com.xiaoneng.ss.account.viewmodel;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author Burning
 * @description:
 * @date :2020/12/3 7:33 PM
 */
class MyTe {
    public static void main(String[] args) {
        int[][] aa = {{1,2},{2,3},{3,4},{3,5},{4,5},{5,5},{5,6},{6,7},{7,8}};
        System.out.println(maxEnvelopes2(aa));
    }

    public static int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] e1, int[] e2) {
                if (e1[0] != e2[0]) return e1[0] - e2[0];
                return e2[1] - e1[1];
            }
        });
        int len = 0;
        int[] h = new int[envelopes.length];
        for (int[] envelope : envelopes) {
            int i = 0, j = len - 1;
            while (i <= j) {
                int m = (i + j) / 2;
                if (h[m] < envelope[1]) i = m + 1;
                else j = m - 1;
            }
            h[i] = envelope[1];
            if (i == len) len++;
        }
        return len;
    }

    public static int maxEnvelopes2(int[][] envelopes) {
        Arrays.sort(envelopes, new Comparator<int[]>() {
            @Override
            public int compare(int[] e1, int[] e2) {
                if (e1[0] != e2[0]) return e1[0] - e2[0];
                return e1[1] - e2[1];
            }
        });
        int len = 0;
        if (envelopes.length > 0) {
            len = 1;
            int aa = envelopes[0][1];
            for (int[] envelope : envelopes) {
                if (envelope[1] > aa) {
                    aa = envelope[1];
                    len++;
                }
            }
        }
        return len;
    }
}
