package ru.pasha.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.pasha.entity.Patient;

import java.util.List;

@Component
public interface PatientsRepo extends JpaRepository<Patient, Long> {
    @Override
    List<Patient> findAll();
}
