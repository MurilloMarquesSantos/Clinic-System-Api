package system.api.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.service.UserService;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register-user")
    public ResponseEntity<NewUserResponse> register(@RequestBody NewUserRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/register-doctor")
    public ResponseEntity<NewDoctorResponse> registerDoc(@RequestBody NewDoctorRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewDoctor(request), HttpStatus.CREATED);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<NewAdminResponse> registerAdmin(@RequestBody NewAdminRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewAdmin(request), HttpStatus.CREATED);
    }

}
