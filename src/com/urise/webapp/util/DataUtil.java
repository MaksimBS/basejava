package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DataUtil {

    public static final LocalDate NOW = LocalDate.of(3000, 1, 1);

    public static LocalDate of() {
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
    }

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, Month.of(month), 1);
    }

    public static String toDateResume(LocalDate date) {
        //  01/2005
        String prefix = date.getMonth().getValue() < 10 ? "0" : "";
        return " " + prefix + date.getMonth().getValue() + "/" + date.getYear() + " ";
    }
}
