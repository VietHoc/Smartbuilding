package com.viethoc.smartbuilding.repository.sensor_data;

import com.viethoc.smartbuilding.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
