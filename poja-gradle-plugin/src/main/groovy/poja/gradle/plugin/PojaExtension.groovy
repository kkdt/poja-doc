/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import org.gradle.api.Project
import org.gradle.api.provider.Property
import poja.core.DefaultDocumentBuilder

class PojaExtension {
    Property<String> builderType

    PojaExtension(Project project) {
        builderType = project.getObjects().property(String.class)
        builderType.set(DefaultDocumentBuilder.class.getName())
    }
}
