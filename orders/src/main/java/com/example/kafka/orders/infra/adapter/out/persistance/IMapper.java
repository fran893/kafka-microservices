package com.example.kafka.orders.infra.adapter.out.persistance;

public interface IMapper<T,Z> {

    T entityToDomain(Z z);

    Z domainToEntity(T t);

}
