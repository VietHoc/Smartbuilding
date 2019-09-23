package com.viethoc.smartbuilding.repository.sensor_data;

import com.viethoc.smartbuilding.model.SensorData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorDataRepository extends JpaRepository<SensorData, Long> {
}
