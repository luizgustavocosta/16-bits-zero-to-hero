package com.costa.luiz.zero2hero.model.movie;

import java.util.Arrays;

public enum Classification {
    G("General, suitable for all ages"),
    PG("Parental Guidance Suggested"),
    PG13("Parents Strongly Cautioned"),
    R("Restricted"),
    NC17("No Children Under 17"),
    X("Not Suitable For Children");

    private String description;

    Classification(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Classification get(String key) {
        return Arrays.stream(Classification.values())
                .filter(current -> key.equalsIgnoreCase(current.name()))
                .findFirst().orElse(Classification.R);
    }
}
