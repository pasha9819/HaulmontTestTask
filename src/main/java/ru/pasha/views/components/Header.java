package ru.pasha.views.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Link;

public class Header extends HorizontalLayout {
    private final Link doctorLink, patientLink, recipeLink;

    public Header() {
        doctorLink = new Link("Врачи", new ExternalResource("/doctors"));
        patientLink = new Link("Пациенты", new ExternalResource("/patients"));
        recipeLink = new Link("Рецепты", new ExternalResource("/recipes"));
        addComponents(doctorLink, patientLink, recipeLink);
    }

    public Link getDoctorLink() {
        return doctorLink;
    }

    public Link getPatientLink() {
        return patientLink;
    }

    public Link getRecipeLink() {
        return recipeLink;
    }
}
