package com.yoti.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * The {@code CleaningResult} class represents a model, instant fresh result of a clean-up.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CleaningResult {

    private PairXY currentPos;
    private int patchCount;
}
