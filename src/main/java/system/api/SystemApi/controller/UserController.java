package system.api.SystemApi.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import system.api.SystemApi.reponses.NewUserResponse;
import system.api.SystemApi.requests.NewUserRequest;
import system.api.SystemApi.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<NewUserResponse> register(@RequestBody NewUserRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewUser(request), HttpStatus.CREATED);
    }
}
