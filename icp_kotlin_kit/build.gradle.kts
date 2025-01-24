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
    implementation(libs.bundles.http)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.guava)
    implementation(libs.bouncycastle)
    implementation(libs.logging.interceptor)

    testImplementation(libs.mockk)
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