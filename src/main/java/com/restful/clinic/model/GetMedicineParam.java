package com.restful.clinic.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetMedicineParam {
    private String page;

    private String size;

    private String from_date;

    private String to_date;

    private String farmalkes_type;

    private String keyword;

    private String template_code;

    private String packaging_code;

}
