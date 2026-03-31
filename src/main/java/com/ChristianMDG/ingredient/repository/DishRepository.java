package com.ChristianMDG.ingredient.repository;

import com.ChristianMDG.ingredient.config.DataSource;
import com.ChristianMDG.ingredient.entity.Dish;
import com.ChristianMDG.ingredient.entity.DishIngredient;
import com.ChristianMDG.ingredient.entity.Ingredient;
import com.ChristianMDG.ingredient.entity.enums.CategoryEnum;
import com.ChristianMDG.ingredient.entity.enums.DishTypeEnum;
import com.ChristianMDG.ingredient.entity.enums.UnitEnum;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class DishRepository {
    private final DataSource dataSource;

    public List<Dish> findAll() {
        List<Dish> dishes = new ArrayList<>();
        String findAllQuery = "SELECT id,name,dish_type,price FROM dish";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery);) {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Dish dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));
                dish.setPrice(resultSet.getDouble("price"));
                dish.setIngredients(findDishIngredientByDishId(dish.getId()));
                dishes.add(dish);
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return dishes;
    }

    public Dish findDishById(Integer id) {

        Dish dish = null;
        String findDishByIdQuery = """
              SELECT id, name, price, dish_type FROM dish WHERE id = ?;
                """;
        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findDishByIdQuery)){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                dish = new Dish();
                dish.setId(resultSet.getInt("id"));
                dish.setName(resultSet.getString("name"));
                dish.setDishType(DishTypeEnum.valueOf(resultSet.getString("dish_type")));
                dish.setPrice(resultSet.getDouble("price"));
                dish.setIngredients(findDishIngredientByDishId(id));
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
        return dish;
    }


    private List<DishIngredient> findDishIngredientByDishId(Integer idDish) {

        List<DishIngredient> listIngredient= new ArrayList<>();
        Dish dish=null;

        String findDishIngredientByDishIdSql = """
                SELECT di.id AS di_id, di.quantity_required, di.unit,
                       i.id AS ingredient_id, i.name AS ingredient_name,
                       i.price AS ingredient_price, i.category AS ingredient_category
                FROM dishingredient di
                JOIN ingredient i ON di.id_ingredient = i.id
                WHERE di.id_dish = ?
                """;

        try(Connection connection= dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(findDishIngredientByDishIdSql)){
            preparedStatement.setInt(1, idDish);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Ingredient ingredient = new Ingredient();
                ingredient.setId(resultSet.getInt("ingredient_id"));
                ingredient.setName(resultSet.getString("ingredient_name"));
                ingredient.setPrice(resultSet.getDouble("ingredient_price"));
                ingredient.setCategory(CategoryEnum.valueOf(resultSet.getString("ingredient_category")));

                DishIngredient dishIngredient = new DishIngredient();
                dishIngredient.setId(resultSet.getInt("di_id"));
                dishIngredient.setIngredient(ingredient);
               // dishIngredient.setDish(dish);
                dishIngredient.setQuantity(resultSet.getDouble("quantity_required"));
                dishIngredient.setUnit(UnitEnum.valueOf(resultSet.getString("unit")));
                listIngredient.add(dishIngredient);
            }
        }catch(SQLException e){
            throw new RuntimeException(e);
        }

        return listIngredient;
    }

    private  void deleteDishIngredientByDishId(Integer idDish) {
        String deteleDishingredientByDishIdQuery = """
               delete from dishingredient where id_dish = ?
        """;
        try(Connection connection = dataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deteleDishingredientByDishIdQuery)) {
            preparedStatement.setInt(1, idDish);
            preparedStatement.execute();
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    public Dish updateDishIngredients(Integer dishId, List<Ingredient> ingredients) {
        String insertBatch  = """
                insert into dishingredient (id_dish, id_ingredient, quantity_required, unit)
               values (?, ?, 1, 'KG')
                """;
        try(Connection connection = dataSource.getConnection()){
            connection.setAutoCommit(false);
            deleteDishIngredientByDishId(dishId);
            PreparedStatement preparedStatement = connection.prepareStatement(insertBatch);
            for (Ingredient ingredient : ingredients) {
                preparedStatement.setInt(1, dishId);
                preparedStatement.setInt(2, ingredient.getId());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            connection.commit();
            connection.setAutoCommit(true);
            return findDishById(dishId);
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }
}