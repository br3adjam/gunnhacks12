group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    mavenCentral()
}


application {
    mainClass.set("keyboard.Keyboard")
}

dependencies {
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
}

tasks.shadowJar {
    archiveBaseName.set("keyboard-app")
    manifest {
        attributes["Main-Class"] = "keyboard.Keyboard"
    }
    mergeServiceFiles()
}
