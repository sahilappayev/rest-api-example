package com.example.rest.repository;

import com.example.rest.entity.Category;
import com.example.rest.view.CategoryView;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    List<Category> findAllByOrderByIdentifierDesc();

    @Query(value = "select * from categories order by identifier desc", nativeQuery = true)
    List<Category> findAllByIdDesc();

    @Query(value = "select c as category from Category c")
    List<CategoryView> findAllNames();

}
