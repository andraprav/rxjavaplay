package com.cegeka.rxjavaplay.annotation;


import com.cegeka.rxjavaplay.annotation.dao.Employee;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeService
{
    void create(Employee e);

    Mono<Employee> findById(Integer id);

    Flux<Employee> findByName(String name);

    Flux<Employee> findAll();

    Mono<Employee> update(Employee e);

    Mono<Void> delete(Integer id);
}
