package com.cegeka.rxjavaplay.annotation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping
public class EmployeeResource {

    private EmployeeService employeeService;

    @GetMapping
    public Mono<String> getEmployee() {
//        return employeeService.getEmployee();
        return Mono.empty();
    }

}
