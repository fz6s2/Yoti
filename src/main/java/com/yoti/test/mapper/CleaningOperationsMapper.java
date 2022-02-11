package com.yoti.test.mapper;

import com.yoti.test.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;

@Component
public class CleaningOperationsMapper {

    public CleaningCondition toDto(CleaningRequest cleaningRequest) {
        if (cleaningRequest == null) {
            return null;
        }

        PairXY roomSize = new PairXY(cleaningRequest.getRoomSize()[0], cleaningRequest.getRoomSize()[1]);
        PairXY startPos = new PairXY(cleaningRequest.getCoords()[0], cleaningRequest.getCoords()[1]);

        List<PairXY> patches = Arrays.stream(cleaningRequest.getPatches())
            .map(pos -> new PairXY(pos[0], pos[1]))
            .collect(Collectors.toList());

        List<Direction> directions = Arrays.stream(cleaningRequest.getInstructions().split(""))
            .map(Direction::of)
            .collect(Collectors.toList());

        return new CleaningCondition()
            .setRoomSize(roomSize)
            .setStartPos(startPos)
            .setPatches(patches)
            .setRoute(directions);
    }

    public CleaningResponse toResponse(CleaningResult cleaningResult) {
        if (cleaningResult == null) {
            return null;
        }

        int [] coords = {cleaningResult.getCurrentPos().getX(), cleaningResult.getCurrentPos().getY()};

        return new CleaningResponse(coords, cleaningResult.getPatchCount());
    }
}
