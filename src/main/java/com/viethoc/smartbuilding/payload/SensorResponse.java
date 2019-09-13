package com.viethoc.smartbuilding.payload;


import com.viethoc.smartbuilding.model.Sensor;
import lombok.Data;

import java.util.List;

@Data
public class SensorResponse {
    private List<Sensor> items;
    private int totalCount;
}
