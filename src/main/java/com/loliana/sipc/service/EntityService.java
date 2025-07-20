package com.loliana.sipc.service;

import com.loliana.sipc.dto.EntityDTO;
import com.loliana.sipc.dto.ProcedureDTO;
import com.loliana.sipc.exception.DuplicateNameException;
import com.loliana.sipc.exception.TypeEstablishmentNotFoundException;
import com.loliana.sipc.exception.TypeProcedureNotFoundException;
import com.loliana.sipc.model.EntityModel;
import com.loliana.sipc.model.Procedure;
import com.loliana.sipc.model.TypeEstablishment;
import com.loliana.sipc.repository.EntityRepository;
import com.loliana.sipc.repository.TypeEstablishmentRepository;
import com.loliana.sipc.repository.TypeProcedureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EntityService {

    private final EntityRepository repository;
    private final TypeEstablishmentRepository typeEstablishmentRepository;

    public EntityModel create(EntityDTO dto) {
        if (!isValidCNPJ(dto.getCnpj())) {
            throw new IllegalArgumentException("CNPJ inválido");
        }
        if (repository.existsByCnpj(dto.getCnpj())) {
            throw new IllegalArgumentException("CNPJ já cadastrado");
        }
        if (!typeEstablishmentRepository.existsById(dto.getTypeEstablishmentId())) {
            throw new TypeEstablishmentNotFoundException(dto.getTypeEstablishmentId());
        }
        EntityModel model = toModel(dto);
        fillAddressByCep(model);
        return repository.save(model);
    }

    public EntityModel update(Integer id, EntityDTO dto) {
        if (!typeEstablishmentRepository.existsById(dto.getTypeEstablishmentId())) {
            throw new TypeEstablishmentNotFoundException(dto.getTypeEstablishmentId());
        }
        EntityModel model = toModel(dto);
        model.setId(id);
        fillAddressByCep(model);
        return repository.save(model);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public List<EntityModel> listAll() {
        return repository.findAll();
    }

    public Optional<EntityModel> findById(Integer id) {
        return repository.findById(id);
    }

    private EntityModel toModel(EntityDTO dto) {
        return EntityModel.builder()
                .cnpj(dto.getCnpj())
                .name(toUpper(dto.getName()))
                .cep(dto.getCep())
                .address(toUpper(dto.getAddress()))
                .number(dto.getNumber())
                .district(toUpper(dto.getDistrict()))
                .city(toUpper(dto.getCity()))
                .uf(toUpper(dto.getUf()))
                .typeEstablishmentId(dto.getTypeEstablishmentId())
                .build();
    }

    private String toUpper(String value) {
        return value != null ? value.toUpperCase() : null;
    }

    private void fillAddressByCep(EntityModel model) {
        try {
            RestTemplate rest = new RestTemplate();
            String url = "https://viacep.com.br/ws/" + model.getCep() + "/json/";
            Map<?, ?> response = rest.getForObject(url, Map.class);
            if (response != null && !response.containsKey("erro")) {
                model.setAddress(toUpper((String) response.get("logradouro")));
                model.setDistrict(toUpper((String) response.get("bairro")));
                model.setCity(toUpper((String) response.get("localidade")));
                model.setUf(toUpper((String) response.get("uf")));
            }
        } catch (Exception e) {
            // continuar com os dados fornecidos
        }
    }

    private boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || !cnpj.matches("\\d{14}")) return false;

        int[] weights1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weights2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights1[i];
            }
            int mod = sum % 11;
            char check1 = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            sum = 0;
            for (int i = 0; i < 13; i++) {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weights2[i];
            }
            mod = sum % 11;
            char check2 = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            return check1 == cnpj.charAt(12) && check2 == cnpj.charAt(13);
        } catch (Exception e) {
            return false;
        }
    }
}
