package system.api.SystemApi.controller;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import system.api.SystemApi.reponses.ScheduleResponse;
import system.api.SystemApi.service.DoctorsService;

@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorsService doctorsService;

    @GetMapping("/{name}")
    public ResponseEntity<Page<ScheduleResponse>> schedule(
            @PathVariable String name, Pageable pageable) throws BadRequestException {
        return new ResponseEntity<>(doctorsService.listSchedules(name, pageable), HttpStatus.OK);
    }


}
