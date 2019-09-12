package com.viethoc.smartbuilding.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Time;

@Entity
@Data
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "automate_id")
    private long automateId;

    @Column(name = "sensortype_id")
    private long sensortypeId;

    @Column(name = "uri")
    private String uri;

    @Column(name = "name")
    private String name;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "location_identifier")
    private String locationIdentifier;

    @Column(name = "status")
    private Long status;

    @Column(name = "creation_date")
    private Time creationDate;

    @Column(name = "modification_date")
    private Time modificationDate;
}
