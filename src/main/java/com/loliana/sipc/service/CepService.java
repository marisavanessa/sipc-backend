package com.loliana.sipc.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@Slf4j
public class CepService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String buscarEnderecoPorCep(String cep) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response != null && response.get("logradouro") != null) {
                return response.get("logradouro").toString();
            }
        } catch (Exception e) {
            log.error("Erro ao consultar o CEP: {}", cep, e);
        }
        return null;
    }

    public void preencherDadosEndereco(String cep, com.loliana.sipc.model.User user) {
        try {
            String url = "https://viacep.com.br/ws/" + cep + "/json/";
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response != null && !Boolean.TRUE.equals(response.get("erro"))) {
                if (response.get("logradouro") != null)
                    user.setAddress(response.get("logradouro").toString());

                if (response.get("bairro") != null)
                    user.setDistrict(response.get("bairro").toString());

                if (response.get("localidade") != null)
                    user.setCity(response.get("localidade").toString());

                if (response.get("uf") != null)
                    user.setUf(response.get("uf").toString());
            }
        } catch (Exception e) {
            log.error("Erro ao consultar CEP: {}", cep, e);
        }
    }
}

