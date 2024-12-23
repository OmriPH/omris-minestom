plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.0"
}

group = "me.omrih"
version = "1.1"

repositories {
    mavenCentral()
    maven ("https://jitpack.io")
}

dependencies {
    implementation("net.minestom:minestom-snapshots:0ca1dda2fe")
    implementation("org.slf4j:slf4j-simple:2.0.16")
    implementation("com.github.TogAr2:MinestomPvP:04180ddf9a")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21)) // Minestom has a minimum Java version of 21
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "me.omrih.Main" // Change this to your main class
        }
    }

    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("") // Prevent the -all suffix on the shadowjar file.
    }
}