package com.example.starbucksapi;

import java.util.List;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

@RequestMapping("/api")
@RestController
public class StarbucksOrderController {
    private final StarbucksOrderRepository repository;

    @Autowired
    private StarbucksCardRepository cardsRepository;

    class Message {
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String msg) {
            status = msg;
        }
    }

    private HashMap<String, StarbucksOrder> orders = new HashMap<>();

    public StarbucksOrderController(StarbucksOrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/orders")
    List<StarbucksOrder> all() {
        return repository.findAll();
    }

    @GetMapping("/order/register/{regid}")
    StarbucksOrder getActiveOrder(@PathVariable String regid, HttpServletResponse response) {
        StarbucksOrder active = orders.get(regid);
        if (active != null) {
            return active;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order not found!");
        }
    }

    @DeleteMapping("/orders")
    void deleteAll() {
        repository.deleteAllInBatch();
        orders.clear();
    }

    @PostMapping("/order/register/{regid}")
    @ResponseStatus(HttpStatus.CREATED)
    StarbucksOrder newOrder(@PathVariable String regid, @RequestBody StarbucksOrder order) {
        System.out.println("Placing order (Reg ID = " + regid + ") => " + order);

        if (order.getDrink().equals("") || order.getMilk().equals("") || order.getSize().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Order Request!");
        }

        StarbucksOrder active = orders.get(regid);
        if (active != null) {
            System.out.println("Active order (Reg ID = " + regid + ") => " + active);
            if (active.getStatus().equals("Ready for Payment."))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Active order exists!");
        }

        double price = 0.0;
        switch (order.getDrink()) {
        case "Caffe Latte":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.65;
                break;
            case "Venti":
                price = 3.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            break;
        case "Caffe Americano":
            switch (order.getSize()) {
            case "Tall":
                price = 2.25;
                break;
            case "Grande":
                price = 2.65;
                break;
            case "Venti":
                price = 2.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            break;
        case "Caffe Mocha":
            switch (order.getSize()) {
            case "Tall":
                price = 3.45;
                break;
            case "Grande":
                price = 4.15;
                break;
            case "Venti":
                price = 4.45;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            break;
        case "Espresso":
            switch (order.getSize()) {
            case "Short":
                price = 1.75;
                break;
            case "Tall":
                price = 1.95;
                break;
            case "Venti":
                price = 4.45;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            break;
        case "Capuccino":
            switch (order.getSize()) {
            case "Tall":
                price = 2.95;
                break;
            case "Grande":
                price = 3.65;
                break;
            case "Venti":
                price = 3.95;
                break;
            default:
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid size!");
            }
            break;
        default:
            System.out.println(order);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Drink!");
        }

        double tax = 0.0725;
        double total = price + (price * tax);
        double scale = Math.pow(10, 2);

        double rounded = Math.round(total * scale) / scale;
        order.setTotal(rounded);

        order.setStatus("Ready for Payment.");
        StarbucksOrder new_order = repository.save(order);
        orders.put(regid, new_order);
        System.out.println(new_order);
        return new_order;
    }

    @DeleteMapping("/order/register/{regid}")
    Message deleteActiveOrder(@PathVariable String regid) {
        StarbucksOrder active = orders.get(regid);
        if (active != null) {
            orders.remove(regid);
            Message msg = new Message();
            msg.setStatus("Active order cleared!");
            return msg;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order not found!");
        }
    }

    @PostMapping("/order/register/{regid}/pay/{cardnum}")
    StarbucksCard processOrder(@PathVariable String regid, @PathVariable String cardnum) {
        System.out.println("Pay for order (Reg ID = " + regid + " Using Card = " + cardnum);

        StarbucksOrder active = orders.get(regid);
        if (active == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order not Found!");
        }
        if (cardnum.equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card Number not provided!");
        }

        if (active.getStatus().startsWith("Paid with Card")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Clear paid active order!");
        }

        StarbucksCard card = cardsRepository.findByCardNumber(cardnum);
        if (card == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card not Found!");
        }

        if (!card.isActivated()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card not activated!");
        }

        double price = active.getTotal();
        double balance = card.getBalance();
        if (balance - price < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds on Card.");
        }

        double new_balance = balance - price;
        card.setBalance(new_balance);
        String status = "Paid with Card: " + cardnum + " Balance: $" + new_balance + ".";
        active.setStatus(status);
        cardsRepository.save(card);
        repository.save(active);
        return card;
    }
}
