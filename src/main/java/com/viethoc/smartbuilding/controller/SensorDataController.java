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
    public List<Sensor> getDetailAutomate(@RequestParam Long automateId, @RequestParam(value = "", required=false) String valueDate ) {
        if (valueDate == null) {
            return automateService.getAllSensorByAutomate(automateId);
        } else {
            return automateService.getAllSensorByAutomate(automateId).subList(0, 5);
        }
    }
}
