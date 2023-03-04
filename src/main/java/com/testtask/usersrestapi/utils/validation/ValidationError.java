package com.testtask.usersrestapi.utils.validation;

import java.util.*;
import java.util.stream.Collectors;

public class ValidationError {

    private final Map<String, List<String>> errors = new HashMap<>();
    private String errorMessage;

    void addValidationError(String field, String defaultMessage) {
        List<String> fieldErrors = errors.get(field);
        if (fieldErrors == null) {
            fieldErrors = new ArrayList<>();
        }

        fieldErrors.add(defaultMessage);
        errors.put(field, fieldErrors);
    }

    void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        String string = errorMessage + "\n";
        string += errors.values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.joining("\n"));
        return string;
    }
}
