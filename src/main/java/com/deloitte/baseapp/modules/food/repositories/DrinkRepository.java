package com.deloitte.baseapp.modules.food.repositories;

import com.deloitte.baseapp.modules.food.entities.Drink;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink,Long> {

    Page<Drink> findAll(Pageable pageable);
    Drink save(Drink drink);



}
