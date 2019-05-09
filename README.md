# Jar-Check

This plugin ensures that the content of your built Jar artifacts do not change on accident.
Jar checking consists of a content check and a manifest classpath check.
Checks are ordering agnostic.

### Tasks

For a jar named `myArchive.jar`, this plugin produces three tasks for each check:

* `examineMyArchiveContent`
* `checkMyArchiveContent`
* `updateMyArchiveExpectedContent`


* `examineMyArchiveManifestClasspath`
* `checkMyArchiveManifestClasspath`
* `updateMyArchiveExpectedManifestClasspath`

The `examine[...]` task extracts the values of interest, for use by other tasks.  
Each `check[...]` task compares this extracted value against the expectation file.  
Upon review and approval, the `update[...]` task can be used to copy the values extracted by the `examine[...]` task to the expectation file. 

Additionally, the plugin provides the following tasks as a way to invoke all other `check[...]` and `update[...]` tasks:

* `jarCheckAll`
* `jarCheckUpdateAll`

### Configuration

The plugin can be additionally configured explicitly as follows:

```

plugin {
  id 'jar-check'
}

// ...

tasks.register("myJarTask", Jar) {
  // Configuration
  // ...
}

tasks.register("myOtherJarTask", Jar) {
  // Configuration
  // ...
}

jarCheck {
  // Should these checks be run with the ':check' task?
  // Default value: true
  runWithCheck false

  // Specify a directory that contains the "golden" expectation files.
  // Defaults value: "${projectDir}/src/build-resources/jarCheckExpectations/"
  expectationDir "${projectDir}/my/preferred/location

  // Specify a jar on disk by path or File object
  check "/path/to/some/jar/file"
  check file("/path/to/some/jar/file")
  
  // Create check tasks for every task in this project of type Jar
  checkAllJarTasks()

  // For a specific Jar, only check the manifest classpath.
  check tasks.named("myJarTask") {
    // Default behavior is to run both tpes of check task.
    checkContent = false
    checkManifestClasspath = true
  }
}
```

If no configuration is provided, behavior is equivalent to the following:

```
jarCheck {
  checkAllJarTasks()
  runWithCheck true
  expectationDir "${projectDir}/src/build-resources/jarCheckExpectations/"
}
```
