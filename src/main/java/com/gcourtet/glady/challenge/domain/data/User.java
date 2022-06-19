package com.gcourtet.glady.challenge.domain.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class User {
    private long id;
    private long companyId;
    private String name;
    private List<Deposit> deposits;
}
