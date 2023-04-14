package com.deloitte.baseapp.modules.food.payloads;

import lombok.Data;

@Data
public class CreateDrinkRequest {
    private String name;

    private Double value;
}
