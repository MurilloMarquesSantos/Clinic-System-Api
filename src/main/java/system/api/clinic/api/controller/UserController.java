package system.api.clinic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User endpoints", description = "Every endpoint for users")
public class UserController {

    private final UserService userService;
    private final HistoryService historyService;

    @GetMapping("/list/users")
    @Operation(
            summary = "List all users",
            description = "Returns Page of User when Successful." +
                    "\nADMIN ONLY"

    )
    @ApiResponse(
            responseCode = "200",
            description = "When successful"
    )
    public ResponseEntity<Page<User>> listAll(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(userService.list(pageable));
    }

    @PostMapping("/register/user")
    @Operation(
            summary = "Register a new user",
            description = "Return the user created and his info" +
                    " | ADMIN ONLY"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When user info does not match requirements",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "The password must have at least 8 and maximum of 12 characters.")
                    )
            )
    })
    public ResponseEntity<NewUserResponse> register(@RequestBody NewUserRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewUser(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/doctor")
    @Operation(
            summary = "Register a new doctor",
            description = "Return the doctor created and his info" +
                    "| ADMIN ONLY"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When doctor info does not match requirements",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "The password must have at least 8 and maximum of 12 characters.")
                    )
            )
    })
    public ResponseEntity<NewDoctorResponse> registerDoc(@RequestBody NewDoctorRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewDoctor(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    @Operation(
            summary = "Register a new admin",
            description = "Return the admin created and his info" +
                    "\n ADMIN ONLY"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When admin info does not match requirements",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "The password must have at least 8 and maximum of 12 characters.")
                    )
            )
    })
    public ResponseEntity<NewAdminResponse> registerAdmin(@RequestBody NewAdminRequest request) throws BadRequestException {
        return new ResponseEntity<>(userService.createNewAdmin(request), HttpStatus.CREATED);
    }

    @GetMapping("/user/history")
    @Operation(
            summary = "List user history",
            description = "Returns User History when successful."


    )
    @ApiResponse(
            responseCode = "200",
            description = "When successful"
    )
    public ResponseEntity<Page<ScheduleHistoryResponse>> userHistory(Principal principal, Pageable pageable) {
        return new ResponseEntity<>(historyService.list(principal, pageable), HttpStatus.OK);
    }

    @DeleteMapping("/user/history/{id}")
    @Operation(
            summary = "Delete an appointment",
            description = "Delete an appointment that the user have made and send an email with confirmation"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When schedule Id is not in User history",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "This schedule Id does not exists in your schedule history")
                    )
            )
    })
    public ResponseEntity<Void> deleteSchedule(@PathVariable long id, Principal principal) throws BadRequestException {
        historyService.deleteSchedule(id, principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("list/users/{id}")
    @Operation(
            summary = "Delete an user",
            description = "Delete an user" +
                    " | ADMIN ONLY"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When user is not found by Id is not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "No user found with this ID")
                    )
            )
    })
    public ResponseEntity<Void> deleteUser(@PathVariable long id) throws BadRequestException {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/user/reset-password")
    @Operation(
            summary = "Endpoint to reset password",
            description = "Send an email with a link to reset the user password"
    )

    @ApiResponse(
            responseCode = "201",
            description = "When successful"
    )
    public ResponseEntity<Void> resetPasswordRequest(Principal principal) {
        userService.requestPasswordReset(principal);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("user/password/reset")
    @Operation(
            summary = "Change user password",
            description = "To use this endpoint, you must do a request that will send the link to your email," +
                    " the link will expire in 5 minutes"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When password does not match requirements",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value =
                                    "The password must have at least 8 and maximum of 12 characters.")
                    )
            )
    })
    public ResponseEntity<Void> resetPassword(@RequestParam String token, @RequestBody ChangePasswordRequest request,
                                              Principal principal) throws BadRequestException {
        userService.updatePassword(principal, request, token);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
