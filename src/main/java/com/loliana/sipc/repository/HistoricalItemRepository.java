package com.loliana.sipc.repository;

import com.loliana.sipc.model.HistoricalItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricalItemRepository extends JpaRepository<HistoricalItem, Integer> {
}
