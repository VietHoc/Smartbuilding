package com.viethoc.smartbuilding.controller;

import com.viethoc.smartbuilding.model.Automate;
import com.viethoc.smartbuilding.service.AutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/automates")
public class AutomateController {
    @Autowired
    private AutomateService automateService;

    @GetMapping
    public List<Automate> getListAutomates(){
        return automateService.getAllAutomates();
    }
}
