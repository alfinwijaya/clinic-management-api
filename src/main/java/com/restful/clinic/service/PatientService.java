package com.restful.clinic.service;

import com.restful.clinic.entities.Patient;
import com.restful.clinic.model.PatientResponse;
import com.restful.clinic.model.CreatePatientRequest;
import com.restful.clinic.repository.MedicRecordRepository;
import com.restful.clinic.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private MedicRecordRepository medicRecordRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void create(CreatePatientRequest request) {
        validationService.validate(request);

        if(patientRepository.existsById(request.getUsername())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        Patient patient = new Patient();
        patient.setUsername(request.getUsername());
        patient.setName(request.getName());
        patient.setBirthDate((request.getBirthDate()));
        patient.setPhoneNumber(request.getPhoneNumber());
        patient.setEmail(request.getEmail());

        patientRepository.save(patient);
    }

    @Transactional(readOnly = true)
    public List<PatientResponse> getAll(){
        List<Patient> patients = patientRepository.findAll();

        List<PatientResponse> response = new ArrayList<PatientResponse>();
        for (Patient patient : patients) {
            PatientResponse obj = new PatientResponse();
            obj.setUsername(patient.getUsername());
            obj.setName(patient.getName());
            obj.setBirthDate(patient.getBirthDate());
            obj.setPhoneNumber(patient.getPhoneNumber());
            obj.setEmail(patient.getEmail());

            response.add(obj);
        }
        return response;
    }

    @Transactional(readOnly = true)
    public PatientResponse get(String username){
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        PatientResponse response = new PatientResponse();
        response.setUsername(patient.getUsername());
        response.setName(patient.getName());
        response.setBirthDate(patient.getBirthDate());
        response.setPhoneNumber(patient.getPhoneNumber());
        response.setEmail(patient.getEmail());

        return response;
    }

    @Transactional
    public void delete(String username) {
        Patient patient = patientRepository.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Patient not found"));

        if (medicRecordRepository.existsByPatientUsername(username)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete patient because used on medic records");
        }

        patientRepository.delete(patient);
    }
}
