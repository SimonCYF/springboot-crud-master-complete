package com.deloitte.baseapp.modules.food.controllers;

import com.deloitte.baseapp.commons.GenericController;
import com.deloitte.baseapp.commons.MessageResponse;
import com.deloitte.baseapp.commons.PagingRequest;
import com.deloitte.baseapp.commons.PagingResult;
import com.deloitte.baseapp.modules.food.entities.Drink;
import com.deloitte.baseapp.modules.food.entities.Food;
import com.deloitte.baseapp.modules.food.payloads.CreateDrinkRequest;
import com.deloitte.baseapp.modules.food.repositories.DrinkRepository;
import com.deloitte.baseapp.modules.food.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/drinks")
public class DrinkController {

    @Autowired
    DrinkService drinkService;
    /*public DrinkController(DrinkRepository repository) {
        super(repository, "food");
    }*/

    @CrossOrigin
    @GetMapping("/getAll")
    public MessageResponse getAllDrinks (){
        try{
            return new MessageResponse(drinkService.getAllDrinks(), "Success");
        }catch(Exception e){
            return new MessageResponse<>(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/pagination")
    public MessageResponse paging( @RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        try{
            final PagingResult<Drink> result = drinkService.pagination(page, size);
            return new MessageResponse(result, "Success");
        }catch (Exception e){
            return new MessageResponse<>(e.getMessage());
        }
    }


    @CrossOrigin
    @PostMapping("/create")
    public MessageResponse create(CreateDrinkRequest createDrinkRequest){
        try{
            return new MessageResponse(drinkService.saveDrink(createDrinkRequest));
        }catch (Exception e){
            return new MessageResponse(e.getMessage());
        }
    }
}
