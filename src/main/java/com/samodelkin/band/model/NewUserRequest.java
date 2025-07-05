package com.samodelkin.band.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NewUserRequest {
    private String firstName;
    private String lastName;
    private int age;
}
