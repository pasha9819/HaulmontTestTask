package ru.pasha.services;

import org.springframework.stereotype.Service;
import ru.pasha.entity.Recipe;
import ru.pasha.repos.RecipeRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    private final RecipeRepo recipeRepo;

    public RecipeService(RecipeRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Recipe findById(long id) {
        Optional<Recipe> r = recipeRepo.findById(id);
        return r.orElse(null);
    }

    public List<Recipe> findAll() {
        return recipeRepo.findAll();
    }

    public void save(Recipe r) {
        recipeRepo.save(r);
    }

    public void delete(Recipe r) {
        recipeRepo.delete(r);
    }

    public List<Recipe> filtered(String patientName, String description, String priority) {
        if (patientName.isEmpty() && description.isEmpty() && priority.isEmpty()) {
            return findAll();
        }
        return findAll()
                .stream()
                .filter(recipe ->
                        recipe.getPatient().fullName().toLowerCase().startsWith(patientName.toLowerCase())
                                && recipe.getDescription().toLowerCase().contains(description)
                                && recipe.getPriority().name().toLowerCase().contains(priority.toLowerCase()))
                .collect(Collectors.toList());
    }
}
