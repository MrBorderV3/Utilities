package me.border.utilities.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    public static String abbreviateNumber(double numValue) {
        char[] suffix = {' ', 'k', 'M', 'B', 'T', 'q', 'Q', 's', 'S'};
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffix.length) {
            return new DecimalFormat("~#0.0").format(numValue / Math.pow(10, base * 3)) + suffix[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }
}
