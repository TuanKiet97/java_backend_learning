package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 200, message = "Title must be 3 and 200 characters")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0" )
    @Max(value = 1000000000, message = "Price must be less than or equal to 100000000")
    private int price;

    private String thumbnail;

    private String description;

    @JsonProperty("category_id")
    private String categoryId;

    private List<MultipartFile> files;
}
