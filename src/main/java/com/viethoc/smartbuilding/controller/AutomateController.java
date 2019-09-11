package com.viethoc.smartbuilding.controller;

import com.viethoc.smartbuilding.model.Automate;
import com.viethoc.smartbuilding.service.AutomateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public Automate addAutomate(@RequestBody Automate automate){
        return automateService.addAutomate(automate);
    }

    @PutMapping("/{id}")
    public Automate updateAutomate(@PathVariable Long id, @RequestBody Automate automate){
        return automateService.updateAutomate(id, automate);
    }

    @DeleteMapping("/{id}")
    public void deleteAutomate(@PathVariable Long id){
        automateService.deleteAutomate(id);
    }
}
