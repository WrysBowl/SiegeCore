buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:5.2.0")
    }
}

plugins {
    id("com.github.johnrengelman.shadow") version "5.2.0"
    java
}

group = "net.siegemc"
version = "1.0-SNAPSHOT"

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven(url = "https://oss.sonatype.org/content/groups/public/")
    maven(url = "https://repo.aikar.co/content/groups/aikar/")
    maven(url = "https://repo.codemc.org/repository/maven-public/" )
    maven(url = "https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    testCompile("junit:junit:4.12")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.3-R0.1-SNAPSHOT")
    compile("co.aikar:acf-paper:0.5.0-SNAPSHOT")
    compile("com.github.stefvanschie.inventoryframework:IF:0.5.19")
    implementation("net.kyori:adventure-text-minimessage:3.0.0-SNAPSHOT")
    implementation("net.kyori:adventure-platform-bukkit:4.0.0-SNAPSHOT")
    //compile(fileTree(include(["*.jar"]), dir("libs")))
}
tasks {
    shadowJar {
        relocate("co.aikar.commands", "net.siegemc.core.acf")
        relocate("co.aikar.locales", "net.siegemc.core.locales")
        relocate("com.github.stefvanschie.inventoryframework", "net.siegemc.core.inventoryframework")
        doFirst {
            exclude("fonts/*.csv")
        }
        sequenceOf("net.kyori.adventure", "net.kyori.examination").forEach {
            relocate(it, "net.kyori.adventure.test.paper.ext.$it") {
                exclude("net.kyori.adventure.test.*")
            }
        }
        dependencies {
            exclude(dependency("com.google.code.gson:.*"))
            exclude(dependency("org.checkerframework:.*"))
        }
    }
    build {
        dependsOn(shadowJar)
    }
}
