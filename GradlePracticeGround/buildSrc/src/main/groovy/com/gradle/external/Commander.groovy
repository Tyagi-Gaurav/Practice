package com.gradle.external

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class Commander extends DefaultTask {
    @Input
    List<String> commands

    @Input
    File dir

    @TaskAction
    void runCommand() {
        logger.info("Executing commands")
        def process = commands.execute(null, dir)

        process.consumeProcessOutput(System.out, System.err)
        process.waitFor()

        if (process.exitValue() != 0) {
            throw new GradleException()
        }
    }
}
