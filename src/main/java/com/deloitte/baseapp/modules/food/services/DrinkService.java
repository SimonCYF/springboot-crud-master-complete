package com.deloitte.baseapp.modules.food.services;

import com.deloitte.baseapp.commons.PagingResult;
import com.deloitte.baseapp.modules.food.entities.Drink;
import com.deloitte.baseapp.modules.food.entities.Food;
import com.deloitte.baseapp.modules.food.payloads.CreateDrinkRequest;
import com.deloitte.baseapp.modules.food.repositories.DrinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

@Service
@Slf4j
public class DrinkService {

    @Autowired
    DrinkRepository drinkRepository;

    public PagingResult<Drink> pagination(int page, int length) {
        final Pageable pageable = PageRequest.of(page, length);
        final Page<Drink> paging = drinkRepository.findAll(pageable);
        final List<Drink> result = paging.getContent();

        PagingResult<Drink> resp = new PagingResult<>();
        resp.setContent(result);
        resp.setLength(length);
        resp.setTotal(paging.getTotalPages());
        resp.setPage(page);
        return resp;
    }

    public List getAllDrinks(){
        System.out.println(drinkRepository.findAll());
        return drinkRepository.findAll();
    }

    public Drink saveDrink(CreateDrinkRequest createDrinkRequest){
        System.out.println("HI");
        Drink drink = new Drink();
        drink.setName(createDrinkRequest.getName());
        drink.setValue(createDrinkRequest.getPrice());
        System.out.println(drink);

        return drinkRepository.save(drink);
    }
}
