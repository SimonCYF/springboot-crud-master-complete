package com.deloitte.baseapp.modules.food.controllers;

import com.deloitte.baseapp.commons.*;
import com.deloitte.baseapp.modules.food.entities.Drink;
import com.deloitte.baseapp.modules.food.payloads.CreateDrinkRequest;
import com.deloitte.baseapp.modules.food.payloads.CreateFoodRequest;
import com.deloitte.baseapp.modules.food.payloads.DeleteDrinkRequest;
import com.deloitte.baseapp.modules.food.repositories.DrinkRepository;
import com.deloitte.baseapp.modules.food.services.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/api/drinks")
public class DrinkController {

    @Autowired
    DrinkService drinkService;

    @Autowired
    DrinkRepository drinkRepository;

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
    @GetMapping("/getDrink/{id}")
    public MessageResponse getDrink (@PathVariable("id") final Long id){
        try{
            return new MessageResponse(drinkService.getDrink(id), "Success");
        }catch(Exception e){
            return new MessageResponse<>(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/updateDrink/{id}")
    public MessageResponse updateDrink (@PathVariable("id") final Long id,  @Valid @RequestBody CreateDrinkRequest payload){
        System.out.println(id+ " "+payload);
        try{
            return new MessageResponse(drinkService.updateDrink(id, payload), "Success");
        }catch(Exception e){
            return new MessageResponse<>(e.getMessage());
        }
    }

    @CrossOrigin
    @GetMapping("/pagination")
    public MessageResponse paging( @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        try{
            final PagingResult<Drink> result = drinkService.pagination(page, size);
            return new MessageResponse(result, "Successfully Load!");
        }catch (Exception e){
            return new MessageResponse<>(e.getMessage());
        }
    }

    @CrossOrigin
    @PostMapping("/create")
    public MessageResponse createDrink(@RequestBody @Valid CreateDrinkRequest createDrinkRequest){
        try{
            return new MessageResponse(drinkService.saveDrink(createDrinkRequest), "Successfully Created!");
        }catch (Exception e){
            return new MessageResponse(e.getMessage());
        }
    }

    @CrossOrigin
    @DeleteMapping("bulk-delete")
    public MessageResponse delete(@RequestParam("id")  List<Long> payload) {
        try {
            System.out.println(payload);
            return new MessageResponse(drinkService.deleteBulkById((List<Long>) payload), "Successfully Deleted!");
        } catch (ObjectNotFoundException e) {
            return new MessageResponse(e.getMessage());
        }
    }
}
