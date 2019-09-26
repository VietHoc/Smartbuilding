package com.viethoc.smartbuilding.controller;

import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.service.AutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor-data")
public class SensorDataController {
    @Autowired
    private  AutomateService automateService;

    @GetMapping("/monitor")
    public List<Sensor> getListSensorDataFixByAutomate(@RequestParam Long automateId) {
        return automateService.getAllSensorByAutomate(automateId);
    }

    @GetMapping("/updated-monitor")
    public List<Sensor> updateSensorDataFixByAutomate(@RequestParam Long automateId) {
        return automateService.getAllSensorByAutomate(automateId).subList(0, 5);
    }

    @GetMapping("/interval")
    public int getInterval() {
        return 10;
    }
}
