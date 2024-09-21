package com.example.rest.service;

import com.example.rest.dto.CategoryResponseDto;
import com.example.rest.entity.Category;

public class StaticService {

    public static CategoryResponseDto toCategoryResponseDto(Category category) {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setCategoryName(category.getName());
        categoryResponseDto.setId(category.getIdentifier());
        return categoryResponseDto;
    }

}
