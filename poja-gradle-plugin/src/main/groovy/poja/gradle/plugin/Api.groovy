/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import poja.core.ApiBuilder

class Api {
    String name
    String value
    String description
    Set<ApiOperation> operations

    Api of(ApiBuilder builder) {
        name = builder.name()
        value = builder.value()
        description = builder.description()
        operations = new HashSet<>()
        return this
    }
}
