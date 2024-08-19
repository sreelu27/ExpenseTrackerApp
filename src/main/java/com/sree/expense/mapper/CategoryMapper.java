package com.sree.expense.mapper;

import com.sree.expense.dto.CategoryDto;
import com.sree.expense.entity.Category;

public class CategoryMapper {

    public static Category mapToCategory(CategoryDto categoryDto){
        return new Category(
                categoryDto.id(),
                categoryDto.name()
        );
    }

    public static CategoryDto mapToCategoryDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}

