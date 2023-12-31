package com.example.kafka.creditcard.application;

import com.example.kafka.creditcard.infra.adapter.out.persistance.CreditCardBalanceEntity;
import com.example.kafka.creditcard.infra.adapter.out.persistance.IMapper;
import com.example.kafka.creditcard.infra.port.in.BalancePort;
import com.example.kafka.creditcard.infra.port.in.MessagingEvent;
import com.example.kafka.creditcard.infra.port.out.CreditCardBalanceRepository;
import com.kafka.example.events.domain.CreditCardBalance;
import com.kafka.example.events.domain.Event;
import com.kafka.example.events.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BalanceService implements BalancePort {

    private CreditCardBalanceRepository creditCardBalanceRepository;
    private IMapper<CreditCardBalance, CreditCardBalanceEntity> mapper;

    @Autowired
    public BalanceService(CreditCardBalanceRepository creditCardBalanceRepository,
                          IMapper<CreditCardBalance, CreditCardBalanceEntity> mapper) {
        this.creditCardBalanceRepository = creditCardBalanceRepository;
        this.mapper = mapper;
    }


    @Override
    public List<CreditCardBalance> getBalances() {
        List<CreditCardBalance> balances = new ArrayList<>();

        creditCardBalanceRepository.findAll().forEach(balanceEntity -> {
            balances.add(mapper.entityToDomain(balanceEntity));
        });

        return balances;
    }

    @Override
    public CreditCardBalance getBalance(int customerId) {
        return mapper.entityToDomain(creditCardBalanceRepository.findByCustomerId(customerId));
    }

    @Override
    public void updateBalance(Order order) {
        CreditCardBalance currentCustomerBalance = this.getBalance(order.getCustomerId());

        currentCustomerBalance.updateAmount(order.getPrice());
        creditCardBalanceRepository.save(mapper.domainToEntity(currentCustomerBalance));
    }
}
