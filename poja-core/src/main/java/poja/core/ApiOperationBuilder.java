/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class ApiOperationBuilder {
    private String api;
    private String path;
    private String description;
    private Set<String> consumes;
    private Set<String> produces;
    private Set<String> methods;

    public ApiOperationBuilder(String path) {
        this.path = path;
    }

    public ApiOperationBuilder api(String api) {
        this.api = api;
        return this;
    }

    public ApiOperationBuilder description(String description) {
        this.description = description;
        return this;
    }

    public ApiOperationBuilder consumes(Set<String> consumes) {
        this.consumes = consumes;
        return this;
    }

    public ApiOperationBuilder produces(Set<String> produces) {
        this.produces = produces;
        return this;
    }

    public ApiOperationBuilder methods(Set<String> methods) {
        this.methods = methods;
        return this;
    }

    public String path() {
        return Optional.ofNullable(path)
            .orElse("");
    }

    public String description() {
        return Optional.ofNullable(description)
            .orElse("");
    }

    public Set<String> consumes() {
        return Optional.ofNullable(consumes)
            .orElse(Collections.emptySet());
    }

    public Set<String> produces() {
        return Optional.ofNullable(produces)
            .orElse(Collections.emptySet());
    }

    public Set<String> methods() {
        return Optional.ofNullable(methods)
            .orElse(Collections.emptySet());
    }

    public String api() {
        return api;
    }
}
