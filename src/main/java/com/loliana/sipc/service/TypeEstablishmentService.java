package com.loliana.sipc.service;

import com.loliana.sipc.dto.TypeEstablishmentDTO;

import java.util.List;

public interface TypeEstablishmentService {
    TypeEstablishmentDTO create(TypeEstablishmentDTO dto);
    TypeEstablishmentDTO update(Integer id, TypeEstablishmentDTO dto);
    List<TypeEstablishmentDTO> findAll();
    TypeEstablishmentDTO findById(Integer id);
    void delete(Integer id);
}
