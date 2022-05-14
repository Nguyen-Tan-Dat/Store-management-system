package com.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class ToMoneyString {
    public static String format(double value){
        Locale localeVN = new Locale("vi", "VN");
        NumberFormat currencyVN = NumberFormat.getCurrencyInstance(localeVN);
        return currencyVN.format(value);
    }
}
