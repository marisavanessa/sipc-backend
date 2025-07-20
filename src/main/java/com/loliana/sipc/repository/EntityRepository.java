package com.loliana.sipc.repository;

import com.loliana.sipc.model.EntityModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntityRepository extends JpaRepository<EntityModel, Integer> {
    boolean existsByCnpj(String cnpj);
}
