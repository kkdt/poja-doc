/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import poja.core.ApiOperationBuilder

class ApiOperation {
    String path
    String description
    Set<String> consumes
    Set<String> produces
    Set<String> methods

    ApiOperation of(ApiOperationBuilder builder) {
        this.path = builder.path()
        this.description = builder.description()
        this.consumes = builder.consumes().size() > 0 ? builder.consumes() : null
        this.produces = builder.produces().size() > 0 ? builder.produces() : null
        this.methods = builder.methods().size() > 0 ? builder.methods() : null
        return this
    }
}
