/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import poja.core.ApiOperationDetails

class ApiOperation {
    String path
    String description
    Set<String> consumes
    Set<String> produces
    Set<String> methods
    String url
    List<ApiParam> parameters = new ArrayList()

    ApiOperation of(ApiOperationDetails details) {
        this.path = details.path()
        this.description = details.description()
        this.consumes = details.consumes().size() > 0 ? details.consumes() : null
        this.produces = details.produces().size() > 0 ? details.produces() : null
        this.methods = details.methods().size() > 0 ? details.methods() : null
        this.url = details.url()
        if(details.parameters().size() > 0) {
            System.out.println(String.format("details.parameters() - %s", details.parameters().size()))
            details.parameters().eachWithIndex { it, i ->
                ApiParam a = new ApiParam()
                a.name = it.name()
                a.url = it.url()
                a.location = it.location()
                a.required = it.required()
                a.type = it.type().name
                System.out.println(String.format("parameter[%s], %s", i, a.class.name))
                this.parameters.add(a)
            }
        }

        return this
    }
}
