package com.yoti.test.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CleaningCondition {
    PairXY roomSize;
    PairXY startPos;
    List<PairXY> patches;
    List<Direction> route;
}
