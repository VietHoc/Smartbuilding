package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.SensorData;
import com.viethoc.smartbuilding.repository.sensor_data.SensorDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SensorDataService {

    @Autowired
    private SensorDataRepository sensorDataRepository;
    public List<SensorData> getListSensorData(Long automateId) {
        return null;
    }
}
