package com.viethoc.smartbuilding.repository;

import com.viethoc.smartbuilding.model.Sensor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SensorRepository extends JpaRepository<Sensor, Long> {
    List<Sensor> findAllByStatusIn(List<Long> status, Pageable pageable);
    List<Sensor> findAllByStatusIn(List<Long> status);
    List<Sensor> findAllByAutomateId(Long automateId);
}