package com.restful.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MedicRecordResponse {
    private String id;
    private String username;
    private String name;
    private Date birthDate;
    private String email;
    private String phoneNumber;
    private String diagnosis;
    private String medicalPrescription;
    private String treatmentSuggestion;
}
