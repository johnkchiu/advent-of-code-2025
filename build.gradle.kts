plugins {
    java
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.10.2")
}

application {
    mainClass.set("aoc.Runner")
}

tasks.test {
    useJUnitPlatform()
}

// Convenience: copy input files into resources on build
sourceSets {
    named("main") {
        java {
            srcDir("src/main/java")
        }
        resources {
            srcDir("inputs")
        }
    }
    named("test") {
        java {
            srcDir("src/test/java")
        }
        resources {
            srcDir("src/test/resources")
        }
    }
}
