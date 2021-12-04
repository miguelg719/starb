package com.example.springpayments;

import org.springframework.data.jpa.repository.JpaRepository;

interface PaymentsRepo extends JpaRepository<PaymentsCommand, Long> {


}
