package com.viethoc.smartbuilding.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Automate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    private String name;
    private String ip;
    private String uri;
    private Boolean active;
}
