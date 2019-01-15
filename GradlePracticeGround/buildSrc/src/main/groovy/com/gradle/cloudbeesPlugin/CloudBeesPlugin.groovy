package com.gradle.cloudbeesPlugin

import com.gradle.cloudbees.AppInfo
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.WarPlugin

class CloudBeesPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        project.plugins.apply(WarPlugin)
        addTasks(project)
    }

    private void addTasks(Project project) {
        project.tasks.withType(AppInfo) {
            //For all tasks of type AppInfo, provide following properties.
            apiUrl = 'https://api.cloudbees.com/api'
            apiKey = project.property('apikey')
            apiSecret = project.property('apiSecret')
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
