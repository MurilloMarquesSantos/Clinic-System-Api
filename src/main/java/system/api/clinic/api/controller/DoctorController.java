package system.api.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.service.DoctorsService;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorsService doctorsService;

    @GetMapping("/doctors")
    public ResponseEntity<Page<FindDoctorsResponse>> listAll(Pageable pageable) {
        return new ResponseEntity<>(doctorsService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/doctors/{name}")
    public ResponseEntity<Page<ScheduleResponse>> schedule(
            @PathVariable String name, Pageable pageable) throws BadRequestException {
        return new ResponseEntity<>(doctorsService.listSchedules(name, pageable), HttpStatus.OK);
    }

    @GetMapping(value = "/doctors", params = "specialty")
    public ResponseEntity<Page<FindDoctorsResponse>> listBySpecialty(
            @RequestParam(name = "specialty") String specialty, Pageable pageable) {
        return new ResponseEntity<>(doctorsService.listBySpecialty(specialty, pageable), HttpStatus.OK);
    }


}
