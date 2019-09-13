package com.viethoc.smartbuilding.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class SensorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "Watchlist_id")
    private Long SensorId;

    @Column(name = "Dec_value")
    private Float DecValue;

    @Column(name = "Bool_value")
    private Boolean BoolValue;

    @Column(name = "Str_val")
    private  String Str_val;

    @Column(name = "Modification_date")
    private Date Modification_date;
}
