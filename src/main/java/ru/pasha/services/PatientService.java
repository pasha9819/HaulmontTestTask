package ru.pasha.services;

import org.springframework.stereotype.Service;
import ru.pasha.entity.Patient;
import ru.pasha.repos.PatientsRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class PatientService {
    private final PatientsRepo patientsRepo;

    public PatientService(PatientsRepo patientsRepo) {
        this.patientsRepo = patientsRepo;
    }

    public Patient findById(long id) {
        Optional<Patient> p = patientsRepo.findById(id);
        return p.orElse(null);
    }

    public List<Patient> findAll() {
        return patientsRepo.findAll();
    }

    public Stream<Patient> fetch(String filter, int offset, int limit) {
        return findAll()
                .stream()
                .filter(patient -> filter == null || patient.fullName().toLowerCase().contains(filter.toLowerCase()))
                .skip(offset)
                .limit(limit);
    }

    public int count(String filter) {
        return (int) findAll()
                .stream()
                .filter(patient -> filter == null || patient.fullName().toLowerCase().contains(filter.toLowerCase()))
                .count();
    }

    public void save(Patient p) {
        patientsRepo.save(p);
    }

    public void delete(Patient p) {
        if (!p.getRecipes().isEmpty()){
            throw new IllegalStateException();
        }
        patientsRepo.delete(p);
    }
}
