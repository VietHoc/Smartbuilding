package com.viethoc.smartbuilding.controller;

import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.payload.SensorResponse;
import com.viethoc.smartbuilding.service.SensorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
public class SensorController {
    @Autowired
    private SensorService sensorService;

    @GetMapping
    public SensorResponse getListAutomates(@RequestParam String sort, @RequestParam String order, @RequestParam int page, @RequestParam int pageSize, @RequestParam String search) {
        return sensorService.getAllSensors(sort, order, page, pageSize, search);
    }

    @PostMapping
    public Sensor addSensor(@RequestBody Sensor sensor) {
        return sensorService.addSenSor(sensor);
    }

    @PutMapping("/{id}")
    public Sensor updateSenSor(@PathVariable Long id, @RequestBody Sensor sensor) {
        return sensorService.updateSenSor(id, sensor);
    }

    @DeleteMapping("/{id}")
    public Sensor deleteSensor(@PathVariable Long id) {
        Long statusSensorDeleted = 2L;
        return sensorService.updateStatus(id, statusSensorDeleted);
    }
}
