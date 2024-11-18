package system.api.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.api.clinic.api.domain.User;
import system.api.clinic.api.reponses.NewAdminResponse;
import system.api.clinic.api.reponses.NewDoctorResponse;
import system.api.clinic.api.reponses.NewUserResponse;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.requests.ChangePasswordRequest;
import system.api.clinic.api.requests.NewAdminRequest;
import system.api.clinic.api.requests.NewDoctorRequest;
import system.api.clinic.api.requests.NewUserRequest;
import system.api.clinic.api.service.HistoryService;
import system.api.clinic.api.service.UserService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class UserController {

    private final UserService userService;
    private final HistoryService historyService;

    @GetMapping("/list/users")
    public ResponseEntity<Page<User>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.list(pageable));
    }

    @PostMapping("/register/user")
    public ResponseEntity<NewUserResponse> register(@RequestBody NewUserRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<NewDoctorResponse> registerDoc(@RequestBody NewDoctorRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewDoctor(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<NewAdminResponse> registerAdmin(@RequestBody NewAdminRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewAdmin(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/history")
    public ResponseEntity<Page<ScheduleHistoryResponse>> userHistory(Principal principal, Pageable pageable) {
        return new ResponseEntity<>(historyService.list(principal, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/user/history/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable long id, Principal principal) throws BadRequestException {
        historyService.deleteSchedule(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("list/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws BadRequestException {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/reset-password")
    public ResponseEntity<Void> resetPasswordRequest(Principal principal) {
        userService.requestPasswordReset(principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("user/password/reset")
    public ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestBody ChangePasswordRequest request,
                                              Principal principal) throws BadRequestException {
        userService.updatePassword(principal, request, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
