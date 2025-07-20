package com.loliana.sipc.controller;

import com.loliana.sipc.model.Doctor;
import com.loliana.sipc.repository.DoctorRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    private final DoctorRepository doctorRepository;

    public DoctorController(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @GetMapping
    public List<Doctor> getAllUsers() {
        return doctorRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getUserById(@PathVariable Integer id) {
        return doctorRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        convertToUppercase(doctor);
        Doctor saved = doctorRepository.save(doctor);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable Integer id, @RequestBody Doctor doctor) {
        Optional<Doctor> optional = doctorRepository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Doctor existing = optional.get();
        existing.setName(doctor.getName());
        existing.setTypeCouncil(doctor.getTypeCouncil());
        existing.setIdCouncil(doctor.getIdCouncil());
        convertToUppercase(existing);
        return ResponseEntity.ok(doctorRepository.save(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        if (!doctorRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        doctorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void convertToUppercase(Doctor doctor) {
        if (doctor.getName() != null) {
            doctor.setName(doctor.getName().toUpperCase());
        }
        if (doctor.getTypeCouncil() != null) {
            doctor.setTypeCouncil(doctor.getTypeCouncil().toUpperCase());
        }
        if (doctor.getIdCouncil() != null) {
            doctor.setIdCouncil(doctor.getIdCouncil().toUpperCase());
        }
    }

}
