package com.yoti.test.web;

import com.yoti.test.exception.MapperException;
import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.ApiErrorDetail;
import com.yoti.test.model.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends DefaultHandlerExceptionResolver {
    @ExceptionHandler(MapperException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleRouteException(MapperException ex) {
        return getErrorResponse(ex);
    }

    @ExceptionHandler(ValidateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiErrorResponse handleRouteException(ValidateException ex) {
        return getErrorResponse(ex);
    }

    private ApiErrorResponse getErrorResponse(Throwable ex) {
        logException(ex);
        return new ApiErrorResponse(ex.getMessage());
    }

    private ApiErrorResponse getErrorResponse(ValidateException ex) {
        logException(ex);

        List<ApiErrorDetail> details = null;
        if (ex.getErrors() != null) {
            details = ex.getErrors().entrySet().stream()
                .map(set -> new ApiErrorDetail(set.getKey(), set.getValue()))
                .collect(Collectors.toList());
        }

        return new ApiErrorResponse(ex.getMessage(), details);
    }

    private void logException(Throwable exception) {
        log.error("Exception", exception);
    }
}
