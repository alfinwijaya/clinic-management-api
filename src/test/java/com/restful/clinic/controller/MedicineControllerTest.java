package com.restful.clinic.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restful.clinic.model.WebResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MedicineControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getMedicineSuccess() throws Exception{
        mockMvc.perform(
                get("/api/medicines")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getData());
        });
    }

    @Test
    void getMedicineWithParamsSuccess() throws Exception{
        String page = "1";
        String size = "10";
        String fromDate = "2024-01-01";
        String toDate = "2024-12-31";
        String farmalkesType = "type1";
        String keyword = "keyword1";
        String templateCode = "template1";
        String packagingCode = "packaging1";

        mockMvc.perform(
                get("/api/medicines")
                        .param("page", page)
                        .param("size", size)
                        .param("from_date", fromDate)
                        .param("to_date", toDate)
                        .param("farmalkes_type", farmalkesType)
                        .param("keyword", keyword)
                        .param("template_code", templateCode)
                        .param("packaging_code", packagingCode)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpectAll(
                status().isOk()
        ).andDo(result -> {
            WebResponse<Object> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            assertNotNull(response.getData());
        });
    }
}