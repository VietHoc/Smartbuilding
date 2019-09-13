package com.viethoc.smartbuilding.service;

import com.viethoc.smartbuilding.model.Sensor;
import com.viethoc.smartbuilding.payload.SensorResponse;
import com.viethoc.smartbuilding.repository.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class SensorService {

    @Autowired
    private SensorRepository sensorRepository;

    public SensorResponse getAllSensors(String sort, String order, int page, int pageSize) {
        SensorResponse sensorResponse = new SensorResponse();
        // sensor enable have status 0 or 1
        List<Long> activeCode = new ArrayList<>(Arrays.asList(0L, 1L));

        if (order.equals("asc")) {
            sensorResponse.setItems(sensorRepository.findAllByStatusIn(activeCode, PageRequest.of(page, pageSize, Sort.by(sort).ascending())));
        } else {
            sensorResponse.setItems(sensorRepository.findAllByStatusIn(activeCode, PageRequest.of(page, pageSize, Sort.by(sort).descending())));
        }

        sensorResponse.setTotalCount(sensorRepository.findAllByStatusIn(activeCode).size());
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
