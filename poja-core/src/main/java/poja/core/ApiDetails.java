/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Optional;

/**
 * The values captured by {@link poja.core.annotation.Api} are translated into this model.
 */
public class ApiDetails {
    public static final ApiDetails UncategorizedApi = new ApiDetails()
        .value("uncategorized")
        .name("uncategorized")
        .description("Uncategorized API");

    private String name;
    private String value;
    private String description;
    private String type;
    private String url;

    public ApiDetails value(String value) {
        this.value = value;
        return this;
    }

    public ApiDetails name(String name) {
        this.name = name;
        return this;
    }

    public ApiDetails description(String description) {
        this.description = description;
        return this;
    }

    public ApiDetails type(String type) {
        this.type = type;
        return this;
    }

    public ApiDetails url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return Optional.ofNullable(url)
            .orElse("");
    }

    public String type() {
        return Optional.ofNullable(type)
            .orElse("");
    }

    public String description() {
        return Optional.ofNullable(description)
            .orElse("");
    }

    public String value() {
        return Optional.ofNullable(value)
            .orElse("");
    }

    public String name() {
        return Optional.ofNullable(name)
            .orElse("");
    }
}
