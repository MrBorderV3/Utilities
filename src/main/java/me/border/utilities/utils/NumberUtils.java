package me.border.utilities.utils;

import java.text.DecimalFormat;

public class NumberUtils {

    /**
     * Function to abbreviate a number
     * Example: 1,000,000 gets abbreviated to 1M
     *
     * @param numValue The number to abbreviate
     * @return The abbreviated number as a {@link String}
     */
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
