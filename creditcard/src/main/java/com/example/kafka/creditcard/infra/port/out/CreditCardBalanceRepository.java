package com.example.kafka.creditcard.infra.port.out;

import com.example.kafka.creditcard.infra.adapter.out.persistance.CreditCardBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardBalanceRepository extends JpaRepository<CreditCardBalanceEntity, Long> {

    CreditCardBalanceEntity findByCustomerId(int customerId);

}
