package com.ChristianMDG.ingredient.validator;

import org.springframework.stereotype.Component;


    @Component
    public class IngredientValidator {

        public void validatePagination(int size, int offset) {
            if (size <= 0) {
                throw new IllegalArgumentException("Size must be greater than 0");
            }

            if (offset < 0) {
                throw new IllegalArgumentException("Offset must be >= 0");
            }
        }

        public void validateId(Integer id) {
            if (id == null || id <= 0) {
                throw new IllegalArgumentException("Invalid id");
            }
        }

        public void validateStockParams(Integer id, String at, String unit) {
            validateId(id);

            if (at == null || at.isBlank()) {
                throw new IllegalArgumentException("Parameter 'at' is required");
            }

            if (unit == null || unit.isBlank()) {
                throw new IllegalArgumentException("Parameter 'unit' is required");
            }
        }
    }
