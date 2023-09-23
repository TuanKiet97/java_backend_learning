package com.project.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    @JsonProperty("order_id")
    @Min(value=1, message = "The value must > 0")
    private Long orderId;


    @JsonProperty("product_id")
    @Min(value=1, message = "The value must > 0")
    private Long productId;

    @Min(value=0, message = "The value must > 0")
    private Long price;

    @Min(value=1, message = "The value must >= 1")
    @JsonProperty("number_of_products")
    private int numberOfProducts;

    @Min(value=1, message = "The value must > 0")
    @JsonProperty("total_money")
    private int totalMoney;

    private String color;
}
