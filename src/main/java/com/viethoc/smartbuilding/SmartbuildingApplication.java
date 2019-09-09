package com.viethoc.smartbuilding;

import com.viethoc.smartbuilding.model.Automate;
import com.viethoc.smartbuilding.repository.AutomateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SmartbuildingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartbuildingApplication.class, args);
	}
}
