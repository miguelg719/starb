package com.example.starbuckscashiers;

import com.example.starbuckscashiers.StarbucksOrder;

import org.springframework.data.repository.CrudRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<StarbucksOrder, Integer> {

}
