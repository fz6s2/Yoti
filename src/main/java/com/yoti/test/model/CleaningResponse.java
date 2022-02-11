package com.yoti.test.model;

import lombok.Value;

/**
 * The {@code CleaningResponse} class represents a model, result of a clean-up, adopted as the system output.
 *
 */

@Value
public class CleaningResponse {

    int [] coords;
    int patches;
}
