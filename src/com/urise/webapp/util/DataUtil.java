package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DataUtil {

    public static LocalDate of() {
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
    }

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, Month.of(month), 1);
    }
}