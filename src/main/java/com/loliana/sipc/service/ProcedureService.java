package com.loliana.sipc.service;

import com.loliana.sipc.dto.ProcedureDTO;
import com.loliana.sipc.exception.DuplicateNameException;
import com.loliana.sipc.exception.TypeProcedureNotFoundException;
import com.loliana.sipc.model.Procedure;
import com.loliana.sipc.repository.ProcedureRepository;
import com.loliana.sipc.repository.TypeProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProcedureService {

    private final ProcedureRepository repository;
    private final TypeProcedureRepository typeProcedureRepository;

    public List<Procedure> findAll() {
        return repository.findAll();
    }

    public Optional<Procedure> findById(Integer id) {
        return repository.findById(id);
    }

    public Procedure save(ProcedureDTO dto) {
        dto.setName(dto.getName()); // converter para maiúsculas

        if (repository.existsByName(dto.getName())) {
            throw new DuplicateNameException(dto.getName());
        }

        if (!typeProcedureRepository.existsById(dto.getTypeProcedureId())) {
            throw new TypeProcedureNotFoundException(dto.getTypeProcedureId());
        }

        Procedure procedure = new Procedure(null, dto.getName(), dto.getTypeProcedureId());
        return repository.save(procedure);
    }

    public Optional<Procedure> update(Integer id, ProcedureDTO dto) {

        dto.setName(dto.getName()); // converter para maiúsculas

        // Verifica se outro registro já tem esse nome
        boolean alreadyExists = repository.existsByName(dto.getName());
        boolean sameAsCurrent = repository.findById(id)
                .map(p -> p.getName().equalsIgnoreCase(dto.getName()))
                .orElse(false);

        if (alreadyExists && !sameAsCurrent) {
            throw new DuplicateNameException(dto.getName());
        }

        if (!typeProcedureRepository.existsById(dto.getTypeProcedureId())) {
            throw new TypeProcedureNotFoundException(dto.getTypeProcedureId());
        }

        return repository.findById(id).map(existing -> {
            dto.setName(dto.getName()); // converter para maiúsculas
            existing.setName(dto.getName());
            existing.setTypeProcedureId(dto.getTypeProcedureId());
            return repository.save(existing);
        });
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }
}
