package com.restful.clinic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.clinic.entities.MedicRecord;
import com.restful.clinic.entities.Patient;
import com.restful.clinic.model.PatientResponse;
import com.restful.clinic.model.CreatePatientRequest;
import com.restful.clinic.model.WebResponse;
import com.restful.clinic.repository.MedicRecordRepository;
import com.restful.clinic.repository.PatientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MedicRecordRepository medicRecordRepository;

    @BeforeEach
    void setUp() {
        medicRecordRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @AfterEach
    void cleanUp() {
        medicRecordRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void testCreateSuccess() throws Exception{
        CreatePatientRequest request = new CreatePatientRequest();
        request.setUsername("alfin");
        request.setName("alfin");
        request.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        request.setEmail("alfin@email.com");

        mockMvc.perform(
                post("/api/patients")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("OK", response.getData());
        });
    }

    @Test
    void testCreateBadRequest() throws Exception{
        CreatePatientRequest request = new CreatePatientRequest();
        request.setUsername("");
        request.setName("");
        request.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        request.setEmail("alfin@email.com");

        mockMvc.perform(
                post("/api/patients")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testCreateDuplicate() throws Exception{
        Patient patient = new Patient();
        patient.setUsername("alfin");
        patient.setName("alfin");
        patient.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        patientRepository.save(patient);

        CreatePatientRequest request = new CreatePatientRequest();
        request.setUsername("alfin");
        request.setName("alfin");
        request.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        request.setPhoneNumber("081111222233");

        mockMvc.perform(
                post("/api/patients")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request))
        ).andExpectAll(
                status().isBadRequest()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testGetAllSuccess() throws Exception{
        Patient patient = new Patient();
        patient.setUsername("alfin");
        patient.setName("alfin");
        patient.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        patientRepository.save(patient);

        mockMvc.perform(
                get("/api/patients")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<PatientResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(response.getData());
        });
    }

    @Test
    void testGetSuccess() throws Exception {
        Patient patient = new Patient();
        patient.setUsername("alfin");
        patient.setName("alfin");
        patient.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        patientRepository.save(patient);

        mockMvc.perform(
                get("/api/patients/alfin")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<PatientResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(response.getData());
        });
    }

    @Test
    void testGetNotFound() throws Exception {
        mockMvc.perform(
                get("/api/patients/nonexistent")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
            status().isNotFound()
        ).andDo(result -> {
            WebResponse<PatientResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testDeleteNotFound() throws Exception {
        mockMvc.perform(
                delete("/api/patients/nonexistent")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<PatientResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testDeleteConflict() throws Exception {
        Patient patient = new Patient();
        patient.setUsername("alfin");
        patient.setName("alfin");
        patient.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        patientRepository.save(patient);

        MedicRecord medic = new MedicRecord();
        medic.setId(UUID.randomUUID().toString());
        medic.setPatient(patient);
        medic.setDiagnosis("tes");
        medic.setTreatmentSuggestion("tes");
        medic.setMedicalPrescription("tes");
        medicRecordRepository.save(medic);

        mockMvc.perform(
                delete("/api/patients/alfin")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isConflict()
        ).andDo(result -> {
            WebResponse<PatientResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void testDeleteSuccess() throws Exception {
        Patient patient = new Patient();
        patient.setUsername("alfin");
        patient.setName("alfin");
        patient.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        patientRepository.save(patient);

        mockMvc.perform(
                delete("/api/patients/alfin")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<String> response =  objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertEquals("OK", response.getData());
        });
    }
}