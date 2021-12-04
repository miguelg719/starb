package com.example.springstarbucks.orderapi;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Entity
@Table(indexes=@Index(name = "altIndex", columnList = "id", unique = true))
@Data
@RequiredArgsConstructor
public class StarbucksOrder {
    private @Id @GeneratedValue Long id;

    private double total;
    private String status;
    @Column(nullable = false) private String size;
    @Column(nullable = false) private String drink;
    @Column(nullable = false) private String milk;


}





// import java.util.Objects;
// import javax.persistence.*;

// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.Table;
// import javax.validation.constraints.NotEmpty;

// import lombok.Data;
// import lombok.RequiredArgsConstructor;

// /* https://spring.io/guides/tutorials/rest/ */

// @Entity
// @Table(name = "ORDERS")  //order is received keyword in H2DB. so used the orders instead of it.  
//                         //TODO: change to MySQL database
// @Data
// @RequiredArgsConstructor
// class StarbucksOrder {

//     @Column(name = "id")
//     @Id
//     @GeneratedValue(strategy= GenerationType.AUTO)	
//     private int id;

//     @Column(name = "drink")
//     @NotEmpty(message = "drink cannot be empty")
//     private String drink;

//     @Column(name = "milk")
//     @NotEmpty(message = "milk cannot be empty")
//     private String milk;

//     @Column(name = "size")
//     @NotEmpty(message = "size cannot be empty")
//     private String size;

//     @Column(name = "total")
//     private double total;

//     @Column(name = "status")
//     private String status;

//     public int getId() {
//         return id;
//     }
//     public void setId(int id) {
//         this.id = id;
//     }
//     public String getDrink() {
//         return drink;
//     }
//     public void setDrink(String drink) {
//         this.drink = drink;
//     }
//     public String getMilk() {
//         return milk;
//     }
//     public void setMilk(String milk) {
//         this.milk = milk;
//     }
//     public String getSize() {
//         return size;
//     }
//     public void setSize(String size) {
//         this.size = size;
//     }
//     public double getTotal() {
//         return total;
//     }
//     public void setTotal(double total) {
//         this.total = total;
//     }
//     public String getStatus() {
//         return status;
//     }
//     public void setStatus(String status) {
//         this.status = status;
//     }
// } 