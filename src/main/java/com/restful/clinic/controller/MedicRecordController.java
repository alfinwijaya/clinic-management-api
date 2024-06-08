package com.restful.clinic.controller;

import com.restful.clinic.model.CreateMedicRecordRequest;
import com.restful.clinic.model.MedicRecordResponse;
import com.restful.clinic.model.WebResponse;
import com.restful.clinic.service.MedicRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MedicRecordController {

    @Autowired
    private MedicRecordService medicRecordService;

    @PostMapping(
            path = "/api/medic_records",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MedicRecordResponse> create(@RequestBody CreateMedicRecordRequest request){
        MedicRecordResponse response = medicRecordService.create(request);
        return WebResponse.<MedicRecordResponse>builder().data(response).build();
    }

    @GetMapping(
            path = "/api/medic_records",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<List<MedicRecordResponse>> get_all() {
        List<MedicRecordResponse> response = medicRecordService.getAll();
        return WebResponse.<List<MedicRecordResponse>>builder().data(response).build();
    }

    @GetMapping(
            path = "/api/medic_records/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<MedicRecordResponse> get(@PathVariable("id") String id){
        MedicRecordResponse response = medicRecordService.get(id);
        return WebResponse.<MedicRecordResponse>builder().data(response).build();
    }

    @DeleteMapping(
            path = "/api/medic_records/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<String> delete(@PathVariable("id") String id) {
        medicRecordService.delete(id);
        return WebResponse.<String>builder().data("OK").build();
    }
}
