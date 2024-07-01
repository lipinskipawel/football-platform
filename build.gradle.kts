plugins {
    `java-platform`
    `maven-publish`
    signing
    id("io.github.gradle-nexus.publish-plugin") version "1.3.0"
}

dependencies {
    constraints {
        api("org.junit.jupiter:junit-jupiter-api:5.7.0")
        api("org.junit.jupiter:junit-jupiter-params:5.7.0")
        api("org.junit.jupiter:junit-jupiter-engine:5.7.0")
        api("org.assertj:assertj-core:3.20.2")
    }
}

group = "com.github.lipinskipawel"
version = "1.1.0"
description = "BOM for football related projects"

nexusPublishing {
    repositories {
        sonatype()
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
