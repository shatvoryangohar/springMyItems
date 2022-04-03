package com.example.springmyitems.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {
    private int id;
   // @NotBlank(message = "Title can't be empty")
    @Size(min = 2, max = 40, message = "Title length should be between 2 and 40")
    private String title;
    @DecimalMin(value = "1", message = "Price should be > 1")
    private double price;
   @NotEmpty(message = "Description should not be empty ")
    private String description;
    private List<Integer> categories;
}
