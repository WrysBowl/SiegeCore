subprojects {
    buildscript {
        repositories {
            jcenter()
        }
        dependencies {
            classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
        }
    }
}