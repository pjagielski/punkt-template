plugins {
    java
    application
    kotlin("jvm") version "1.4.32"
}

group = "pl.pjagielski"
version = "0.1.0-SNAPSHOT"

application {
    mainClassName = "pl.pjagielski.MainKt"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("com.github.pjagielski", "punkt", "master-SNAPSHOT") {
        exclude("org.slf4j", "slf4j-api")
        exclude("org.slf4j", "slf4j-log4j12")
    }
    runtimeOnly("org.slf4j","slf4j-simple","1.7.29")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}
