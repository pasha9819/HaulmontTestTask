package ru.pasha.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public class Human {
    protected String name;
    protected String patronymic;
    protected String surname;

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "patronymic", nullable = false)
    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Column(name = "surname", nullable = false)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String fullName(){
        final StringBuilder sb = new StringBuilder();
        sb.append(surname);
        sb.append(" ").append(name);
        sb.append(" ").append(patronymic);
        return sb.toString();
    }
}
