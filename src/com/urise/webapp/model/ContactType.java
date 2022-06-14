package com.urise.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    MOBILE("Мобильный"),
    HOME_PHONE("Домашний тел."),
    SKYPE("Скайп"){
        public String toHtml0(String value) {
            return "<a href='skype:" + value + "'>" + value + "</a>";
        }
    },
    MAIL("E-Mail"){
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    },
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STATCKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница"){
        public String toHtml0(String value) {
            return "<a href='" + value + "'>" + value + "</a>";
        }
    };

    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(String value) {
        return  title + ": "+value;
    }

    public String toHtml(String value) {
        return (value ==null ) ? "" : toHtml0(value);
    }
}
