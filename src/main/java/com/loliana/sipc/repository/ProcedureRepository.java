package com.loliana.sipc.repository;

import com.loliana.sipc.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure, Integer> {
    boolean existsByName(String name);
}
