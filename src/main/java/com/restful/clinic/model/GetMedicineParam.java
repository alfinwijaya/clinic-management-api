package com.restful.clinic.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMedicineParam {
    private String page;

    private String size;

    private Date fromDate;

    private Date toDate;

    private String farmalkesType;

    private String keyword;

    private String templateCode;

    private String packagingCode;

}
