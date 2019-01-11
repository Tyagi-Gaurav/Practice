package com.gradle

import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionGraph
import org.gradle.api.execution.TaskExecutionGraphListener

class ReleaseVersionListener implements TaskExecutionGraphListener {
    private String releaseTaskPath = "release"

    @Override
    void graphPopulated(TaskExecutionGraph taskExecutionGraph) {
        if (taskExecutionGraph.hasTask(releaseTaskPath)) {
            List<Task> allTasks = taskExecutionGraph.allTasks
            Task releaseTask = allTasks.find {it.path == releaseTaskPath}

            Project project = releaseTask.project

            if (!project.version.release) {
                project.version.release = true
                project.ant.propertyFile(file: project.versionFile) {
                    entry(key: "release", type: "string", operation: "=", value: "true")
                }
            }
        }

    }
}
