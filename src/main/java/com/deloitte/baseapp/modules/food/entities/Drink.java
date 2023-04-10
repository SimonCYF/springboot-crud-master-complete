package com.deloitte.baseapp.modules.food.entities;

import com.deloitte.baseapp.commons.GenericEntity;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table
@Data
public class Drink{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private Double value;

//    @Override
//    public void update(Drink source) {
//        this.name = source.getName();
//        this.price = source.getPrice();
//    }
//
//    @Override
//    public Long getId() {
//        return this.id;
//    }
//
//    @Override
//    public Drink createNewInstance() {
//        Drink newInstance = new Drink();
//        newInstance.update(this);
//
//        return newInstance;
//    }
}
