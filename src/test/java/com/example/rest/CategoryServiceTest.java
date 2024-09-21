package com.example.rest;

import com.example.rest.dto.CategoryResponseDto;
import com.example.rest.entity.Category;
import com.example.rest.error.CustomNotFoundException;
import com.example.rest.mapper.CategoryMapper;
import com.example.rest.repository.CategoryRepository;
import com.example.rest.service.CategoryService;
import com.example.rest.service.StaticService;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    private static Category category;
    private static CategoryResponseDto expected;

    @Spy
    @InjectMocks
    private CategoryService categoryService;

//    @BeforeAll
//    static void initBeforeAll(){
//        System.out.println("Before All");
//        category = category();
//        expected = categoryResponseDto();
//    }

    @BeforeEach
    void init() {
        System.out.println("Before Each");
//        categoryService = Mockito.spy(new CategoryService(categoryRepository, categoryMapper));

        category = category();
        expected = categoryResponseDto();
    }

    @Test
    void getById_When_CategoryName_Is_Null() {
        //Arrange
        expected.setCategoryName(null);

        try (MockedStatic<StaticService> staticServiceMock = Mockito.mockStatic(StaticService.class)) {
            //Act
            Mockito.when(categoryRepository.findById(5L)).thenReturn(Optional.of(category));

            staticServiceMock.when(() -> StaticService.toCategoryResponseDto(Mockito.any(Category.class)))
                    .thenReturn(expected);

//          Mockito.when(categoryMapper.toResponseDto(Mockito.any(Category.class))).thenReturn(expected);

            CategoryResponseDto actual = categoryService.getById(5L);

            Assertions.assertNull(actual.getCategoryName());

            Mockito.verify(categoryRepository, Mockito.only()).findById(5L);
        }

//        Mockito.verify(categoryMapper, Mockito.only()).toResponseDto(Mockito.any(Category.class));
    }


    @Test
    void getById_When_Success() {
        //Act
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
//        Mockito.when(categoryMapper.toResponseDto(category)).thenReturn(expected);

        CategoryResponseDto actual = categoryService.getById(1L);

        //Assert
//        Assertions.assertEquals(expected, actual);


        org.assertj.core.api.Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        Assertions.assertEquals(category.getIdentifier(), actual.getId());
        Assertions.assertEquals(category.getName(), actual.getCategoryName());

//        Assertions.assertEquals(expected.getId(), actual.getId());
//        Assertions.assertTrue(actual.getId().equals(expected.getId()));

        Mockito.verify(categoryRepository, Mockito.only()).findById(1L);
//        Mockito.verify(categoryMapper, Mockito.only()).toResponseDto(category);
    }

    @Test
    void getById_When_NotFound() {
        Mockito.when(categoryRepository.findById(1L)).thenThrow(new CustomNotFoundException(Category.class, 1L));

        Assertions.assertThrows(CustomNotFoundException.class, () -> categoryService.getById(1L));
    }

    @Test
    void delete_When_Success() {
        Mockito.doReturn(category).when(categoryService).findById(1L);
        Mockito.doNothing().when(categoryRepository).delete(category);

        categoryService.delete(1L);

        Mockito.verify(categoryRepository, Mockito.times(1)).delete(category);
    }


    private static Category category() {
        Category category = new Category();
        category.setName("Category Name");
        category.setIdentifier(1);
        return category;
    }

    private static CategoryResponseDto categoryResponseDto() {
        CategoryResponseDto categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(1);
        categoryResponseDto.setCategoryName("Category Name");
        return categoryResponseDto;
    }

}
