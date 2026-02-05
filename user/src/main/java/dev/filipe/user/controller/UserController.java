package dev.filipe.user.controller;

import dev.filipe.user.domain.UserModel;
import dev.filipe.user.dto.UserDto;
import dev.filipe.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Users", description = "Operations related to users")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user", description = "Creates a user and publishes the event for sending email")
    @PostMapping("/users")
    public ResponseEntity<UserModel> createUser(@RequestBody UserDto dto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveAndPublish(userModel));
    }

    @Operation(summary = "List all users", description = "Returns a list of all registered users.")
    @GetMapping("users/list")
    public ResponseEntity<List<UserModel>> listUsers() {
        List<UserModel> user = userService.findAll();
        return ResponseEntity.ok(user);
    }
}
