package com.costa.luiz.zero2hero.model.movie;

import java.util.Arrays;

public enum Rating {
    G("General, suitable for all ages"),
    PG("Parental Guidance Suggested"),
    PG13("Parents Strongly Cautioned"),
    R("Restricted"),
    NC17("No Children Under 17"),
    X("Not Suitable For Children");

    private String description;

    Rating(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Rating get(String key) {
        return Arrays.stream(Rating.values())
                .filter(current -> key.equalsIgnoreCase(current.name()))
                .findFirst().orElse(Rating.R);
    }
}
