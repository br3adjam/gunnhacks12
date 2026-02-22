group = "org.example"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.kwhat:jnativehook:2.2.2")
}