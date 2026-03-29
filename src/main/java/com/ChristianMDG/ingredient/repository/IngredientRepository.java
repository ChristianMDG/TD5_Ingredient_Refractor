package com.ChristianMDG.ingredient.repository;

import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
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

    public StockValue getStockValueAt(Instant t, Integer ingredientId,UnitEnum unit) {
        String sql = """
        select unit, sum(
            case
                when type = 'OUT' then quantity * -1
                else quantity
            end
        ) as actual_quantity 
        from stockmovement
        where creation_datetime <= ?
        and id_ingredient = ?
        and unit = ?::unit_type
        group by unit
    """;

        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                            new StockValue(
                                    rs.getDouble("actual_quantity"),
                                    UnitEnum.valueOf(rs.getString("unit"))
                            )
                    , Timestamp.from(t), ingredientId,unit.name());
        } catch (EmptyResultDataAccessException e) {
            return new StockValue(0.0, null);
        }
    }
}
