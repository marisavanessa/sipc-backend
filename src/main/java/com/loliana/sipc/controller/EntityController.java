package com.loliana.sipc.controller;

import com.loliana.sipc.dto.EntityDTO;
import com.loliana.sipc.model.EntityModel;
import com.loliana.sipc.service.EntityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entities")
@RequiredArgsConstructor
public class EntityController {

    private final EntityService service;

    @PostMapping
    public ResponseEntity<EntityModel> create(@RequestBody @Valid EntityDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel> update(@PathVariable Integer id, @RequestBody @Valid EntityDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<EntityModel> list() {
        return service.listAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel> get(@PathVariable Integer id) {
        return ResponseEntity.of(service.findById(id));
    }
}
