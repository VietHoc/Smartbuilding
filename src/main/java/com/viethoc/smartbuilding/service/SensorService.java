package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public List<Sensor> getAllSensors() {
        return sensorRepository.findAll();
    }
}