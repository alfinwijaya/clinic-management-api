package com.restful.clinic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.clinic.entities.MedicRecord;
import com.restful.clinic.entities.Patient;
import com.restful.clinic.model.CreateMedicRecordRequest;
import com.restful.clinic.model.MedicRecordResponse;
import com.restful.clinic.model.PatientResponse;
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

import javax.lang.model.type.ReferenceType;
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
class MedicRecordControllerTest {
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

        Patient patient = new Patient();
        patient.setUsername("alfin");
        patient.setName("alfin");
        patient.setBirthDate(new Date(98, Calendar.AUGUST, 11));
        patientRepository.save(patient);
    }

    @AfterEach
    void cleanUp() {
        medicRecordRepository.deleteAll();
        patientRepository.deleteAll();
    }

    @Test
    void createMedicRecordBadRequest() throws Exception{
         CreateMedicRecordRequest request = new CreateMedicRecordRequest();
         request.setUsername("");
         request.setDiagnosis("tes");;
         request.setTreatmentSuggestion("tes");
         request.setMedicalPrescription("tes");

         mockMvc.perform(
                 post("/api/medic_records")
                         .accept(MediaType.APPLICATION_JSON)
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request))
         ).andExpectAll(
                 status().isBadRequest()
         ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
         });
    }

    @Test
    void createMedicRecordSuccess() throws Exception{
        CreateMedicRecordRequest request = new CreateMedicRecordRequest();
        request.setUsername("alfin");
        request.setDiagnosis("tes");;
        request.setTreatmentSuggestion("tes");
        request.setMedicalPrescription("tes");

        mockMvc.perform(
                post("/api/medic_records")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<MedicRecordResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals("alfin", response.getData().getUsername());
            assertEquals("alfin", response.getData().getName());
            assertEquals("tes", response.getData().getDiagnosis());
            assertEquals("tes", response.getData().getMedicalPrescription());
            assertEquals("tes", response.getData().getTreatmentSuggestion());

            assertTrue(medicRecordRepository.existsById(response.getData().getId()));
        });
    }

    @Test
    void testGetAllSuccess() throws Exception{
        Patient patient = patientRepository.findById("alfin").orElseThrow();

        MedicRecord medic = new MedicRecord();
        medic.setId(UUID.randomUUID().toString());
        medic.setPatient(patient);
        medic.setDiagnosis("tes");
        medic.setTreatmentSuggestion("tes");
        medic.setMedicalPrescription("tes");
        medicRecordRepository.save(medic);

        mockMvc.perform(
                get("/api/medic_records")
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<List<MedicRecordResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            assertNotNull(response.getData());
        });
    }

    @Test
    void getMedicRecordNotFound() throws Exception {
        mockMvc.perform(
                get("/api/medic_records/nonexistent")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isNotFound()
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getErrors());
        });
    }

    @Test
    void getMedicRecordSuccess() throws Exception {
        Patient patient = patientRepository.findById("alfin").orElseThrow();

        MedicRecord medic = new MedicRecord();
        medic.setId(UUID.randomUUID().toString());
        medic.setPatient(patient);
        medic.setDiagnosis("tes");
        medic.setTreatmentSuggestion("tes");
        medic.setMedicalPrescription("tes");
        medicRecordRepository.save(medic);

        mockMvc.perform(
                get("/api/medic_records/" + medic.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<MedicRecordResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNull(response.getErrors());
            assertEquals(medic.getId(), response.getData().getId());
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
    void testDeleteSuccess() throws Exception {
        Patient patient = patientRepository.findById("alfin").orElseThrow();

        MedicRecord medic = new MedicRecord();
        medic.setId(UUID.randomUUID().toString());
        medic.setPatient(patient);
        medic.setDiagnosis("tes");
        medic.setTreatmentSuggestion("tes");
        medic.setMedicalPrescription("tes");
        medicRecordRepository.save(medic);

        mockMvc.perform(
                delete("/api/medic_records/" + medic.getId())
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