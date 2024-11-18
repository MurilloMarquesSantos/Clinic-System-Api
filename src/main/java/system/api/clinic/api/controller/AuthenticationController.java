package system.api.clinic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.api.clinic.api.reponses.LoginResponse;
import system.api.clinic.api.requests.LoginRequest;
import system.api.clinic.api.service.AuthenticationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Tag(name = "Authentication", description = "User login")
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/login")
    @Operation(
            summary = "Login method",
            description = "Returns access token for valid user credentials"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "User logged successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{ \"accessToken\": \"eyJhbGciOiJIUzI1...\" }")
                    )
            ),
            @ApiResponse(responseCode = "401",
                    description = "When user credentials are invalid",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "INVALID LOGIN CREDENTIALS")
            ))
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

}
