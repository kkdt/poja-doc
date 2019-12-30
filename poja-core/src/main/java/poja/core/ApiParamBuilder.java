/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Optional;
import java.util.function.Supplier;

public class ApiParamBuilder {

    public static ApiParamBuilder apiPathParam(String value) {
        return new ApiParamBuilder(value, "path")
            .type("string");
    }

    public static ApiParamBuilder apiBodyParam(String value) {
        return new ApiParamBuilder(value, "body");
    }

    private String value;
    private Supplier<String> description;
    private boolean required = false;
    private String paramType;
    private String type;


    private ApiParamBuilder(String value, String paramType) {
        this.value = value;
        this.paramType = paramType;
    }

    public ApiParamBuilder description(Supplier<String> description) {
        this.description = description;
        return this;
    }

    public ApiParamBuilder type(String type) {
        this.type = type;
        return this;
    }

    public String description() {
        return Optional.ofNullable(description)
            .orElse(() -> "")
            .get();
    }

    public ApiParamBuilder required(boolean required) {
        this.required = required;
        return this;
    }

    public String paramType() {
        return Optional.ofNullable(paramType)
            .orElse("");
    }

    public boolean required() {
        return required;
    }

    public String type() {
        return Optional.ofNullable(type)
            .orElse("");
    }
}
