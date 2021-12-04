package com.example.springstarbucks.paymentapi;

import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsRepo extends JpaRepository<PaymentsCommand, Long> {


}
