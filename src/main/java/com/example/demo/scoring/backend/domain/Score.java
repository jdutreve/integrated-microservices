package com.example.demo.scoring.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Score {
    public String name;
    public String lastname;
    public int value;
}
