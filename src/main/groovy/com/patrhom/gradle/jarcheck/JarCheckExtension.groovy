package com.patrhom.gradle.jarcheck

import org.gradle.api.Project
import org.gradle.api.tasks.bundling.Jar

class JarCheckExtension {
  boolean runWithCheck = true
  boolean implicitlyCheckAll = true // disabled if any explicit configuration is provided.
  Object expectationsDir = null

  final Project project

  Map<Object, JarCheckConfiguration> jarsToCheck = [:]

  private static final Closure<JarCheckConfiguration> defaultCheckConfiguration = ({
    checkContent = true
    checkManifestClasspath = true
  } as Closure<JarCheckConfiguration>)

  JarCheckExtension(Project project) {
    this.project = project
  }

  void checkAllJarTasks() {
    project.tasks.withType(Jar).each { jarTask ->
      check(jarTask)
    }
  }

  void check(Object checkObj) {
    check(checkObj, defaultCheckConfiguration)
  }

  void check(Object checkObj, Closure<JarCheckConfiguration> configClosure) {
    implicitlyCheckAll = false

    JarCheckConfiguration config = project.configure(new JarCheckConfiguration(), configClosure) as JarCheckConfiguration
    jarsToCheck.put(checkObj, config)
  }
}
