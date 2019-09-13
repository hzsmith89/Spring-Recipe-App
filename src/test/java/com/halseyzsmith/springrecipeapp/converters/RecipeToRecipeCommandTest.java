package com.halseyzsmith.springrecipeapp.converters;

import com.halseyzsmith.springrecipeapp.commands.RecipeCommand;
import com.halseyzsmith.springrecipeapp.domain.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RecipeToRecipeCommandTest {

    private static final Long ID_VALUE = 1L;
    private static final String DESCRIPTION = "description";
    private static final Integer PREP_TIME = 13;
    private static final Integer COOK_TIME = 9;
    private static final Integer SERVINGS = 4;
    private static final String SOURCE = "source";
    private static final String URL = "url";
    private static final String DIRECTIONS = "directions";
    private static final Difficulty DIFFICULTY = Difficulty.EASY;

    private static final Long CATEGORY_ID_1 = 2L;
    private static final Long CATEGORY_ID_2 = 3L;

    private static final Long INGREDIENT_ID_1 = 4L;
    private static final Long INGREDIENT_ID_2 = 5L;

    private static final Long NOTES_ID_1 = 6L;

    RecipeToRecipeCommand converter;

    @Before
    public void setUp() {
        converter = new RecipeToRecipeCommand(new CategoryToCategoryCommand(),
                new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand()),
                new NotesToNotesCommand());
    }

    @Test
    public void testNullParameter() {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new Recipe()));
    }

    @Test
    public void testConvert() {
        Recipe recipe = new Recipe();
        recipe.setId(ID_VALUE);
        recipe.setDescription(DESCRIPTION);
        recipe.setPrepTime(PREP_TIME);
        recipe.setCookTime(COOK_TIME);
        recipe.setServings(SERVINGS);
        recipe.setSource(SOURCE);
        recipe.setUrl(URL);
        recipe.setDirections(DIRECTIONS);
        recipe.setDifficulty(DIFFICULTY);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(INGREDIENT_ID_1);
        recipe.getIngredients().add(ingredient1);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID_2);
        recipe.getIngredients().add(ingredient2);

        Category category1 = new Category();
        category1.setId(CATEGORY_ID_1);
        recipe.getCategories().add(category1);

        Category category2 = new Category();
        category2.setId(CATEGORY_ID_2);
        recipe.getCategories().add(category2);

        Notes notes = new Notes();
        notes.setId(NOTES_ID_1);
        recipe.setNotes(notes);

        RecipeCommand recipeCommand = converter.convert(recipe);

        assertNotNull(recipeCommand);
        assertEquals(ID_VALUE, recipeCommand.getId());
        assertEquals(DESCRIPTION, recipeCommand.getDescription());
        assertEquals(PREP_TIME, recipeCommand.getPrepTime());
        assertEquals(COOK_TIME, recipeCommand.getCookTime());
        assertEquals(SERVINGS, recipeCommand.getServings());
        assertEquals(SOURCE, recipeCommand.getSource());
        assertEquals(URL, recipeCommand.getUrl());
        assertEquals(DIRECTIONS, recipeCommand.getDirections());
        assertEquals(DIFFICULTY, recipeCommand.getDifficulty());
        assertEquals(2, recipeCommand.getIngredientCommands().size());
        assertEquals(2, recipeCommand.getCategoryCommands().size());
        assertEquals(NOTES_ID_1, recipeCommand.getNotesCommand().getId());
    }
}