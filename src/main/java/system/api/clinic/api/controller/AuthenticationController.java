package system.api.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.requests.LoginRequest;
import system.api.clinic.api.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) throws BadRequestException {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

}
