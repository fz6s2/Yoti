package com.yoti.test.mapper;

import com.yoti.test.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class CleaningOperationsMapper {

    public CleaningCondition toDto(RequestCleaning requestCleaning) {
        if (requestCleaning == null) {
            return null;
        }

        PairXY roomSize = new PairXY(requestCleaning.getRoomSize()[0], requestCleaning.getRoomSize()[1]);
        PairXY startPos = new PairXY(requestCleaning.getCoords()[0], requestCleaning.getCoords()[1]);

        List<PairXY> patches = Arrays.stream(requestCleaning.getPatches())
            .map(pos -> new PairXY(pos[0], pos[1]))
            .distinct()
            .collect(Collectors.toList());

        List<Direction> directions = Arrays.stream(requestCleaning.getInstructions().split(""))
            .map(Direction::of)
            .collect(Collectors.toList());

        return CleaningCondition.builder()
            .roomSize(roomSize)
            .startPos(startPos)
            .patches(patches)
            .route(directions)
            .build();
    }

    public ResponseCleaning toResponse(CleaningResult cleaningResult) {
        if (cleaningResult == null) {
            return null;
        }

        int [] coords = {cleaningResult.getCurrentPos().getX(), cleaningResult.getCurrentPos().getY()};

        return new ResponseCleaning(coords, cleaningResult.getPatchCount());
    }
}
