package com.yoti.test.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestCleaning {
    private int [] roomSize;
    private int [] coords;
    private int [][] patches;
    private String instructions;
}