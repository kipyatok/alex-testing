package com.samodelkin.band.controller;

import com.samodelkin.band.error.UserNotFoundException;
import com.samodelkin.band.model.NewUserRequest;
import com.samodelkin.band.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.instancio.Instancio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Operation(
            summary = "create new user",
            method = "POST")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "" + 200, description = "user created"),
                    @ApiResponse(responseCode = "404", description = "User not found"),
            }
    )
    @PostMapping
    public void create(@RequestBody NewUserRequest request) {
        log.info("Create new user by request: {}", request);
    }

    @Operation(
            summary = "get user by id",
            method = "GET")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "" + 200, description = "User",
                            content = @Content(schema = @Schema(implementation = User.class))),
                    @ApiResponse(responseCode = "404", description = "User not found"),
            }
    )
    @GetMapping("/{userId}")
    public User get(@PathVariable("userId") long userId) {
        if (userId < 0) { // проверка что id должен быть > 0
            throw new UserNotFoundException(userId);
        }

        User user = Instancio.create(User.class); // генерирует случайным образом юзера
        user.setId(userId);

        return user;
    }
}
