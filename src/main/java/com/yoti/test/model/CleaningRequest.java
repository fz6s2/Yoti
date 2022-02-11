package com.yoti.test.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CleaningRequest {
    private int [] roomSize;
    private int [] coords;
    private int [][] patches;
    private String instructions;
}