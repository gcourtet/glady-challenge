package com.gcourtet.glady.challenge.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    private long id;
    private String name;
    private double balance;
    private List<User> employees;
}
