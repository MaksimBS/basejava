package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class OrganizationSection extends AbstractSection {

    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public void addOrganization(Organization organization) {
        if (OrganizationExist(organization) == -1) {
            organizations.add(organization);
        } else {
            organizations.get(OrganizationExist(organization)).setPosition(organization.getPosition().get(0));
        }
    }

    private int OrganizationExist(Organization organization) {
        for (int i = 0; i < organizations.size(); i++) {
            if (organization.getPosition().equals(organizations.get(i).getPosition())) {
                return i;
            }
        }
        return -1;
    }

    public List<Organization> getInfo() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationSection that = (OrganizationSection) o;

        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        return organizations.toString();
    }
}
