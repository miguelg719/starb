package com.example.springstarbucks.cardapi;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long> {

	StarbucksCard findByCardNumber(String cardNumber);
}

// package com.example.springstarbucks.cardapi;

// //import com.example.springstarbucks.cardapi.Cards;
// import com.example.springstarbucksapi.StarbucksCard;

// import org.springframework.data.jpa.repository.JpaRepository;


// public interface StarbucksCardRepository extends JpaRepository<StarbucksCard, Long>{

//     // Cards findBycardNumber(String cardNumber);
//     // Cards findBycardNumberAndCardCode(String cardNumber, int cardCode);

// }