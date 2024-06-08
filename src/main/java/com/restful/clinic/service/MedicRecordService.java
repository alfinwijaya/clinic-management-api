package com.restful.clinic.service;

import com.restful.clinic.entities.MedicRecord;
import com.restful.clinic.entities.Patient;
import com.restful.clinic.model.CreateMedicRecordRequest;
import com.restful.clinic.model.MedicRecordResponse;
import com.restful.clinic.repository.MedicRecordRepository;
import com.restful.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MedicRecordService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRecordRepository medicRecordRepository;

    @Autowired
    private ValidationService validationService;

    private MedicRecordResponse toMedicRecordResponse(MedicRecord medicRecord){
        return MedicRecordResponse.builder()
                .id(medicRecord.getId())
                .username(medicRecord.getPatient().getUsername())
                .name(medicRecord.getPatient().getName())
                .birthDate(medicRecord.getPatient().getBirthDate())
                .email(medicRecord.getPatient().getEmail())
                .phoneNumber(medicRecord.getPatient().getPhoneNumber())
                .diagnosis(medicRecord.getDiagnosis())
                .medicalPrescription(medicRecord.getMedicalPrescription())
                .treatmentSuggestion(medicRecord.getTreatmentSuggestion())
                .build();
    }

    @Transactional
    public MedicRecordResponse create(CreateMedicRecordRequest request){
        validationService.validate(request);

        Patient patient = patientRepository.findById(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient not found"));

        MedicRecord medicRecord = new MedicRecord();
        medicRecord.setId(UUID.randomUUID().toString());
        medicRecord.setDiagnosis(request.getDiagnosis());
        medicRecord.setMedicalPrescription(request.getMedicalPrescription());
        medicRecord.setTreatmentSuggestion(request.getTreatmentSuggestion());
        medicRecord.setPatient(patient);

        medicRecordRepository.save(medicRecord);

        return toMedicRecordResponse(medicRecord);
    }

    @Transactional(readOnly = true)
    public List<MedicRecordResponse> getAll(){
        List<MedicRecord> medicRecords = medicRecordRepository.findAll();

        List<MedicRecordResponse> response = new ArrayList<MedicRecordResponse>();
        for (MedicRecord medicRecord : medicRecords) {
            response.add(toMedicRecordResponse(medicRecord));
        }
        return response;
    }

    @Transactional(readOnly = true)
    public MedicRecordResponse get(String id) {
        MedicRecord medicRecord = medicRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medic record not found"));

        return toMedicRecordResponse(medicRecord);
    }

    @Transactional
    public void delete(String id) {
        MedicRecord medicRecord = medicRecordRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Medic record not found"));

        medicRecordRepository.delete(medicRecord);
    }
}
