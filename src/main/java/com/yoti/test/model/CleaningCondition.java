package com.yoti.test.model;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * The {@code CleaningCondition} class represents a vital data to perform a clean-up operation.
 * This data is defined by a user wiling to run the operation.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class CleaningCondition {

    PairXY roomSize;
    PairXY startPos;
    List<PairXY> patches;
    List<Direction> route;
}
