/*
 * Copyright (c) 2019. thinh ho
 * This file is part of 'poja-gradle-plugin' which is released under the MIT license.
 * See LICENSE at the project root directory.
 */

package poja.gradle.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSetContainer
import poja.core.DefaultDocumentBuilder

class PojaDocPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        PojaExtension poja = project.getExtensions().create("poja", PojaExtension.class, project)

//        project.afterEvaluate {
//
//        }

        project.plugins.withType(JavaPlugin.class, { java ->
            JavaPluginConvention convention = project.getConvention().getPlugin(JavaPluginConvention.class)
            SourceSetContainer sourceSetContainer = convention.getSourceSets()

            project.tasks.create("pojadoc", PojaDocTask.class, {
                def builderType = poja.builderType.get();
                if(!builderType) builderType = DefaultDocumentBuilder.class.getName()

                it.sourceSetContainer = sourceSetContainer
                it.builderType = builderType
                it.description = "Generates POJA Documentation."
                it.setGroup("Documentation")
                it.dependsOn(sourceSetContainer.main.getOutput().getDirs())
                it.dependsOn(sourceSetContainer.main.getCompileJavaTaskName())
                it.dependsOn(sourceSetContainer.main.getProcessResourcesTaskName())
                it.dependsOn(java.JAR_TASK_NAME)
            })
        })
    }

    def jars(Project project, SourceSetContainer sourceSets) {
        def jars = []
        sourceSets.main.runtimeClasspath.files.findAll {
            it.isFile() && it.name.endsWith(".jar")
        }.each {
            jars << it
        }
        jars << project.getTasksByName("jar", false).archivePath
        jars
    }

    def classes(Project project, SourceSetContainer sourceSets) {
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