package com.example.springstarbucks.orderapi;

import org.springframework.data.jpa.repository.JpaRepository; 

interface StarbucksOrderRepository extends JpaRepository<StarbucksOrder, Long>{
    StarbucksOrder findById(int id);
    StarbucksOrder deleteById(int id);
}