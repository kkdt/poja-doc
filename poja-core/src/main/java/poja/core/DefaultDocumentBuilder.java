/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import poja.core.annotation.Api;
import poja.core.annotation.ApiOperation;

public class DefaultDocumentBuilder implements PojaDocumentBuilder {
    private ApiBuilder api;
    private final Set<ApiOperationBuilder> operations;

    public DefaultDocumentBuilder() {
        this.operations = new HashSet<>();
    }

    @Override
    public void process(Class<?> clazz) {
        if(clazz != null) {
            processApi(clazz);
            processOperations(clazz);

            // associate operations to their respective APIs
            String key = Optional.ofNullable(api).orElse(ApiBuilder.UncatApi).name();
            operations.forEach(o -> o.api(key));
        }
    }

    @Override
    public ApiBuilder api() {
        return api;
    }

    @Override
    public Set<ApiOperationBuilder> operations() {
        return operations;
    }

    private void processApi(Class<?> clazz) {
        Arrays.stream(clazz.getAnnotations())
            .filter(annotation -> annotation.annotationType().getCanonicalName().equals(Api.class.getCanonicalName()))
            .findFirst()
            .ifPresent(annotation -> {
                String name = "";
                try {
                    name = (String)annotation.annotationType().getMethod("name").invoke(annotation);
                } catch (Exception e) {}

                String value = "";
                try {
                    value = (String)annotation.annotationType().getMethod("value").invoke(annotation);
                } catch (Exception e) {}

                String description = "";
                try {
                    description = (String)annotation.annotationType().getMethod("description").invoke(annotation);
                } catch (Exception e) {}

                this.api = new ApiBuilder()
                    .value(value)
                    .name(name)
                    .description(description);
            });
    }

    private void processOperations(Class<?> clazz) {
        Arrays.stream(clazz.getMethods())
            .flatMap(method -> Arrays.stream(method.getAnnotations()))
            .filter(annotation -> annotation.annotationType().getCanonicalName().equals(ApiOperation.class.getCanonicalName()))
            .map(annotation -> {
                String path = "";
                try {
                    path = (String)annotation.annotationType().getMethod("path").invoke(annotation);
                } catch (Exception e) {}

                String api = "";
                try {
                    api = (String)annotation.annotationType().getMethod("api").invoke(annotation);
                } catch (Exception e) {}

                String description = "";
                try {
                    description = (String)annotation.annotationType().getMethod("description").invoke(annotation);
                } catch (Exception e) {}

                String[] methods = {};
                try {
                    methods = (String[])annotation.annotationType().getMethod("methods").invoke(annotation);
                } catch (Exception e) {}

                String[] produces = {};
                try {
                    produces = (String[])annotation.annotationType().getMethod("produces").invoke(annotation);
                } catch (Exception e) {}

                String[] consumes = {};
                try {
                    consumes = (String[])annotation.annotationType().getMethod("consumes").invoke(annotation);
                } catch (Exception e) {}

                return new ApiOperationBuilder(path)
                    .api(api)
                    .description(description)
                    .methods(Arrays.stream(methods).collect(Collectors.toSet()))
                    .produces(Arrays.stream(produces).collect(Collectors.toSet()))
                    .consumes(Arrays.stream(consumes).collect(Collectors.toSet()));
            }).forEach(operations::add);
    }
}
