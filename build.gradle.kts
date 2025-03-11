import org.jreleaser.gradle.plugin.JReleaserExtension
import org.jreleaser.model.Active
import org.jreleaser.model.api.deploy.maven.MavenCentralMavenDeployer.Stage
import org.jreleaser.model.api.signing.Signing

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("org.jreleaser") version "1.14.0"
}

apply(plugin = "java")
apply(plugin = "java-library")
apply(plugin = "maven-publish")
apply(plugin = "signing")

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


java {
    withSourcesJar()
    withJavadocJar()

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

tasks.test {
    useJUnitPlatform()
}

extensions.configure<JReleaserExtension>("jreleaser") {
    project {
        group = "dev.ultreon"
        authors.set(listOf("XyperCode"))
        license = "Apache-2.0"
        description = "Extensible NBT-like data API."
        copyright.set("(C) Copyright 2023 Quinten Jungblut. All rights reserved.")
        links {
            homepage = "https://ultreon.dev"
        }
        inceptionYear = "2022"
    }

    gitRootSearch = true

    release {
        gitlab {
            branch.set("main")
        }
    }

    signing {
        active.set(Active.ALWAYS)
        armored = true
    }

    deploy {
        maven {
            this@maven.active.set(Active.ALWAYS)

            mavenCentral {
                create("sonatype") {
                    active.set(Active.ALWAYS)
                    url.set("https://central.sonatype.com/api/v1/publisher")
                    stagingRepository(projectDir.path + "/build/staging-deploy")
                }
            }
        }
    }
}

publishing {
    repositories {
        maven {
            mkdir("build/staging-deploy")
            name = "Staging"
            url = uri("file://${projectDir.path.replace("\\", "/")}/build/staging-deploy")
        }

        maven {
            name = "UltreonMavenReleases"
            url = uri("https://maven.ultreon.dev/releases")
            credentials {
                username = (findProperty("ultreonmvn.name") ?: System.getenv("ULTREON_MVN_NAME")).toString()
                password = (findProperty("ultreonmvn.secret") ?: System.getenv("ULTREON_MVN_SEC")).toString()
            }
        }

        maven {
            name = "UltreonMavenSnapshots"
            url = uri("https://maven.ultreon.dev/snapshots")
            credentials {
                username = (findProperty("ultreonmvn.name") ?: System.getenv("ULTREON_MVN_NAME")).toString()
                password = (findProperty("ultreonmvn.secret") ?: System.getenv("ULTREON_MVN_SEC")).toString()
            }
        }
    }

    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])

            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            pom {
                name.set("UBO")
                description.set("Extensible NBT-like data API.")

                url.set("https://github.com/Ultreon/ubo")
                inceptionYear.set("2022")

                developers {
                    developer {
                        name.set("XyperCode")
                        email.set("xyppercode@ultreon.dev")

                        organization.set("Ultreon")
                        organizationUrl.set("https://github.com/Ultreon")
                    }
                }

                organization {
                    name.set("Ultreon")
                    url.set("https://github.com/Ultreon")
                }

                issueManagement {
                    system.set("GitHub")
                    url.set("https://github.com/Ultreon/ubo/issues")
                }

                licenses {
                    license {
                        name.set("Apache License")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/Ultreon/ubo.git")
                    developerConnection.set("scm:git:ssh://github.com/Ultreon/ubo.git")

                    url.set("https://github.com/Ultreon/ubo/tree/main")
                }

                contributors {
                    contributor {
                        name.set("XyperCode")
                        url.set("https://github.com/XyperCode")
                    }

                    contributor {
                        name.set("AndEditor7")
                        url.set("https://github.com/AndEditor7")
                    }
                }
            }
        }
    }

    repositories {
        maven {
            url = uri("https://gitlab.com/api/v4/projects/60101946/packages/maven")
            credentials(HttpHeaderCredentials::class) {
                name = "Private-Token"
                value =
                    findProperty("gitLabPrivateToken") as String? // the variable resides in $GRADLE_USER_HOME/gradle.properties
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
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
