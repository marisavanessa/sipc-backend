package com.loliana.sipc.service;

import com.loliana.sipc.dto.TypeEstablishmentDTO;
import com.loliana.sipc.model.TypeEstablishment;
import com.loliana.sipc.repository.TypeEstablishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeEstablishmentServiceImpl implements TypeEstablishmentService {

    private final TypeEstablishmentRepository repository;

    @Override
    public TypeEstablishmentDTO create(TypeEstablishmentDTO dto) {
        TypeEstablishment entity = TypeEstablishment.builder()
                .name(dto.getName().toUpperCase())
                .build();
        entity = repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public TypeEstablishmentDTO update(Integer id, TypeEstablishmentDTO dto) {
        Optional<TypeEstablishment> optional = repository.findById(id);
        if (optional.isEmpty()) {
            throw new RuntimeException("Tipo de estabelecimento não encontrado");
        }
        TypeEstablishment entity = optional.get();
        entity.setName(dto.getName().toUpperCase());
        repository.save(entity);
        return mapToDTO(entity);
    }

    @Override
    public void delete(Integer id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Tipo de estabelecimento não encontrado");
        }
        repository.deleteById(id);
    }

    @Override
    public List<TypeEstablishmentDTO> findAll() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .toList();
    }

    @Override
    public TypeEstablishmentDTO findById(Integer id) {
        return repository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new RuntimeException("Tipo de estabelecimento não encontrado"));
    }


    private TypeEstablishmentDTO mapToDTO(TypeEstablishment entity) {
        return TypeEstablishmentDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
