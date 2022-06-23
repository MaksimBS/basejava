package com.urise.webapp.util;

import com.urise.webapp.model.Organization;

public class HtmlUtil {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    public static String formatDates(Organization.Position position) {
        return DataUtil.format(position.getStartDate()) + " - " + DataUtil.format(position.getEndDate());
    }
}
