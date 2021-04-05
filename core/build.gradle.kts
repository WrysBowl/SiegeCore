import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.4.21"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group "net.siegemc"
version "0.0.2"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    maven { url = uri("https://nexus.mcdevs.us/repository/mcdevs/") }
    maven { url = uri("https://repo.aikar.co/content/groups/aikar/") }
    maven { url = uri("https://repo.codemc.org/repository/maven-public/") }
    maven { url = uri("https://papermc.io/repo/repository/maven-public/") }
    maven { url = uri("https://mvn.lumine.io/repository/maven-public/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.dmulloy2.net/nexus/repository/public/") }
    maven { url = uri("https://dl.bintray.com/ichbinjoe/public/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
}

dependencies {
    implementation("us.mcdevs.library.kotlin:Kotlin:1.4.0")
    compileOnly("com.destroystokyo.paper:paper-api:1.16.5-R0.1-SNAPSHOT")
    testImplementation("junit:junit:4.12")
    compileOnly("org.projectlombok:lombok:1.18.16")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.5.0")
    compileOnly("io.lumine.xikage:MythicMobs:4.11.2") // MythicMobs API
    compileOnly("com.vexsoftware:nuvotifier-universal:2.6.0") // NuVotifier API
    compileOnly("com.github.MilkBowl:VaultAPI:1.7") // Vault API
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.1-SNAPSHOT")
    implementation("co.aikar:acf-paper:0.5.0-SNAPSHOT")
    implementation("com.github.stefvanschie.inventoryframework:IF:0.9.0")
    implementation("de.tr7zw:item-nbt-api-plugin:2.7.1")
    implementation("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
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
        dependencies {
            exclude(dependency("com.google.code.gson:.*"))
            exclude(dependency("org.checkerframework:.*"))
            include(dependency("net.kyori:adventure-text-minimessage:4.1.0-SNAPSHOT"))

        }
    }
    build {
        dependsOn(shadowJar)
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }
}