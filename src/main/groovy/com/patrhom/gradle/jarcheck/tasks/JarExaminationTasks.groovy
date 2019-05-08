package com.patrhom.gradle.jarcheck.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskProvider

abstract class JarExaminationTasks extends DefaultTask {
  @InputFile
  File jarFile

  @OutputFile
  File outputFile


  void checks(TaskProvider<Task> taskProvider) {
    checks(taskProvider.get())
  }

  void checks(Task t) {
    dependsOn t
    checks(t.outputs.files.singleFile)
  }

  void checks(File f) {
    jarFile = f
  }
}
