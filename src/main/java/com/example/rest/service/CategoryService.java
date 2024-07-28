package com.example.rest.service;

import com.example.rest.dto.CategoryRequestDto;
import com.example.rest.dto.CategoryResponseDto;
import com.example.rest.entity.Category;
import com.example.rest.error.CustomNotFoundException;
import com.example.rest.mapper.CategoryMapper;
import com.example.rest.repository.CategoryRepository;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public Page<CategoryResponseDto> findAll(int page, int size, String sortField, Sort.Direction sortDirection) {
        PageRequest pageRequest = PageRequest.of(page, size, sortDirection, sortField);

        Page<Category> all = categoryRepository.findAll(pageRequest);

        List<CategoryResponseDto> responseDtoList = categoryMapper.toResponseDtoList(all.getContent());

        return new PageImpl<>(responseDtoList, pageRequest, all.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<CategoryResponseDto> findAllNames() {
        return categoryRepository.findAllNames().stream()
                .map(v -> new CategoryResponseDto(v.getIdentifier(), v.getName())).toList();
    }

    public CategoryResponseDto getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Category.class, id));

        return categoryMapper.toResponseDto(category);
    }

    public CategoryResponseDto getByName(String name) {
        Category category = categoryRepository.findByName(name)
                .orElseThrow(() -> new CustomNotFoundException(Category.class, name));

        return categoryMapper.toResponseDto(category);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponseDto create(String name) {
        Category category = categoryMapper.toEntity(name);
        category = categoryRepository.save(category);
        return categoryMapper.toResponseDto(category);
    }

    public CategoryResponseDto update(Long id, CategoryRequestDto categoryRequestDto) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException(Category.class, id));

        category.setName(categoryRequestDto.getName());

        category = categoryRepository.save(category);

        return categoryMapper.toResponseDto(category);
    }


    public byte[] generateCsvZip() {
        List<Category> categories = categoryRepository.findAll();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ID").append(",").append("NAME").append("\n");
        for (Category category : categories) {
            stringBuilder.append(category.getIdentifier()).append(",").append(category.getName()).append("\n");
        }
        byte[] bytes;
        try (FileWriter fileWriter = new FileWriter("categories.csv")) {
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            File file = new File("categories.csv");
            bytes = zipFile(file);
            Files.delete(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

    public byte[] zipFile(File file) {
        try (FileOutputStream fos = new FileOutputStream("compressed.zip");
             ZipOutputStream zipOut = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(file)) {

            ZipEntry zipEntry = new ZipEntry(file.getName());
            zipOut.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zipOut.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] bytes;
        try {
            bytes = Files.readAllBytes(new File("compressed.zip").toPath());
            Files.delete(new File("compressed.zip").toPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }

}
