package com.example.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequestDto {

    private String name;
    private int count;
    private double price;
    private Integer categoryId;
}
