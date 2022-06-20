package com.gcourtet.glady.challenge.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class User {
    private Long id;
    private Long companyId;
    private String name;
    private List<Deposit> deposits;
}
