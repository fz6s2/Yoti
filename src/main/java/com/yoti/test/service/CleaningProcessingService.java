package com.yoti.test.service;

import com.yoti.test.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * The {@code CleaningProcessingService} class represents a service to control robotic hoover work.
 * It processes a clean-up request, acting like a robotic hoover main processor,
 * creates a room map, indicates dirt coordinates on it and leads hoover to its goal.
 *
 * @see CleaningCondition
 * @see CleaningResult
 */

@Service
@RequiredArgsConstructor
public class CleaningProcessingService {

    public CleaningResult process(CleaningCondition cleaningCondition) {
        List<PairXY> currentPatches = cleaningCondition.getPatches().stream()
            .distinct()
            .collect(Collectors.toList());
        PairXY currentPos = new PairXY(cleaningCondition.getStartPos().getX(), cleaningCondition.getStartPos().getY());
        PairXY roomSize = new PairXY(cleaningCondition.getRoomSize().getX(), cleaningCondition.getRoomSize().getY());

        int patchCount = 0;

        for(Direction direction: cleaningCondition.getRoute()) {
            if (isPatchOnPosition(currentPatches, currentPos)) {
                cleanPatch(currentPatches, currentPos);
                patchCount++;
            }
            currentPos = getNextPosition(currentPos, direction, roomSize);
        }

        if (isPatchOnPosition(currentPatches, currentPos)) {
            cleanPatch(currentPatches, currentPos);
            patchCount++;
        }

        return new CleaningResult(currentPos, patchCount);
    }

    private boolean isPatchOnPosition(List<PairXY> pitches, PairXY pos) {
        return pitches.contains(pos);
    }

    private void cleanPatch(List<PairXY> currentPatches, PairXY pos) {
        currentPatches.remove(pos);
    }

    private PairXY getNextPosition(PairXY pos, Direction direction, PairXY roomSize) {
        int x = pos.getX();
        int y = pos.getY();

        switch (direction) {
            case EAST: x = min(x + 1, roomSize.getX() - 1); break;
            case WEST: x = max(x - 1, 0); break;
            case NORTH: y = min(y + 1, roomSize.getY() - 1); break;
            case SOUTH: y = max(y - 1, 0); break;
        }

        return new PairXY(x, y);
    }
}
