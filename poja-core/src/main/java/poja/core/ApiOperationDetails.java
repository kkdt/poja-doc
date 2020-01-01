/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class ApiOperationDetails {
    private String api;
    private String path;
    private String description;
    private Set<String> consumes;
    private Set<String> produces;
    private Set<String> methods;
    private String url;
    private List<ApiParamDetails> parameters;

    public ApiOperationDetails parameters(List<ApiParamDetails> parameters) {
        this.parameters = parameters;
        return this;
    }

    public List<ApiParamDetails> parameters() {
        return parameters;
    }

    public ApiOperationDetails url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return Optional.ofNullable(url)
            .orElse("");
    }

    public ApiOperationDetails(String path) {
        this.path = path;
    }

    public ApiOperationDetails api(String api) {
        this.api = api;
        return this;
    }

    public ApiOperationDetails description(String description) {
        this.description = description;
        return this;
    }

    public ApiOperationDetails consumes(Set<String> consumes) {
        this.consumes = consumes;
        return this;
    }

    public ApiOperationDetails produces(Set<String> produces) {
        this.produces = produces;
        return this;
    }

    public ApiOperationDetails methods(Set<String> methods) {
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
