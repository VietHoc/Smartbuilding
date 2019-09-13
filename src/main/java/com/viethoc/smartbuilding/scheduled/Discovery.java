package com.viethoc.smartbuilding.scheduled;

import com.viethoc.smartbuilding.service.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Configuration
@EnableScheduling
@Component
public class Discovery {
    @Autowired
    private DiscoveryService discoveryService;

    @Scheduled(cron="* * * * * *")
    public void discoveryAutomate() throws IOException {
        System.out.println("Start discovery");

        System.out.println(discoveryService.newSensors().size());
        if (discoveryService.newSensors().size() > 0) {

        }
    }
}
