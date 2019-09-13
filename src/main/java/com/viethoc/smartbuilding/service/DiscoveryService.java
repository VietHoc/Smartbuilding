package com.viethoc.smartbuilding.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.viethoc.smartbuilding.model.Sensor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DiscoveryService {
    ObjectMapper mapper = new ObjectMapper();
    String jsonInput = new String();

    @Autowired
    private SensorService sensorService;

    @Autowired
    private RedisTemplate redisTemplate;

    public List<Sensor> newSensors() throws IOException {
        // yesterday sensors
        jsonInput = (String) redisTemplate.opsForValue().get("sensors");
        List<Sensor> sensors = mapper.readValue(jsonInput, new TypeReference<List<Sensor>>(){});

        // today sensors
        List newListSensors = sensorService.getAllSensorActive();

        List<Sensor> newSensors = new ArrayList<>();

        if (newListSensors.size() > sensors.size()) {
            newListSensors.forEach(sensor -> {
                if (!sensors.contains(sensor)) {
                    newSensors.add((Sensor) sensor);
                }
            });
        }

        return newSensors;
    }

}
