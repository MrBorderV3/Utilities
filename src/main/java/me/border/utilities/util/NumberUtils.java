package me.border.utilities.util;

import java.text.DecimalFormat;

/**
 * Utility class for numbers.
 */
public class NumberUtils {

    /**
     * Function to abbreviate a number using default suffixes {@code new String[]{" ", "k", "M", "B", "T", "q", "Q", "s", "S"}}
     * Example: 1,000,000 gets abbreviated to 1M
     *
     * @param numValue The number to abbreviate
     * @return The abbreviated number as a {@link String}
     */
    public static String abbreviateNumber(double numValue){
        return abbreviateNumber(numValue, new String[]{" ", "k", "M", "B", "T", "q", "Q", "s", "S"});
    }

    /**
     * Function to abbreviate a number
     * Example: 1,000,000 gets abbreviated to 1M
     *
     * @param numValue The number to abbreviate
     * @param suffixes The available suffixes, ordered by chronological order.
     * @return The abbreviated number as a {@link String}
     */
    public static String abbreviateNumber(double numValue, String[] suffixes) {
        int value = (int) Math.floor(Math.log10(numValue));
        int base = value / 3;
        if (value >= 3 && base < suffixes.length) {
            return new DecimalFormat("~#0.0").format(numValue / Math.pow(10, base * 3)) + suffixes[base];
        } else {
            return new DecimalFormat("#,##0").format(numValue);
        }
    }
}
