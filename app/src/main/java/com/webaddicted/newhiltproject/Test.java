package com.webaddicted.newhiltproject;

import android.util.Log;

import java.util.HashMap;

public class Test {
    public static void hexadecimalModulus() {
        String str1 = "3E8";
        String str2 = "13";
        long one = Long.parseLong(str1, 16);
        long two = Long.parseLong(str2, 16);
        long longSum = one % two;
        String result = Long.toHexString(longSum);
        Log.d("TAG", "Hexadecimal Sum : " + result);
    }

    public static void hexadecimalModul(String N, String k) {
        String str1 = "AD";
        String str2 = "13";
        long one = Long.parseLong(str1, 16);
        long two = Long.parseLong(str2, 16);
        long longSum = one % two;
        String result = Long.toHexString(longSum);
        Log.d("TAG", "Sum : " + result);


        // Store all possible
        // hexadecimal digits
        HashMap<Character, Integer> map
                = new HashMap<>();

        // Iterate over the range ['0', '9']
        for (char i = '0'; i <= '9'; i++) {
            map.put(i, i - '0');
        }
        map.put('A', 10);
        map.put('B', 11);
        map.put('C', 12);
        map.put('D', 13);
        map.put('E', 14);
        map.put('F', 15);

        // Convert given string to long
        long m = Long.parseLong(k, 16);
        Log.d("TAG", " asjbj : " + m);
        Log.d("TAG", " asjbj : " + Long.parseLong(N, 16));
        // Base to get 16 power
        long base = 1;

        // Store N % K
        long ans = 0;

        // Iterate over the digits of N
        for (int i = N.length() - 1; i >= 0; i--) {

            // Stores i-th digit of N
            long n = map.get(N.charAt(i)) % m;

            // Update ans
            ans = (ans + (base % m * n % m) % m) % m;

            // Update base
            base = (base % m * 16 % m) % m;
        }

        // Print the answer converting
        // into hexadecimal
        Log.d("TAG", Long.toHexString(ans).toUpperCase());
    }
}
