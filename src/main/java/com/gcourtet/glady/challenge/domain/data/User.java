package com.gcourtet.glady.challenge.domain.data;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private UUID companyId;
    private String name;
    private List<Deposit> deposits;
}
