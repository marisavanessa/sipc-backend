package com.loliana.sipc.repository;

import com.loliana.sipc.model.Historical;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalRepository extends JpaRepository<Historical, Integer> {
}
