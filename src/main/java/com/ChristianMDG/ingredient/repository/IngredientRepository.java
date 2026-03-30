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

    public List<Ingredient> findIngredients(Integer page, Integer size){
        List<Ingredient> ingredients = new ArrayList<>();

        String findIngredientsSql = """
               select ingredient.id , ingredient.name, ingredient.price, ingredient.category from ingredient
               limit ? offset ?
               """;
        int offset = (page - 1) * size;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findIngredientsSql)){
            preparedStatement.setInt(1,size);
            preparedStatement.setInt(2,offset);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("id"));
                ingredient.setName(resultSet.getString("name"));
                ingredient.setPrice(resultSet.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("category")));
                ingredients.add(ingredient);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return ingredients;
    }

    public List<Ingredient> findIngredientsByCriteria(
            String ingredientName,
            CategoryEnum category,
            String dishName,
            int page,
            int size
    ) {

        List<Ingredient> ingredients = new ArrayList<>();
        int offset = (page - 1) * size;

        StringBuilder sql = new StringBuilder("""
        SELECT i.id, i.name, i.price, i.category
        FROM ingredient i
        """);

        List<Object> params = new ArrayList<>();

        if (dishName != null && !dishName.isBlank()) {
            sql.append("""
            JOIN dishingredient di ON di.id_ingredient = i.id
            JOIN dish d ON d.id = di.id_dish
        """);
        }

        sql.append(" WHERE 1=1 ");

        if (ingredientName != null && !ingredientName.isBlank()) {
            sql.append(" AND i.name ILIKE ?");
            params.add("%" + ingredientName + "%");
        }

        if (category != null) {
            sql.append(" AND i.category = ?::ingredient_category");
            params.add(category.name());
        }

        if (dishName != null && !dishName.isBlank()) {
            sql.append(" AND d.name ILIKE ?");
            params.add("%" + dishName + "%");
        }

        sql.append(" LIMIT ? OFFSET ?");
        params.add(size);
        params.add(offset);

        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setId(rs.getInt("id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setPrice(rs.getDouble("price"));
                ingredient.setCategory(CategoryEnum.valueOf(rs.getString("category")));
                ingredients.add(ingredient);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la recherche des ingrédients", e);
        }

        return ingredients;
    }


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



