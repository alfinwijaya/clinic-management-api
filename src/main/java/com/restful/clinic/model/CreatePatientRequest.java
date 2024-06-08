package com.restful.clinic.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePatientRequest {
    @NotBlank
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    private Date birthDate;

    @Email
    @Size(max = 100)
    private String email;

    @Digits(message="Phone number should contain 12 digits", fraction = 0, integer = 12)
    private String phoneNumber;
}
