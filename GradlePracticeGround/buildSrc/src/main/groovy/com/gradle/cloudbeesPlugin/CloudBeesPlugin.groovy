package com.gradle.cloudbeesPlugin

import com.gradle.cloudbees.AppInfo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin

class CloudBeesPlugin implements Plugin<Project> {
    static final String EXTENSION_NAME = "cloudbees"

    @Override
    void apply(Project project) {
        project.plugins.apply(WarPlugin)
        //Register the extension container with the name cloudbees
        project.extensions.create(EXTENSION_NAME, CloudBeesPluginExtension)
        addTasks(project)
    }

    private void addTasks(Project project) {
        project.tasks.withType(AppInfo) {
            //For all tasks of type AppInfo, provide following properties.
            def extension = project.extensions.findByName(EXTENSION_NAME)
            //Lookup extension values from extension container
            conventionMapping.apiUrl = {extension.apiUrl}
            conventionMapping.apiKey = {extension.apiKey}
            conventionMapping.apiSecret = {extension.apiSecret}
        }

        addAppTasks(project)
    }

    private void addAppTasks(Project project) {
        project.task("cloudBeesAppDeployWar", type: AppInfo) {
            appId = project.hasProperty('appId') ? project.appId : null
            //message = project.hasProperty('message') ? project.message : null
        }
    }
}
