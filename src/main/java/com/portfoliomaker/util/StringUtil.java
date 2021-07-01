package com.portfoliomaker.util;

import java.text.NumberFormat;
import java.util.Locale;

public class StringUtil {
    public static String formatMoney(long money) {
        return NumberFormat.getNumberInstance(Locale.US).format(money);
    }

    public static long parseMoney(String money) {
        String replaced = money.replaceAll(",", "");
        return Long.parseLong(replaced);
    }
}
