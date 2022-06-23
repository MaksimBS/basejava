package com.urise.webapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataUtil {

    public static final LocalDate NOW = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");

    public static LocalDate of() {
        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
    }

    public static LocalDate of(int year, int month) {
        return LocalDate.of(year, Month.of(month), 1);
    }

    public static String toDateResume(LocalDate date) {
        if (date == null) return "";
        String prefix = date.getMonth().getValue() < 10 ? "0" : "";
        return " " + prefix + date.getMonth().getValue() + "/" + date.getYear() + " ";
    }

    public static LocalDate parse(String date) {
        if (HtmlUtil.isEmpty(date) || "Сейчас".equals(date)) return NOW;
        YearMonth yearMonth = YearMonth.parse(date, DATE_FORMATTER);
        return LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), 1);
    }

    public static String format(LocalDate date) {
        if (date == null) return "";
        return date.equals(NOW) ? "Сейчас" : date.format(DATE_FORMATTER);
    }

}
