package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.SensorType;
import com.viethoc.smartbuilding.repository.sensor_type.SensorTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorTypeService {
    @Autowired
    private SensorTypeRepository sensorTypeRepository;

    public List<SensorType> getAllSensorType(){
        return sensorTypeRepository.findAll();
    }

}
