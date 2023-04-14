package com.deloitte.baseapp.modules.food.services;

import com.deloitte.baseapp.commons.ObjectNotFoundException;
import com.deloitte.baseapp.commons.PagingResult;
import com.deloitte.baseapp.modules.food.entities.Drink;
import com.deloitte.baseapp.modules.food.entities.Food;
import com.deloitte.baseapp.modules.food.payloads.CreateDrinkRequest;
import com.deloitte.baseapp.modules.food.payloads.CreateFoodRequest;
import com.deloitte.baseapp.modules.food.repositories.DrinkRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class DrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    public PagingResult<Drink> pagination(int page, int length) {
        final Pageable pageable = PageRequest.of(page-1, length);
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

    public Drink getDrink(final Long id) throws ObjectNotFoundException {
        Optional<Drink> optionalDrink = drinkRepository.findById(id);
        if (optionalDrink.isEmpty()) {
            throw new ObjectNotFoundException();
        }
        return optionalDrink.get();
    }

    public Drink updateDrink (final Long id, final CreateDrinkRequest payload) throws  ObjectNotFoundException{
        Optional<Drink> optionalDrink = drinkRepository.findById(id);
        System.out.println(id+ " "+payload);
        if (optionalDrink.isEmpty()) {
            throw new ObjectNotFoundException();
        }

        Drink drink = optionalDrink.get();
        drink.setName(payload.getName());
        drink.setValue(payload.getValue());

        System.out.println(drink);


        return drinkRepository.save(drink);
    }

    @Transactional
    public Drink saveDrink(CreateDrinkRequest createDrinkRequest){
        System.out.println(createDrinkRequest);
        Drink drink = new Drink();
        drink.setName(createDrinkRequest.getName());
        drink.setValue(createDrinkRequest.getValue());
        System.out.println(drinkRepository.save(drink));

        return drinkRepository.save(drink);
    }

    public boolean deleteBulkById(List<Long> id) throws ObjectNotFoundException {
        if(id.isEmpty()){
            throw new ObjectNotFoundException();
        }else{
            drinkRepository.deleteAllById(id);
        }

        return true;
    }

    public boolean delete(final Long id) throws ObjectNotFoundException {
        Optional<Drink> optionalFood = drinkRepository.findById(id);
        if (optionalFood.isEmpty()) {
            throw new ObjectNotFoundException();
        }
        drinkRepository.deleteById(id);
        return true;
    }



}
