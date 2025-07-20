package com.loliana.sipc.service;

import com.loliana.sipc.dto.HistoricalDTO;
import com.loliana.sipc.exception.EntityNotFoundException;
import com.loliana.sipc.model.EntityModel;
import com.loliana.sipc.model.Historical;
import com.loliana.sipc.model.Procedure;
import com.loliana.sipc.model.User;
import com.loliana.sipc.repository.EntityRepository;
import com.loliana.sipc.repository.HistoricalRepository;
import com.loliana.sipc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricalService {

    private final HistoricalRepository repository;
    private final UserRepository userRepository;
    private final EntityRepository entityRepository;

    public HistoricalDTO save(HistoricalDTO dto) {
        Historical entity = toEntity(dto);
        return toDTO(repository.save(entity));
    }

    public HistoricalDTO update(Integer id, HistoricalDTO dto) {
        Historical entity = repository.findById(id).orElseThrow();
        dto.setId(id);
        return toDTO(repository.save(toEntity(dto)));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<HistoricalDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public Optional<HistoricalDTO> findById(Integer id) {
        return repository.findById(id).map(this::toDTO);
    }

    private HistoricalDTO toDTO(Historical h) {
        return HistoricalDTO.builder()
                .id(h.getId())
                .userId(h.getUserId())
                .entityId(h.getEntityId())
                .scheduled(h.getScheduled())
                .dateScheduled(h.getDateScheduled())
                .dateIn(h.getDateIn())
                .dateOut(h.getDateOut())
                .build();
    }

    private Historical toEntity(HistoricalDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + dto.getUserId()));

        EntityModel entity = entityRepository.findById(dto.getEntityId())
                .orElseThrow(() -> new EntityNotFoundException("Entidade não encontrada com ID: " + dto.getEntityId()));

        return Historical.builder()
                .id(dto.getId())
                .userId(dto.getUserId())
                .entityId(dto.getEntityId())
                .scheduled(dto.getScheduled() != null ? dto.getScheduled().toUpperCase() : null)
                .dateScheduled(dto.getDateScheduled())
                .dateIn(dto.getDateIn())
                .dateOut(dto.getDateOut())
                .build();
    }
}
