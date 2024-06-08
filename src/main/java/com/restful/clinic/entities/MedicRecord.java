package com.restful.clinic.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "medic_records")
public class MedicRecord {
    @Id
    private String id;

    private String diagnosis;

    @Column(name = "medical_prescription")
    private String medicalPrescription;

    @Column(name = "treatment_suggestion")
    private String treatmentSuggestion;

    @ManyToOne
    @JoinColumn(name = "username", referencedColumnName = "username")
    private Patient patient;
}
