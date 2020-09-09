plugins {
    java
    application
    kotlin("jvm") version "1.3.70"
}

group = "pl.pjagielski"
version = "0.1.0-SNAPSHOT"

application {
    mainClassName = "pl.pjagielski.MainKt"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://dl.bintray.com/punkt/punkt")
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("pl.pjagielski", "punkt", "0.3.0")
    runtimeOnly("org.slf4j","slf4j-simple","1.7.29")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
