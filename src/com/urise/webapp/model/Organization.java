package com.urise.webapp.model;

import com.urise.webapp.util.DataUtil;
import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {

    private Link homePage;
    private List<Position> positions;

    public Organization() {
    }

    public Organization(String name, String url) {
        Objects.requireNonNull(name, "name must not be null");
        this.homePage = new Link(name, url);
        this.positions = new ArrayList<>();
    }

    public Organization(String name, String url, List<Position> positions) {
        Objects.requireNonNull(name, "name must not be null");
        this.homePage = new Link(name, url);
        this.positions = positions;
    }

    public Organization(String name, String url, LocalDate startDate, LocalDate endDate, String title, String description) {
        Objects.requireNonNull(startDate, "startDate must not be null");
        Objects.requireNonNull(endDate, "endDate must not be null");
        Objects.requireNonNull(title, "title must not be null");

        this.homePage = new Link(name, url);
        this.positions = new ArrayList<>();
        setPosition(startDate, endDate, title, description);
    }

    public Link getHomePage() {
        return homePage;
    }

    public List<Position> getPosition() {
        return positions;
    }

    public void setPosition(LocalDate startDate, LocalDate endDate, String title, String description) {
        positions.add(new Position(startDate, endDate, title, description));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return homePage.equals(that.homePage) && positions.equals(that.positions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, positions);
    }

    @Override
    public String toString() {
        return homePage.toString();
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Position() {
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) && Objects.equals(endDate, position.endDate) && Objects.equals(title, position.title) && Objects.equals(description, position.description);
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public String toString() {
            String strEndDate = endDate.isBefore(LocalDate.now()) ? DataUtil.toDateResume(endDate) : " /по н.в./ ";

            return DataUtil.toDateResume(startDate) + "-" + strEndDate + title + " " + description;
        }
    }
}