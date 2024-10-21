import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `maven-publish`
    `java-library`
    alias(libs.plugins.jetbrainsKotlinJvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(tegralLibs.niwen.lexer)
    implementation(tegralLibs.niwen.parser)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
    implementation("com.google.guava:guava:31.0.1-jre")
    implementation("org.bouncycastle:bcpkix-jdk15on:1.67")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-cbor:2.13.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("com.squareup.retrofit2:converter-jackson:2.7.2")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.10")

    testImplementation("io.mockk:mockk:1.13.12")
    testImplementation(libs.bundles.junit.test)
    testImplementation(libs.bundles.kotlinx.test)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = "com.bity"
            artifactId = "icp_kotlin_kit"
            version = "1.0.1"
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
        languageVersion = "1.9"
    }
}