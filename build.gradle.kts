plugins {
    `java-platform`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

dependencies {
    constraints {
        api(libs.json.jackson.databind)
        api(libs.json.jackson.databind.jdk8)
        api(libs.json.jackson.databind.jsr310)

        api(libs.testing.junit.api)
        api(libs.testing.junit.params)
        api(libs.testing.junit.engine)
        api(libs.testing.assertj)
    }
}

group = "com.github.lipinskipawel"
version = "1.3.0"
description = "BOM for football related projects"

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://ossrh-staging-api.central.sonatype.com/service/local/"))
            snapshotRepositoryUrl.set(uri("https://central.sonatype.com/repository/maven-snapshots/"))
        }
    }
}

publishing {
    publications {
        create<MavenPublication>("footballPlatform") {
            from(components["javaPlatform"])

            pom {
                name.set("football-platform")
                description.set("Platform for all football projects")
                url.set("https://github.com/lipinskipawel/football-platform")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("http://www.opensource.org/licenses/mit-license.php")
                    }
                }
                developers {
                    developer {
                        name.set("Paweł Lipiński")
                    }
                }
                scm {
                    connection.set("scm:git:ssh://git@github.com:lipinskipawel/football-platform.git")
                    developerConnection.set("scm:git:ssh://git@github.com:lipinskipawel/football-platform.git")
                    url.set("git@github.com:lipinskipawel/football-platform.git")
                }
            }
        }
    }
}

signing {
    val signingKeyId = findProperty("signingKeyId").toString()
    val signingKey = findProperty("signingKey").toString()
    val signingPassword = findProperty("signingPassword").toString()
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications["footballPlatform"])
}
