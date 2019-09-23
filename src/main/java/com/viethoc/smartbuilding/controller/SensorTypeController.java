package com.viethoc.smartbuilding.controller;

import com.viethoc.smartbuilding.model.SensorType;
import com.viethoc.smartbuilding.service.SensorTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensor-types")
public class SensorTypeController {
    @Autowired
    private SensorTypeService sensorTypeService;

    @GetMapping
    public List<SensorType> getAllSensorType() {
        return sensorTypeService.getAllSensorType();
    }
}
