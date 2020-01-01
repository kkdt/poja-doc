/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Optional;

public class ApiParamDetails {

    private String name;
    private boolean required = false;
    private Class<?> type;
    private String paramType;
    private String location;
    private String url;

    public ApiParamDetails url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return Optional.ofNullable(url)
            .orElse("");
    }

    public ApiParamDetails location(String location) {
        this.location = location;
        return this;
    }

    public String location() {
        return Optional.ofNullable(location)
            .orElse("");
    }

    public ApiParamDetails name(String name) {
        this.name = name;
        return this;
    }

    public String name() {
        return Optional.ofNullable(name)
            .orElse("");
    }

    public ApiParamDetails type(Class<?> type) {
        this.type = type;
        return this;
    }

    public Class<?> type() {
        return type;
    }

    public ApiParamDetails required(boolean required) {
        this.required = required;
        return this;
    }

    public boolean required() {
        return required;
    }

}
