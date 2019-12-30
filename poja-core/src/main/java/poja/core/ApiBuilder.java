/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Optional;

public class ApiBuilder {
    public static final ApiBuilder UncatApi = new ApiBuilder()
        .value("uncat")
        .name("uncat")
        .description("Uncategorized API");

    private String name;
    private String value;
    private String description;

    public ApiBuilder value(String value) {
        this.value = value;
        return this;
    }

    public ApiBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ApiBuilder description(String description) {
        this.description = description;
        return this;
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
