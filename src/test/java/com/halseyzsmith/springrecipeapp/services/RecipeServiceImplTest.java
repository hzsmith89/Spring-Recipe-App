package com.halseyzsmith.springrecipeapp.services;

import com.halseyzsmith.springrecipeapp.converters.RecipeCommandToRecipe;
import com.halseyzsmith.springrecipeapp.converters.RecipeToRecipeCommand;
import com.halseyzsmith.springrecipeapp.domain.Recipe;
import com.halseyzsmith.springrecipeapp.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    private static final Long RECIPE_ID = 1L;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    RecipeCommandToRecipe recipeCommandToRecipe;

    @Mock
    RecipeToRecipeCommand recipeToRecipeCommand;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    public void testGetRecipes() {

        Recipe recipe = new Recipe();
        HashSet<Recipe> recipesData = new HashSet<>();
        recipesData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipesData);

        Set<Recipe> recipes = recipeService.getRecipes();

        assertEquals(1, recipes.size());
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testGetRecipeById() {

        Recipe recipeMock = new Recipe();
        recipeMock.setId(RECIPE_ID);
        Optional<Recipe> optionalRecipeMock = Optional.of(recipeMock);

        when(recipeRepository.findById(anyLong())).thenReturn(optionalRecipeMock);

        Recipe foundRecipe = recipeService.findRecipeById(1L);

        assertNotNull("Null Recipe Returned.", foundRecipe);
        assertEquals(RECIPE_ID, foundRecipe.getId());

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void testDeleteById() {

        Long idToDelete = 2L;

        doNothing().when(recipeRepository).deleteById(anyLong());

        recipeService.deleteById(idToDelete);

        verify(recipeRepository, times(1)).deleteById(anyLong());
    }
}