package com.example.springstarbucks.cardapi;

import java.util.List;
import java.util.Random;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RestController
class StarbucksCardController {

	private final StarbucksCardRepository repository;

	StarbucksCardController(StarbucksCardRepository repository) {
		this.repository = repository;
	}

	@PostMapping("/cards")
	StarbucksCard newCard() {
		StarbucksCard newcard = new StarbucksCard();
		Random random = new Random();
		int num = random.nextInt(900000000) + 100000000;
		int code = random.nextInt(900) + 100;

		newcard.setCardNumber(String.valueOf(num));
		newcard.setCardCode(String.valueOf(code));
		newcard.setBalance(20.00);
		newcard.setStatus("New Card");
		return repository.save(newcard);
	}

	@GetMapping("/cards")
	List<StarbucksCard> all() {
		return repository.findAll();
	}

	@DeleteMapping("/cards")
	HashMap<String,String> deleteAll() {

		repository.deleteAllInBatch();

		HashMap<String, String> map = new HashMap<>();
		String status = "All Cards Cleared!";
    	map.put("status", status);
		
		return map;
	}

	//baeldung.com/exception-handling-for-rest-with-spring
	//baeldung.com/spring-pathvariable
	@GetMapping("/card/{num}")
	StarbucksCard getOne(@PathVariable String num, HttpServletResponse response) {
		StarbucksCard card = repository.findByCardNumber(num);
		if(card == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
		return card;
	}

	@PostMapping("/card/activate/{num}/{code}")
	StarbucksCard activate(@PathVariable String num, @PathVariable String code, HttpServletResponse response) {
		StarbucksCard card = repository.findByCardNumber(num);

		if(card == null)
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Found!");
		if(card.getCardCode().equals(code)) {
			card.setActivated(true);
			repository.save(card);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error. Card Not Valid!");
		}

		return card;
	}














}