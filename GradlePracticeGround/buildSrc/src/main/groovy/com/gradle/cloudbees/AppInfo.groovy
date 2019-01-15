package com.gradle.cloudbees

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class AppInfo extends DefaultTask {
    @Input String apiUrl
    @Input String apiKey
    @Input String apiSecret
    @Input String apiFormat
    @Input String apiVersion
    @Input String appId

    AppInfo() {
        description = "Returns basic information about an application"
        group = "Cloudbees"
    }

    @TaskAction
    void start() {
        logger.quiet("ApplicationId : myId")
        logger.quiet("title : title")
        logger.quiet("apiKey : $apiKey")
        logger.quiet("secret : $apiSecret")
        logger.quiet("apiFormat : $apiFormat")
        logger.quiet("apiVersion : $apiVersion")
        logger.quiet("appId : $appId")
    }
}
