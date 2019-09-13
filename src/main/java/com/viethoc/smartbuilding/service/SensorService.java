package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.SearchCriteria;
import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.payload.SensorResponse;
import com.viethoc.smartbuilding.repository.sensor.SensorRepository;
import com.viethoc.smartbuilding.repository.sensor.SensorSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public SensorResponse getAllSensors(String sort, String order, int page, int pageSize, String search) {
        SensorResponse sensorResponse = new SensorResponse();

        // sensor search
        SensorSpecification spec1 = new SensorSpecification().setCriteria(new SearchCriteria().setKey("name").setOperation(":").setValue(search));
        SensorSpecification spec2 = new SensorSpecification().setCriteria(new SearchCriteria().setKey("uri").setOperation(":").setValue(search));

        // sensor active
        SensorSpecification spec3 = new SensorSpecification().setCriteria(new SearchCriteria().setKey("status").setOperation(":").setValue("0"));
        SensorSpecification spec4 = new SensorSpecification().setCriteria(new SearchCriteria().setKey("status").setOperation(":").setValue("1"));

        Page pageSensor = Page.empty();

        if (order.equals("asc")) {
            pageSensor = sensorRepository.findAll(Specification.where(spec1).or(spec2).and(Specification.where(spec3).or(spec4)), PageRequest.of(page, pageSize, Sort.by(sort).ascending()));
        } else {
            pageSensor = sensorRepository.findAll(Specification.where(spec1).or(spec2).and(Specification.where(spec3).or(spec4)), PageRequest.of(page, pageSize, Sort.by(sort).descending()));
        }

        sensorResponse.setItems(pageSensor.getContent()).setTotalCount((int) pageSensor.getTotalElements());
        return sensorResponse;
    }

    public Sensor addSenSor(Sensor sensor) {
        return sensorRepository.save(sensor);
    }

    public Sensor updateSenSor(Long id, Sensor sensor) {
        sensor.setId(id);
        return sensorRepository.save(sensor);
    }

    public void deleteSenSor(Long id) {
        sensorRepository.deleteById(id);
    }

    public Sensor updateStatus(long id, Long status) {
        Optional<Sensor> sensor = sensorRepository.findById(id);
        if (sensor.isPresent()) {
            sensor.get().setStatus(status);
            return sensorRepository.save(sensor.get());
        }

        throw new RuntimeException("Invalid sensor id " + id);
    }

    public List<Sensor> getAllSensorActive(){
        // sensor active have status 1
        List<Long> activeCode = new ArrayList<>(Arrays.asList(1L));
        return sensorRepository.findAllByStatusIn(activeCode);
    }

    public List<Sensor> findAllByAutomateId(Long automateId) {
        return sensorRepository.findAllByAutomateId(automateId);
    }
}
