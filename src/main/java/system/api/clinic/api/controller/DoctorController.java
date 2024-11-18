package system.api.clinic.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class DoctorController {

    private final DoctorsService doctorsService;
    private final ScheduleService scheduleService;


    @GetMapping("/doctors")
    @Operation(summary = "List all doctors and his specialties",
            description = "Returns doctor response")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "When successful"),
            @ApiResponse(responseCode = "400", description = "When could not find doctors")
    })
    public ResponseEntity<Page<FindDoctorsResponse>> listAll(@ParameterObject Pageable pageable) {
        return new ResponseEntity<>(doctorsService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/doctors/{name}")
    public ResponseEntity<Page<ScheduleResponse>> schedule(
            @PathVariable String name, @ParameterObject Pageable pageable) throws BadRequestException {
        return new ResponseEntity<>(doctorsService.listSchedulesPage(name, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/doctors", params = "specialty")
    public ResponseEntity<Page<FindDoctorsResponse>> listBySpecialty(
            @RequestParam(name = "specialty") String specialty, @ParameterObject Pageable pageable) {
        return new ResponseEntity<>(doctorsService.listBySpecialty(specialty, pageable), HttpStatus.OK);
    }

    @GetMapping("/doctors/{name}/{id}")
    public ResponseEntity<ScheduleHistoryResponse> doSchedule(@PathVariable String name, @PathVariable long id,
                                                              Principal principal) throws BadRequestException {
        return new ResponseEntity<>(scheduleService.doSchedule(name, id, principal), HttpStatus.OK);
    }


}
