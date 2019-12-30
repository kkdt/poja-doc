/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import groovy.json.JsonOutput
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import poja.core.ApiBuilder
import poja.core.PojaDocumentBuilder

class PojaDocTask extends DefaultTask {

    SourceSetContainer sourceSetContainer
    String builderType

    @OutputDirectory
    File outputDirectory = project.file("${project.buildDir}/pojadoc")

    @TaskAction
    def pojadoc() {
        def jars = jars(sourceSetContainer)
        def classes = classes(sourceSetContainer)
        def loader = new URLClassLoader(jars.collect { it.toURI().toURL() } as URL[])
        def builders = classes.collect {
            process(loader.loadClass(it))
        } as Set

        def apis = builders.stream()
            .filter { it.api() != null }
            .collect { it.api() } as Set
        apis << ApiBuilder.UncatApi

        def operations = (builders.stream()
            .filter { it.operations().size() > 0 }
            .collect { it.operations() } as Set).flatten()

        def apimap = apis.collectEntries {
            [(it.name()): new Api().of(it)]
        }

        operations.each {
            apimap[it.api()].operations << new ApiOperation().of(it)
        }

        apimap.each {
            def f = new File("" + outputDirectory.absolutePath
                + File.separator
                + it.key + ".json")
            logger.info("File {}", f)
            f.write(JsonOutput.prettyPrint(JsonOutput.toJson(it.value)))
        }

    }

    def process(Class<?> clazz) {
        PojaDocumentBuilder builder = (PojaDocumentBuilder)Class.forName(builderType).newInstance()
        builder.process(clazz)
        logger.info("{}, API: {}, Operation count: {}", clazz, builder.api(), builder.operations().size())
        builder
    }

    def jars(SourceSetContainer sourceSets) {
        def jars = []
        sourceSets.main.runtimeClasspath.files.findAll {
            it.isFile() && it.name.endsWith(".jar")
        }.each {
            jars << it
        }
        jars << project.getTasksByName("jar", false).archivePath[0]
        jars
    }

    def classes(SourceSetContainer sourceSets) {
        def classes = []
        sourceSets.main.runtimeClasspath.files.findAll {
            it.isDirectory()
        }.each {
            it.eachFileRecurse(groovy.io.FileType.FILES) { f ->
                if(f.name.endsWith('.class')) {
                    classes << f.absolutePath.replace("${it.absolutePath}${File.separator}", "")
                        .replace(File.separator, ".")
                        .replace(".class","")
                }
            }
        }
        classes
    }

}
