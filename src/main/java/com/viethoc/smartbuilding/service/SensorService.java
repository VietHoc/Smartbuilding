package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> getAllSensors() {
        // sensor active have status 0 or 1
        List<Long> activeCode = new ArrayList<>(Arrays.asList(0L, 1L));
        return sensorRepository.findAllByStatusIn(activeCode);
    }

    public Sensor addSenSor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Sensor updateSenSor(Long id, Sensor sensor) {
        sensor.setId(id);
        return sensorRepository.save(sensor);
    }

    public void deleteSenSor(Long id) {
        sensorRepository.deleteById(id);
    }

    public Sensor updateStatus(long id, Long status) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isPresent()) {
            sensor.get().setStatus(status);
            return sensorRepository.save(sensor.get());
        }

        throw new RuntimeException("Invalid sensor id " + id);
    }
}
