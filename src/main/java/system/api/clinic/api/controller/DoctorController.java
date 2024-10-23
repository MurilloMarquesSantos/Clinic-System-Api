package system.api.clinic.api.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.api.clinic.api.reponses.FindDoctorsResponse;
import system.api.clinic.api.reponses.ScheduleResponse;
import system.api.clinic.api.service.DoctorsService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
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


}
