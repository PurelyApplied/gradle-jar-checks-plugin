package com.patrhom.gradle.jarcheck

import org.gradle.api.Task

class JarCheckConfiguration {
  boolean checkContent = true
  boolean checkManifestClasspath = true
  Task jarCreator = null
}


