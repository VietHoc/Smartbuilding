package com.viethoc.smartbuilding.repository.sensor_type;

import com.viethoc.smartbuilding.model.SensorType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorTypeRepository extends JpaRepository<SensorType, Long> {
}
