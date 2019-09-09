package com.viethoc.smartbuilding.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class Automate {
    @Id
    private long id;
    private String name;
    private String ip;
    private String uri;
    private Boolean active;
}
