package com.loliana.sipc.controller;

import com.loliana.sipc.model.TypeProcedure;
import com.loliana.sipc.repository.TypeProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/type-procedures")
@RequiredArgsConstructor
public class TypeProcedureController {

    private final TypeProcedureRepository repository;

    // Criar
    @PostMapping
    public ResponseEntity<TypeProcedure> create(@RequestBody TypeProcedure procedure) {
        procedure.setName(procedure.getName().toUpperCase());
        return ResponseEntity.ok(repository.save(procedure));
    }

    // Atualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<TypeProcedure> update(@PathVariable Integer id, @RequestBody TypeProcedure updated) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setName(updated.getName().toUpperCase());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Atualizar parcialmente (PATCH)
    @PatchMapping("/{id}")
    public ResponseEntity<TypeProcedure> patch(@PathVariable Integer id, @RequestBody TypeProcedure patch) {
        return repository.findById(id)
                .map(existing -> {
                    if (patch.getName() != null) existing.setName(patch.getName().toUpperCase());
                    return ResponseEntity.ok(repository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // ✅ Listar todos
    @GetMapping
    public ResponseEntity<List<TypeProcedure>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    // ✅ Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<TypeProcedure> getById(@PathVariable Integer id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Excluir
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}