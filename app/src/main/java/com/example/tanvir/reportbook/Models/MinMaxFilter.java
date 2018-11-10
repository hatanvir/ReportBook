package com.example.tanvir.reportbook.Models;

import android.text.InputFilter;
import android.text.Spanned;

public class MinMaxFilter implements InputFilter {

    private int min, max;
    public MinMaxFilter(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public MinMaxFilter(String min, String max) {
        this.min = Integer.parseInt(min);
        this.max = Integer.parseInt(max);
    }

    @Override
    public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
        int input = 0;
        try {
            // Remove the string out of destination that is to be replaced
            String newVal = spanned.toString().substring(0, i2) + spanned.toString().substring(i3, spanned.toString().length());
            // Add the new string in
            newVal = newVal.substring(0, i2) + charSequence.toString() + newVal.substring(i2, newVal.length());
            input = Integer.parseInt(newVal);
            if (isInRange(min, max, input))
                return null;
        } catch (NumberFormatException nfe) { }
        if(input>=24){
            return "24";
        }
       else
            return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
