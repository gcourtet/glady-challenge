package com.gcourtet.glady.challenge.domain.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Company {
    private long id;
    private String name;
    private double balance;
    private List<User> employees;
}
