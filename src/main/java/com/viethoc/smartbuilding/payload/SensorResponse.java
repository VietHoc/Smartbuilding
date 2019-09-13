package com.viethoc.smartbuilding.payload;


import com.viethoc.smartbuilding.model.Sensor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class SensorResponse {
    private List<Sensor> items;
    private int totalCount;
}
