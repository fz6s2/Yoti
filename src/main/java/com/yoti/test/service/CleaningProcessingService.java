package com.yoti.test.service;

import com.yoti.test.exception.RoomIndexOfBoundException;
import com.yoti.test.exception.RouteException;
import com.yoti.test.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

@Service
@RequiredArgsConstructor
public class CleaningProcessingService {
    public CleaningResult process(CleaningCondition cleaningCondition) throws RouteException {
        List<PairXY> currentPatches = new ArrayList<>(cleaningCondition.getPatches());
        PairXY currentPos = new PairXY(cleaningCondition.getStartPos().getX(), cleaningCondition.getStartPos().getY());
        PairXY roomSize = new PairXY(cleaningCondition.getRoomSize().getX(), cleaningCondition.getRoomSize().getY());

        int patchCount = 0;

        for(Direction direction: cleaningCondition.getRoute()) {
            validatePosition(currentPos, roomSize);
            if (isPitchOnPosition(currentPatches, currentPos)) {
                cleanPitch(currentPatches, currentPos);
                patchCount++;
            }
            currentPos = getNextPosition(currentPos, direction, roomSize);
        }

        return new CleaningResult(currentPos, patchCount);
    }

    private void validatePosition(PairXY pos, PairXY roomSize) throws RouteException {
        if(pos.getX() < 0 || pos.getX() > roomSize.getX()) {
            throw new RoomIndexOfBoundException("Hoover route is outside the room, position: " + pos);
        }

        if(pos.getY() < 0 || pos.getY() > roomSize.getY()) {
            throw new RoomIndexOfBoundException("Hoover route is outside the room, position: " + pos);
        }
    }

    private boolean isPitchOnPosition(List<PairXY> pitches, PairXY pos) {
        return pitches.contains(pos);
    }

    private void cleanPitch(List<PairXY> currentPatches, PairXY pos) {
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
