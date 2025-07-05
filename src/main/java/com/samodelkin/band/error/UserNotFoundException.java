package com.samodelkin.band.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(long userId) {
        super(String.format("User [%s] not found", userId));
    }
}
