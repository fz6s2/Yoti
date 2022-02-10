package com.yoti.test.model;

import lombok.Value;

@Value
public class CleaningResult {
    PairXY currentPos;
    int patchCount;
}
