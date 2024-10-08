package com.example.rest.controller;

import com.example.rest.dto.CategoryRequestDto;
import com.example.rest.dto.CategoryResponseDto;
import com.example.rest.service.CategoryService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/categories")
public class CategoryController {

    // HTTP REQUEST:  url, query param, request body, request headers
    // HTTP RESPONSE:   http status, response body, response headers
    private final CategoryService categoryService;

    @GetMapping(consumes = MediaType.ALL_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<CategoryResponseDto> getCategories(@RequestParam(defaultValue = "0") @Parameter(description = "Sehifenin sayi, 0-dan bashlamaqla") int page,
                                                   @RequestParam(defaultValue = "5") @Parameter(description = "Her sehifede olan elementlerein sayi, 0-dan bashlamaqla") int size,
                                                   @RequestParam String sortField,
                                                   @RequestParam Sort.Direction sortDirection) {
        return categoryService.findAll(page, size, sortField, sortDirection);
    }

    @GetMapping("/names")
    public List<CategoryResponseDto> getCategoryNames() {
        return categoryService.findAllNames();
    }

    // categories?id=5&name=sahil&
    // categories/5?name=sahil
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
//        return categoryService.getById(id);
        return ResponseEntity.ok(categoryService.getById(id));
    }

    @GetMapping("/by-name")
    public ResponseEntity<CategoryResponseDto> getCategoryByName(@RequestParam String name) {
        return ResponseEntity.ok(categoryService.getByName(name));
    }

    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable(value = "categoryId") Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<CategoryResponseDto> create(@RequestParam @NotBlank String name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.create(name));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id,
                                                      @RequestBody @Valid CategoryRequestDto categoryRequestDto) {
        return ResponseEntity.ok(categoryService.update(id, categoryRequestDto));
    }


    // .zip    application/zip, application/octet-stream, application/x-zip-compressed, multipart/x-zip
    @GetMapping(value = "/zip")
    public ResponseEntity<byte[]> generateCsv() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/zip");
        headers.set("content-disposition", "inline; filename=categories.zip");
        return new ResponseEntity<>(categoryService.generateCsvZip(), headers, HttpStatus.OK);

    }


}
