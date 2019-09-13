package com.halseyzsmith.springrecipeapp.repositories;

import com.halseyzsmith.springrecipeapp.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
