package com.example.kafka.creditcard.infra.adapter.out.persistance;

import com.kafka.example.events.domain.CreditCardBalance;
import org.springframework.stereotype.Component;

@Component
public class CreditCardMapperImpl implements IMapper<CreditCardBalance, CreditCardBalanceEntity> {

    @Override
    public CreditCardBalance entityToDomain(CreditCardBalanceEntity creditCardBalanceEntity) {
        CreditCardBalance balance = new CreditCardBalance();

        balance.setId(creditCardBalanceEntity.getId());
        balance.setAmount(creditCardBalanceEntity.getAmount());
        balance.setCustomerId(creditCardBalanceEntity.getCustomerId());

        return balance;
    }

    @Override
    public CreditCardBalanceEntity domainToEntity(CreditCardBalance creditCardBalance) {
        CreditCardBalanceEntity balance = new CreditCardBalanceEntity();

        balance.setId(creditCardBalance.getId());
        balance.setAmount(creditCardBalance.getAmount());
        balance.setCustomerId(creditCardBalance.getCustomerId());

        return balance;
    }
}
