package com.example.kafka.creditcard.application;

import com.example.kafka.creditcard.infra.adapter.out.persistance.CreditCardBalanceEntity;
import com.example.kafka.creditcard.infra.adapter.out.persistance.IMapper;
import com.example.kafka.creditcard.infra.port.out.CreditCardBalanceRepository;
import com.kafka.example.events.domain.CreditCardBalance;
import com.kafka.example.events.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BalanceServiceTest {

    @Autowired
    private IMapper<CreditCardBalance, CreditCardBalanceEntity> mapper;
    @Mock
    private CreditCardBalanceRepository creditCardBalanceRepository;
    private BalanceService balanceService;

    @BeforeEach
    void setUp() {
        balanceService = new BalanceService(creditCardBalanceRepository, mapper);
    }

    @Test
    void getBalances() {
        Mockito.when(creditCardBalanceRepository.findAll()).thenReturn(mockCreditCardBalanceEntities());
        List<CreditCardBalance> result = balanceService.getBalances();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(2, result.size());
    }

    @Test
    void getBalance() {
        Mockito.when(creditCardBalanceRepository.findByCustomerId(1)).thenReturn(mockCreditCardBalanceEntities().get(0));
        CreditCardBalance result = balanceService.getBalance(1);

        assertNotNull(result);
        assertEquals(100.0, result.getAmount());
    }

    @Test
    void updateBalance() {
        CreditCardBalanceEntity updatedBalance = mockCreditCardBalanceEntities().get(0);

        Order order = new Order();
        order.setCustomerId(1);
        order.setPrice(50.0);
        order.setOrderId(1);
        order.setProductId(1);

        Mockito.when(creditCardBalanceRepository.findByCustomerId(1)).thenReturn(updatedBalance);

        Mockito.when(creditCardBalanceRepository.save(Mockito.any(CreditCardBalanceEntity.class))).thenReturn(updatedBalance);

        balanceService.updateBalance(order);

        Mockito.verify(creditCardBalanceRepository).save(Mockito.any(CreditCardBalanceEntity.class));
    }

    private List<CreditCardBalanceEntity> mockCreditCardBalanceEntities() {
        List<CreditCardBalanceEntity> creditCardBalanceEntities = new ArrayList<>();
        creditCardBalanceEntities.add(new CreditCardBalanceEntity(){{
            setCustomerId(1);
            setAmount(100.0);
            setId(1L);
        }});
        creditCardBalanceEntities.add(new CreditCardBalanceEntity(){{
            setCustomerId(2);
            setAmount(200.0);
            setId(2L);
        }});

        return creditCardBalanceEntities;
    }
}