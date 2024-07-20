package com.example.rest.mapper;

import com.example.rest.dto.CategoryResponseDto;
import com.example.rest.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public Category toEntity(String name) {
        Category category = new Category();
        category.setName(name);
        return category;
    }

    public CategoryResponseDto toResponseDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(category.getId());
        categoryResponseDto.setName(category.getName());
        return categoryResponseDto;
    }


}
