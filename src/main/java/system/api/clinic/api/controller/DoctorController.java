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
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.reponses.ScheduleHistoryResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.service.DoctorsService;
import system.api.clinic.api.service.ScheduleService;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
@Tag(name = "Doctors endpoints", description = "Every endpoint that need any doctor info")
public class DoctorController {

    private final DoctorsService doctorsService;
    private final ScheduleService scheduleService;


    @GetMapping("/doctors")
    @Operation(
            summary = "List doctors and his specialties",
            description = "This endpoint must redirect to \"/doctors/{name}\", when clicking in the doctor name," +
                    "so the user can see this doctors available schedules"
    )
    @ApiResponse(
            responseCode = "200",
            description = "When successful"
    )
    public ResponseEntity<Page<FindDoctorsResponse>> listAll(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(doctorsService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/doctors/{name}")
    @Operation(
            summary = "List doctor and his schedules",
            description = "Returns page of schedule response"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When doctor is not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "No doctor found with this name")
                    ))
    })
    public ResponseEntity<Page<ScheduleResponse>> schedule(
            @PathVariable String name, @ParameterObject Pageable pageable) throws BadRequestException {
        return new ResponseEntity<>(doctorsService.listSchedulesPage(name, pageable), HttpStatus.OK);
    }


    @GetMapping(value = "/doctors/{specialty}")
    @Operation(
            summary = "List doctors by specialty",
            description = "Returns page of doctor response"
    )
    @ApiResponse(
            responseCode = "200",
            description = "When successful"
    )
    public ResponseEntity<Page<FindDoctorsResponse>> listBySpecialty(
            @PathVariable String specialty, @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(doctorsService.listBySpecialty(specialty, pageable), HttpStatus.OK);
    }

    @GetMapping("/doctors/{name}/{id}")
    @Operation(
            summary = "Do a new appointment",
            description = "Returns and send email with appointment info"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "When successful"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "When schedule Id is invalid",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "This schedule is not in this Doctor's schedule")
                    )
            )
    })
    public ResponseEntity<ScheduleHistoryResponse> doSchedule(@PathVariable String name, @PathVariable long id,
                                                              Principal principal) throws BadRequestException {
        return new ResponseEntity<>(scheduleService.doSchedule(name, id, principal), HttpStatus.OK);
    }


}
