package com.example.rest.mapper;

import com.example.rest.dto.CategoryResponseDto;
import com.example.rest.entity.Category;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.WARN,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {

    Category toEntity(String name);

    //    @Mapping(target = "categoryName", expression = "java(getCategoryName(category))")
    @Mapping(target = "categoryName", source = "category", qualifiedByName = "name")
    @Mapping(target = "id", source = "identifier")
    CategoryResponseDto toResponseDto(Category category);


    @Named("name")
    default String getCategoryName(Category category) {
        return category.getName();
    }

    List<CategoryResponseDto> toResponseDtoList(List<Category> categories);

}
