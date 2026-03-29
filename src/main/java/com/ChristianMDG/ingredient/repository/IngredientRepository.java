package com.ChristianMDG.ingredient.repository;

import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class IngredientRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String getIngredientsql = "SELECT ingredient.id,ingredient.name,ingredient.price,ingredient.category FROM ingredient";

            ingredients = jdbcTemplate.query(getIngredientsql,(rs, rowNum) -> new Ingredient(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    CategoryEnum.valueOf(rs.getString("category"))
            ));
        return ingredients;
    }


    public Ingredient getIngredientById(Integer id) {
        Ingredient ingredient = null;
        String sql = "select * from ingredient where id = ?";
       try{
           ingredient = jdbcTemplate.queryForObject(sql,(rs, rowNum) ->
                   new Ingredient(
                           rs.getInt("id"),
                           rs.getString("name"),
                           rs.getDouble("price"),
                           CategoryEnum.valueOf(rs.getString("category"))
                   )
                   ,id);

       } catch (EmptyResultDataAccessException e) {
           System.out.println(e.getMessage());
       }
       return ingredient;
    }
}
