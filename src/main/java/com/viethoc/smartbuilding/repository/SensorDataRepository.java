package com.viethoc.smartbuilding.repository;

import com.viethoc.smartbuilding.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
