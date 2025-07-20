package com.loliana.sipc.service;

import com.loliana.sipc.dto.HistoricalItemDTO;
import com.loliana.sipc.exception.EntityNotFoundException;
import com.loliana.sipc.model.*;
import com.loliana.sipc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoricalItemService {

    private final HistoricalItemRepository repository;
    private final HistoricalRepository historicalRepository;
    private final ProcedureRepository procedureRepository;
    private final DoctorRepository doctorRepository;

    public List<HistoricalItemDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public HistoricalItemDTO findById(Integer id) {
        return toDTO(repository.findById(id).orElseThrow());
    }

    public HistoricalItemDTO save(HistoricalItemDTO dto) {
        return toDTO(repository.save(toEntity(dto)));
    }

    public HistoricalItemDTO update(Integer id, HistoricalItemDTO dto) {
        HistoricalItem item = repository.findById(id).orElseThrow();
        dto.setId(item.getId());
        return toDTO(repository.save(toEntity(dto)));
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    private HistoricalItemDTO toDTO(HistoricalItem item) {
        return HistoricalItemDTO.builder()
                .id(item.getId())
                .historicalId(item.getHistorical().getId())
                .procedureId(item.getProcedure().getId())
                .doctorId(item.getDoctor().getId())
                .dateIn(item.getDateIn())
                .dateOut(item.getDateOut())
                .amount(item.getAmount())
                .build();
    }

    private HistoricalItem toEntity(HistoricalItemDTO dto) {

        Historical historical = historicalRepository.findById(dto.getHistoricalId())
                .orElseThrow(() -> new EntityNotFoundException("Histórico não encontrado com ID: " + dto.getHistoricalId()));

        Procedure procedure = procedureRepository.findById(dto.getProcedureId())
                .orElseThrow(() -> new EntityNotFoundException("Procedimento não encontrado com ID: " + dto.getProcedureId()));

        Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Médico não encontrado com ID: " + dto.getDoctorId()));

        return HistoricalItem.builder()
                .id(dto.getId())
                .historical(historicalRepository.findById(dto.getHistoricalId()).orElseThrow())
                .procedure(procedureRepository.findById(dto.getProcedureId()).orElseThrow())
                .doctor(doctorRepository.findById(dto.getDoctorId()).orElseThrow())
                .dateIn(dto.getDateIn())
                .dateOut(dto.getDateOut())
                .amount(dto.getAmount())
                .build();
    }
}
