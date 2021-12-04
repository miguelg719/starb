package com.example.starbuckscashiers;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Entity;

import lombok.Data;


@Getter
@Setter
@RequiredArgsConstructor
@Entity
public class StarbucksOrder {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;


    private String drinkName;
    private String milk;
    private String size;

    private String firstname;
    private String lastname;
    private String address;
    private String city;
    private String state;
    private String zip;
    private String phonenumber;
    private String cardnumber;
    private String expmonth;
    private String expyear;
    private String cvv;
    private String email;
    private String notes;

    private String orderNumber;
    private String transactionAmount;
    private String transactionCurrency;
    private String authId;
    private String authStatus;
    private String captureId;
    private String captureStatus;

    @Override
    public String toString(){
        return drinkName + " " + milk + " Milk" + " " + size;
    }

}
