package ru.pasha.services;

import org.springframework.stereotype.Service;
import ru.pasha.entity.Doctor;
import ru.pasha.repos.DoctorRepo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DoctorService {

    private final DoctorRepo doctorRepo;

    public DoctorService(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    public Doctor findById(long id){
        Optional<Doctor> d = doctorRepo.findById(id);
        return d.orElse(null);
    }

    public List<Doctor> findAll(){
        return doctorRepo.findAll();
    }

    public Stream<Doctor> fetch(String filter, int offset, int limit){
        return findAll()
                .stream()
                .filter(doctor -> filter == null || doctor.fullName().toLowerCase().contains(filter.toLowerCase()))
                .skip(offset)
                .limit(limit);
    }

    public int count(String filter){
        return (int)findAll()
                .stream()
                .filter(doctor -> filter == null || doctor.fullName().toLowerCase().contains(filter.toLowerCase()))
                .count();
    }

    public void save(Doctor d){
        doctorRepo.save(d);
    }

    public void delete(Doctor d){
        if (!d.getRecipes().isEmpty()){
            throw new IllegalStateException();
        }
        doctorRepo.delete(d);
    }

}
