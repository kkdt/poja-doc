/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import groovy.json.JsonOutput
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.TaskAction
import poja.core.ApiDetails
import poja.core.PojaDocumentation

class PojaDocTask extends DefaultTask {

    SourceSetContainer sourceSetContainer

    @Input
    String builderType

    @Input
    def potentialImplementations

    @OutputDirectory
    def docOutputDir

    @TaskAction
    def pojadoc() {
        logger.info("PojaDocTask.docOutputDir = ${docOutputDir}")
        logger.info("PojaDocTask.builderType = ${builderType}")
        logger.info("PojaDocTask.potentialImplementations = ${potentialImplementations}")


        def jars = jars(sourceSetContainer)
        def classes = classes(sourceSetContainer)
        def loader = new URLClassLoader(jars.collect { it.toURI().toURL() } as URL[])
        def builders = classes.collect {
            process(loader.loadClass(it))
        } as Set

        def apis = builders.stream()
            .filter { it.api() != null && !(it.api().name().equals("undocumented") || it.api().name().equals("uncategorized"))}
            .collect { it.api() } as Set

        def operations = (builders.stream()
            .filter { !"uncategorized".equals(it.api().name) && it.operations().size() > 0 }
            .collect { it.operations() } as Set).flatten()

        def apimap = apis.collectEntries {
            [(it.name()): new Api().of(it)]
        }

        operations.each {
            apimap[it.api()].operations << new ApiOperation().of(it)
        }

        apimap.each {
            def f = new File(docOutputDir, "" +  it.key + ".json")
            logger.info("File {}", f)
            f.write(JsonOutput.prettyPrint(JsonOutput.toJson(it.value)))
        }

        def uncategorized = builders.stream()
            .filter { "uncategorized".equals(it.api().name) && it.operations().size() > 0 }
            .collect {
                def a = new Api().of(it.api())
                a.operations << it.operations().collect {
                    new ApiOperation().of(it)
                }
                a

            } as List
        def uncatfile = new File(docOutputDir, "uncategorized.json")
        logger.info("Uncategorized {}", uncatfile)
        uncatfile.write(JsonOutput.prettyPrint(JsonOutput.toJson(uncategorized)))

        def undocumented = builders.stream()
            .filter { it.undocumented() }
            .collect { it.api() }
            .collect { new Api().of(it) } as List
        def undocfile = new File(docOutputDir, "undocumented.json")
        logger.info("Undocumented {}", undocfile)
        undocfile.write(JsonOutput.prettyPrint(JsonOutput.toJson(undocumented)))
    }

    def process(Class<?> clazz) {
        PojaDocumentation builder = (PojaDocumentation)Class.forName(builderType).newInstance()
        builder.process(clazz, potentialImplementations)
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
