package com.javaapi.pmanager.infrastructure.util;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class SortProperties {

    private static final List<String> ALLOWED_PROPERTIES = List.of(
            "title",
            "status",
            "numberOfDays"
    );

    private final List<String> sortPropertiesList;

    public SortProperties(String commaSeparedString) {
        sortPropertiesList = Arrays
                .stream(commaSeparedString.split(","))
                .filter(ALLOWED_PROPERTIES::contains)
                .toList();
    }
}
