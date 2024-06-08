package com.restful.clinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateMedicRecordRequest {
    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 255)
    private String diagnosis;

    @NotBlank
    @Size(max = 255)
    private String medicalPrescription;

    @NotBlank
    @Size(max = 255)
    private String treatmentSuggestion;
}
