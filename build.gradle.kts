plugins {
    id("java")
    id("java-library")
    id("maven-publish")

}

apply(plugin="java")
apply(plugin="java-library")
apply(plugin="maven-publish")
apply(plugin="signing")

group = project.property("group")!!
version = "${project.property("version")}"

base {
    archivesName.set(project.property("archivesBaseName").toString())
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    compileOnly("org.jetbrains:annotations:23.0.0")
}

//tasks.compileJava {
//    sourceCompatibility = JavaVersion("1.8")
//    targetCompatibility = JavaVersion("1.8")
//}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${rootProject.projectDir}/.mvnrepo")
        }

        val ossrhUsername = findProperty("ossrh.username") ?: System.getenv("OSSRH_USERNAME")
        val ossrhPassword = findProperty("ossrh.password") ?: System.getenv("OSSRH_PASSWORD")

        maven {
            name = "OssSonatype"
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                if (ossrhUsername != null && ossrhPassword != null) {
                    username = ossrhUsername.toString()
                    password = ossrhPassword.toString()
                }
            }
        }

        maven {
            name = "OssSnapshots"
            url = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            credentials {
                if (ossrhUsername != null && ossrhPassword != null) {
                    username = ossrhUsername.toString()
                    password = ossrhPassword.toString()
                }
            }
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.publish.get().dependsOn(tasks.build)

tasks.withType<GenerateModuleMetadata> {
    enabled = false
}

//tasks.signing {
//    sign(configurations["archives"])
//}

afterEvaluate {
    tasks.javadoc {
//        source(sourceSets.main.allJava.sourceDirectories)
//        title = "Ultreon Data API (UBO/USO)"
//        description = "Extensible NBT-like data API."
//        setDestinationDir(file("$rootProject.projectDir/build/docs/javadoc"))
//        // Configure the classpath
//        classpath = files(sourceSets.main.compileClasspath)
//        (options as StandardJavadocDocletOptions).links(
//                // Kinda empty in here lmao.
//        )
    }
}
