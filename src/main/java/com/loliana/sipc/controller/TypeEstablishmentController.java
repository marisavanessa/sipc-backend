package com.loliana.sipc.controller;

import ch.qos.logback.classic.Logger;
import com.loliana.sipc.dto.TypeEstablishmentDTO;
import com.loliana.sipc.service.TypeEstablishmentService;
import com.loliana.sipc.model.TypeEstablishment;
import com.loliana.sipc.repository.TypeEstablishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type-establishments")
@RequiredArgsConstructor
public class TypeEstablishmentController {

    private final TypeEstablishmentService service;

    @GetMapping
    public ResponseEntity<List<TypeEstablishmentDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeEstablishmentDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<TypeEstablishmentDTO> create(@RequestBody TypeEstablishmentDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    /*
    @PutMapping("/{id}")
    public ResponseEntity<TypeEstablishment> update(@PathVariable Integer id, @RequestBody TypeEstablishment updated) {
        Optional<TypeEstablishment> optional = repository.findById(id);
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TypeEstablishment entity = optional.get();
        entity.setName(updated.getName());
        return ResponseEntity.ok(repository.save(entity));
    }
    */

    @PutMapping("/{id}")
    public ResponseEntity<TypeEstablishmentDTO> update(@PathVariable Integer id,
                                                       @RequestBody TypeEstablishmentDTO dto) {

        Optional<TypeEstablishmentDTO> optional = Optional.ofNullable(service.findById(id));
        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TypeEstablishmentDTO entity = optional.get();
        entity.setName(dto.getName());

        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
