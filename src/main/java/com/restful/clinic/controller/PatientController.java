package com.restful.clinic.controller;

import com.restful.clinic.model.PatientResponse;
import com.restful.clinic.model.CreatePatientRequest;
import com.restful.clinic.model.WebResponse;
import com.restful.clinic.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping(
            path = "/api/patients",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> create(@RequestBody CreatePatientRequest request) {
        patientService.create(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(
            path = "/api/patients",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<PatientResponse>> get_all() {
        List<PatientResponse> response = patientService.getAll();
        return WebResponse.<List<PatientResponse>>builder().data(response).build();
    }

    @GetMapping(
            path = "/api/patients/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<PatientResponse> get(@PathVariable("username") String username) {
        PatientResponse response = patientService.get(username);
        return WebResponse.<PatientResponse>builder().data(response).build();
    }

    @DeleteMapping(
            path = "/api/patients/{username}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("username") String username) {
        patientService.delete(username);
        return WebResponse.<String>builder().data("OK").build();
    }
}
