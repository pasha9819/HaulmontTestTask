package ru.pasha.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import ru.pasha.entity.Doctor;

import java.util.List;

@Component
public interface DoctorRepo extends JpaRepository<Doctor, Long > {
    @Override
    List<Doctor> findAll();
}
