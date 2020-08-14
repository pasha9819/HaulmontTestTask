package ru.pasha.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.pasha.entity.Recipe;

import java.util.List;

@Component
public interface RecipeRepo extends JpaRepository<Recipe, Long> {
    @Override
    List<Recipe> findAll();
}
