plugins {
    id("java")
    application
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.br3adjam.keyboard"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kwhat:jnativehook:2.2.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.1")
}

application {
    mainClass.set("keyboard.Keyboard")
}

tasks.test {
    useJUnitPlatform()
}