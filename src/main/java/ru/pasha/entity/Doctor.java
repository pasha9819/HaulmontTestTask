package ru.pasha.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "doctor")
public class Doctor extends Human {
    private Long id;
    private String specialization;

    private Set<Recipe> recipes;

    public Doctor() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "specialization", nullable = false)
    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    @OneToMany(mappedBy = "doctor", fetch = FetchType.LAZY)
    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    public long normalCount(){
        return recipes.stream().filter(recipe -> recipe.getPriority().equals(RecipePriority.NORMAL)).count();
    }

    public long citoCount(){
        return recipes.stream().filter(recipe -> recipe.getPriority().equals(RecipePriority.CITO)).count();
    }

    public long statimCount(){
        return recipes.stream().filter(recipe -> recipe.getPriority().equals(RecipePriority.STATIM)).count();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(surname);
        sb.append(" ").append(name);
        sb.append(" ").append(patronymic);
        sb.append(" (").append(id).append(")");
        return sb.toString();
    }
}
