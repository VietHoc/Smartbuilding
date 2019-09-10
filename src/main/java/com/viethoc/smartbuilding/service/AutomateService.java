package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.Automate;
import com.viethoc.smartbuilding.repository.AutomateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AutomateService {

    @Autowired
    private AutomateRepository automateRepository;

    public List<Automate> getAllAutomates() {
        return automateRepository.findAll();
    }
}

