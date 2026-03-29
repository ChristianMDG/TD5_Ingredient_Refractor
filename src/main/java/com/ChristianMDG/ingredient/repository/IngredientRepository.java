package com.ChristianMDG.ingredient.repository;

import com.ChristianMDG.ingredient.config.DataSource;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.StockValue;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;


import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class IngredientRepository {
    private final DataSource dataSource;

    public List<Ingredient> getAllIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        String getAllIngredientSql = "SELECT id,name,price,category FROM Ingredient";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getAllIngredientSql)) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));
                ingredients.add(ingredient);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public Ingredient getIngredientById(int id) {
        Ingredient ingredient = new Ingredient();
        String getIngredientSql = "SELECT id,name,price,category FROM Ingredient WHERE id=?";
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getIngredientSql)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return ingredient;
    }

    public StockValue getStockValueAt(Instant t, Integer ingredientIdentifier, UnitEnum unit) {


        StockValue stockValue = new StockValue();
        String getStockValueSql = """
              select unit , sum(
              case
                  when stockmovement.type = 'OUT' then stockmovement.quantity * -1
                            else stockmovement.quantity
                            end
                            ) as actual_quantity from stockmovement
              where creation_datetime <= ?
              and stockmovement.id_ingredient = ?
              and stockmovement.unit = ?::unit_type
              group by unit
            """;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(getStockValueSql)) {
            preparedStatement.setTimestamp(1, Timestamp.from(t));
            preparedStatement.setInt(2, ingredientIdentifier);
            preparedStatement.setString(3, unit.name());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                stockValue.setUnit(UnitEnum.valueOf(resultSet.getString("unit")));
                stockValue.setQuantity(resultSet.getDouble("actual_quantity"));
            }
            return stockValue;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    }



