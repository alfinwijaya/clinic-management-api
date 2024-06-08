package com.restful.clinic.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.clinic.model.GetMedicineParam;
import com.restful.clinic.model.MedicRecordResponse;
import com.restful.clinic.model.WebResponse;
import com.restful.clinic.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.List;

@RestController
public class MedicineController {;
    @Autowired
    private MedicineService medicineService;

    @GetMapping(
            path = "/api/medicines",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public WebResponse<Object> get_all(@ModelAttribute GetMedicineParam param) throws Exception {
        return medicineService.getAll(param);
    }
}
