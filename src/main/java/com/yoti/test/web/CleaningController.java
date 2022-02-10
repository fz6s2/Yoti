package com.yoti.test.web;

import com.yoti.test.model.CommonResponseCleaning;
import com.yoti.test.model.RequestCleaning;
import com.yoti.test.model.ResponseCleaning;
import com.yoti.test.service.CleaningDataAccessService;
import com.yoti.test.service.CleaningOperationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/v1/cleanings")
@RequiredArgsConstructor
public class CleaningController {

    private final CleaningOperationService cleaningService;
    private final CleaningDataAccessService cleaningDataAccessService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseCleaning startCleaning(@RequestBody RequestCleaning request) {
        return cleaningService.startCleaning(request);
    }

    @GetMapping
    public Page<CommonResponseCleaning> startCleaning(@PageableDefault(page = 0) Pageable pageable) {
        return cleaningDataAccessService.getCleanings(pageable);
    }
}
