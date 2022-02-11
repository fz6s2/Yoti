package com.yoti.test.validator;

import com.yoti.test.exception.ValidateException;
import com.yoti.test.model.CleaningRequest;

/**
 * The {@code CleaningValidator} class represents a vital control device
 * to check inputs for a robotic hoover clean-up system.
 *
 * @see CleaningRequest
 */

public interface CleaningValidator {

    void validate(CleaningRequest request) throws ValidateException;
}
