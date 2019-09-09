package com.viethoc.smartbuilding.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Time;

@Entity
@Data
public class Watchlist {
    @Id
    private Long id;

    @Column(name = "automate_id")
    private long automateId;

    @Column(name = "sensortype_id")
    private long sensortypeId;

    @Column(name = "uri")
    private String uri;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "location_identifier")
    private String locationIdentifier;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "creation_date")
    private Time creationDate;

    @Column(name = "modification_date")
    private Time modificationDate;
}
