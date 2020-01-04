/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import poja.core.ApiDetails

class Api {
    String name
    String value
    String description
    Set<ApiOperation> operations
    String url
    String type

    Api(ApiDetails details) {
        name = details.name()
        value = details.value()
        description = details.description()
        operations = new HashSet<>()
        url = details.url()
        type = details.type()
    }
}
