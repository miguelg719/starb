package com.example.starbucksapi;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@RequestMapping("/api")
@RestController
class StarbucksCardController {
    private final StarbucksCardRepository repository;

    StarbucksCardController(StarbucksCardRepository repository) {
        this.repository = repository;
    }

    class Message {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String msg) {
            status = msg;
        }
    }

    /* Create a new starbucks card */
    @PostMapping("/cards")
    StarbucksCard newCard() {
        StarbucksCard newcard = new StarbucksCard();

        Random random = new Random();
        int num = random.nextInt(900000000) + 100000000;
        int code = random.nextInt(900) + 100;

        newcard.setCardNumber(String.valueOf(num));
        newcard.setCardCode(String.valueOf(code));
        newcard.setBalance(20.00);
        newcard.setActivated(false);
        newcard.setStatus("New Card");
        return repository.save(newcard);
    }

    /* Get list of starbucks cards */
    @GetMapping("/cards")
    List<StarbucksCard> all() {
        return repository.findAll();
    }

    /* Delete all starbucks cards */
    @DeleteMapping("/cards")
    Message deleteAll() {
        repository.deleteAllInBatch();
        Message msg = new Message();
        msg.setStatus("All cards cleared");
        return msg;
    }

    /* Get details of a starbucks card */
    @GetMapping("/card/{num}")
    StarbucksCard getOne(@PathVariable String num, HttpServletResponse response) {
        StarbucksCard card = repository.findByCardNumber(num);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error--Card not found");
        }
        return card;
    }

    /* Activate a starbucks card */
    @PostMapping("/card/activate/{num}/{code}")
    StarbucksCard activate(@PathVariable String num, @PathVariable String code, HttpServletResponse response) {
        StarbucksCard card = repository.findByCardNumber(num);
        System.out.println(num);
        System.out.println(card);

        if (card == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error--Card not found");
        }
        if (card.getCardCode().equals(code)) {
            card.setActivated(true);
            repository.save(card);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error--Card not valid");
        }
        return card;
    }
}