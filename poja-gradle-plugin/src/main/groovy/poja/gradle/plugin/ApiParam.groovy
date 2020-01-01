/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

/**
 * Paramter can be located in the URL path, the request body, or as header attribute.
 */
class ApiParam {
    String name
    String location
    String type
    String url
    boolean required = true
}
