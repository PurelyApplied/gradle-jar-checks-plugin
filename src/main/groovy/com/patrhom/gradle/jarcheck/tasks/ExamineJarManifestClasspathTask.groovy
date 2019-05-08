package com.patrhom.gradle.jarcheck.tasks

import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

import java.util.jar.Manifest

class ExamineJarManifestClasspathTask extends JarExaminationTasks {

  ExamineJarManifestClasspathTask() {
    setGroup('verification')
    setDescription("Extract jar manifest to file, for use by check task against some expectation.")
  }


  Set<String> extractClasspathFromManifest(File manifestPath) {
    if (!manifestPath.exists()) {
      logger.debug("Expected manifest '${manifestPath.absolutePath}' not found.  Using empty classpath.")
      return [] as Set<String>
    }

    def manifest = new Manifest(manifestPath.newInputStream())
    def classpathValue = manifest.getMainAttributes().getValue('Class-Path')
    if (classpathValue == null) {
      logger.debug("Manifest file did not contain a Class-Path.  Using empty classpath.")
      return [] as Set<String>
    }
    return classpathValue.split() as Set<String>
  }

  @TaskAction
  def getJarManifest() {
    logger.info "Examining manifest of ${jarFile}"

    // Check the manifest of the jar
    FileTree jarTree = project.zipTree(jarFile)
    Object manifest = jarTree.find { it.path.endsWith('/META-INF/MANIFEST.MF') }
    if (manifest == null) {
      println "Jar file ${jarFile} does not appear to have a manifest.  Using empty classpath."
      outputFile.write("")
      return
    }

    File manifestFile = project.file(manifest)
    outputFile.write(extractClasspathFromManifest(manifestFile).sort().join("\n"))
  }
}
