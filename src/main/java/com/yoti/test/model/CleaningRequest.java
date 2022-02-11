package com.yoti.test.model;

import lombok.*;

/**
 * The {@code CleaningRequest} class represents a model, instant fresh result of a clean-up.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CleaningRequest {

    private int [] roomSize;
    private int [] coords;
    private int [][] patches;
    private String instructions;
}