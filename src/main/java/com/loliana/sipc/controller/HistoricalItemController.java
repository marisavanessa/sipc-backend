package com.loliana.sipc.controller;

import com.loliana.sipc.dto.HistoricalItemDTO;
import com.loliana.sipc.service.HistoricalItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historical-items")
@RequiredArgsConstructor
public class HistoricalItemController {

    private final HistoricalItemService service;

    @GetMapping
    public List<HistoricalItemDTO> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public HistoricalItemDTO getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    public HistoricalItemDTO create(@RequestBody HistoricalItemDTO dto) {
        return service.save(upperCaseFields(dto));
    }

    @PutMapping("/{id}")
    public HistoricalItemDTO update(@PathVariable Integer id, @RequestBody HistoricalItemDTO dto) {
        return service.update(id, upperCaseFields(dto));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    private HistoricalItemDTO upperCaseFields(HistoricalItemDTO dto) {
        // Nenhum campo de texto precisa ser convertido, mas método mantido para padrão.
        return dto;
    }
}
