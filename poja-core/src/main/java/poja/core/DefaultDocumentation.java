/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-core' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import poja.core.annotation.Api;
import poja.core.annotation.ApiOperation;

public class DefaultDocumentation implements PojaDocumentation {
    private ApiDetails api;
    private final Set<ApiOperationDetails> operations;
    private boolean undocumented = false;

    public DefaultDocumentation() {
        this.operations = new HashSet<>();
    }

    @Override
    public void process(Class<?> clazz, Set<String> interested) {
        if(clazz == null) {
            throw new IllegalArgumentException("Invalid class to process API documentation");
        }

        // do not process if undocumented
        if(checkPotentialUndocumented(clazz, interested)) {
            System.out.println(String.format("Potential API: %s", clazz.getCanonicalName()));
            this.api = new ApiDetails()
                .value("undocumented")
                .name("undocumented")
                .description("Undocumented API")
                .type(clazz.getName());
            undocumented = true;
            return;
        }

        processApi(clazz);
        processOperations(clazz);

        // associate operations to their respective APIs
        api = Optional.ofNullable(api).orElseGet(() ->
            new ApiDetails()
                .value("uncategorized")
                .name("uncategorized")
                .description("Uncategorized API: " + clazz.getCanonicalName()));
        if(operations.size() > 0) {
            operations.forEach(o -> o.api(api.name()));
        }
    }

    @Override
    public ApiDetails api() {
        return api;
    }

    @Override
    public Set<ApiOperationDetails> operations() {
        return operations;
    }

    @Override
    public boolean undocumented() {
        return undocumented;
    }

    /**
     * True if class is undocumented but could potentially be an API implementation.
     *
     * @param clazz
     * @param interested
     * @return
     */
    private boolean checkPotentialUndocumented(Class<?> clazz, Set<String> interested) {
        // check class-level annotations
        boolean classInterested = interested.stream().anyMatch(type ->
            Arrays.stream(clazz.getAnnotations())
                .anyMatch(annotation -> annotation.annotationType().getCanonicalName().contains(type))
        );

        // method-level annotations
        boolean methodsInterested = interested.stream().anyMatch(type ->
            Arrays.stream(clazz.getMethods())
                .anyMatch(method ->
                    Arrays.stream(method.getAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().getCanonicalName().contains(type)))
        );

        // no api annotations but has potential class- and method-level annotations
//        System.out.println(String.format("%s - hasApiAnnotation: %s, hasApiOperationAnnotations: %s, classInterested: %s, methodsInterested: %s",
//            clazz.getCanonicalName(),
//            hasApiAnnotation(clazz),
//            hasApiOperationAnnotations(clazz),
//            classInterested,
//            methodsInterested));
        return !hasApiAnnotation(clazz) && !hasApiOperationAnnotations(clazz) && (classInterested || methodsInterested);
    }

    private boolean hasApiAnnotation(Class<?> clazz) {
        return Arrays.stream(clazz.getAnnotations())
            .anyMatch(annotation -> annotation.annotationType().getCanonicalName().equals(Api.class.getCanonicalName()));
    }

    private boolean hasApiOperationAnnotations(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods())
            .flatMap(method -> Arrays.stream(method.getAnnotations()))
            .anyMatch(annotation -> annotation.annotationType().getCanonicalName().equals(ApiOperation.class.getCanonicalName()));
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

                String url = "";
                try {
                    url = (String)annotation.annotationType().getMethod("url").invoke(annotation);
                } catch (Exception e) {}

                this.api = new ApiDetails()
                    .value(value)
                    .name(name)
                    .url(url)
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

                String url = "";
                try {
                    url = (String)annotation.annotationType().getMethod("url").invoke(annotation);
                } catch (Exception e) {}

                List<ApiParamDetails> params = new ArrayList<>();
                try {
                    Object[] o = (Object[])annotation.annotationType().getMethod("parameters").invoke(annotation);
                    params = Stream.of(o).map(p -> {
                        String name = "";
                        try {
                            name = (String) p.getClass().getMethod("name").invoke(p);
                        } catch (Exception e) {}

                        String location = "";
                        try {
                            location = (String) p.getClass().getMethod("location").invoke(p);
                        } catch (Exception e) {}

                        Boolean required = null;
                        try {
                            required = (Boolean) p.getClass().getMethod("required").invoke(p);
                        } catch (Exception e) {}

                        String paramUrl = "";
                        try {
                            paramUrl = (String) p.getClass().getMethod("url").invoke(p);
                        } catch (Exception e) {}

                        Class<?> type = String.class;
                        try {
                            type = (Class<?>) p.getClass().getMethod("type").invoke(p);
                        } catch (Exception e) {}

                        return new ApiParamDetails()
                            .location(location)
                            .url(paramUrl)
                            .name(name)
                            .required(required == null ? true :  required)
                            .type(type);
                    }).collect(Collectors.toList());
                } catch (Exception e) {}

                return new ApiOperationDetails(path)
                    .api(api)
                    .description(description)
                    .url(url)
                    .parameters(params)
                    .methods(Arrays.stream(methods).collect(Collectors.toSet()))
                    .produces(Arrays.stream(produces).collect(Collectors.toSet()))
                    .consumes(Arrays.stream(consumes).collect(Collectors.toSet()));
            }).forEach(operations::add);
    }
}
