plugins {
    kotlin("jvm") version "2.0.0"
}

tasks.wrapper {
    gradleVersion = "8.8"
}

allprojects {
    repositories {
        mavenCentral()
    }
    apply(plugin = "org.jetbrains.kotlin.jvm")

    group = "org.gnu"
    version = "1.0-SNAPSHOT"

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        testImplementation(kotlin("test"))
        testImplementation("org.assertj:assertj-core:3.26.3")
        testImplementation("org.junit.jupiter:junit-jupiter-params:5.10.0")
    }

    tasks.test {
        useJUnitPlatform()
    }
    kotlin {
        jvmToolchain(20)
    }
}
