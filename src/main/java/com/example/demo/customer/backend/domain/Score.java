package com.example.demo.customer.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {
    public String name;
    public int value;
    public int total;

    public Score() {}
}
