package com.yoti.test.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The {@code PairXY} class represents 2 dimensional coordinates of an object in Robotic Hoover system.
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PairXY {

    private int x;
    private int y;
}
