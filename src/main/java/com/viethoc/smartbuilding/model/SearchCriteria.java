package com.viethoc.smartbuilding.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
}
