package com.halseyzsmith.springrecipeapp.converters;

import com.halseyzsmith.springrecipeapp.commands.RecipeCommand;
import com.halseyzsmith.springrecipeapp.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RecipeToRecipeCommand implements Converter<Recipe, RecipeCommand> {

    CategoryToCategoryCommand categoryToCategoryCommand;
    IngredientToIngredientCommand ingredientToIngredientCommand;
    NotesToNotesCommand notesToNotesCommand;

    public RecipeToRecipeCommand(CategoryToCategoryCommand categoryToCategoryCommand,
                                 IngredientToIngredientCommand ingredientToIngredientCommand,
                                 NotesToNotesCommand notesToNotesCommand) {
        this.categoryToCategoryCommand = categoryToCategoryCommand;
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.notesToNotesCommand = notesToNotesCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeCommand convert(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        final RecipeCommand recipeCommand = new RecipeCommand();

        recipeCommand.setId(recipe.getId());
        recipeCommand.setDescription(recipe.getDescription());
        recipeCommand.setCookTime(recipe.getCookTime());
        recipeCommand.setPrepTime(recipe.getPrepTime());
        recipeCommand.setDirections(recipe.getDirections());
        recipeCommand.setServings(recipe.getServings());
        recipeCommand.setSource(recipe.getSource());
        recipeCommand.setUrl(recipe.getUrl());
        recipeCommand.setDifficulty(recipe.getDifficulty());
        recipeCommand.setNotesCommand(notesToNotesCommand.convert(recipe.getNotes()));

        if (!CollectionUtils.isEmpty(recipe.getCategories())) {
            recipe.getCategories().forEach(category -> recipeCommand.getCategoryCommands().add(categoryToCategoryCommand.convert(category)));
        }

        if (!CollectionUtils.isEmpty(recipe.getIngredients())) {
            recipe.getIngredients().forEach(ingredient -> recipeCommand.getIngredientCommands().add(ingredientToIngredientCommand.convert(ingredient)));
        }

        return recipeCommand;
    }
}
