package ru.kobaevgenii.testtask.offsidegaming.exception;

import lombok.Getter;

public class UserNotFoundException extends RuntimeException {

    @Getter
    private final String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }
}
