package com.example.springpayments;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="Payments")
@Data
@RequiredArgsConstructor
class PaymentsCommand {

    private @Id @GeneratedValue Long id;

    transient private String action ;
    private String firstname ;
    private String lastname ;
    private String address ;
    private String city ;
    private String state ;
    private String zip ;
    private String phone ;
    private String cardnum ;
    private String expmon ;
    private String expyear ;
    private String cvv ;
    private String email ;
    private String notes ;

    private String orderNumber ;
    private String transactionAmount ;
    private String transactionCurrency ;
    private String authId ;
    private String authStatus ;
    private String captureId ;
    private String captureStatus;

    public String firstname() { return firstname ; }
    public String lastname() { return lastname ;}
    public String address() { return address ;}
    public String city() { return city ;}
    public String state() { return state ;}
    public String zip() { return zip ;}
    public String phone() { return phone ;}
    public String cardnum() { return cardnum ;}
    public String expmon() { return expmon ;}
    public String expyear() { return expyear ;}
    public String cvv() { return cvv ;}
    public String email() { return email ;}
    public String notes() { return notes ;}

    
}
