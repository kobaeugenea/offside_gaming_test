package ru.kobaevgenii.testtask.offsidegaming.exception;

import lombok.Getter;

public class IllegalRequestParameterValueException extends RuntimeException {

    @Getter
    private final String field;

    public IllegalRequestParameterValueException(String field, String message) {
        super(message);
        this.field = field;
    }
}
