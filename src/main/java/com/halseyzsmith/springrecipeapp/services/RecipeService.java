package com.halseyzsmith.springrecipeapp.services;

import com.halseyzsmith.springrecipeapp.commands.RecipeCommand;
import com.halseyzsmith.springrecipeapp.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findRecipeById(Long id);

    RecipeCommand findCommandById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    void deleteById(Long id);
}
