import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.2" apply false
    id("io.spring.dependency-management") version "1.1.2" apply false
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22" apply false
}

allprojects{
    group = "com.sim"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile>{
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "17"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects{
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "kotlin-spring")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
        implementation("org.springframework.boot:spring-boot-starter-mustache")
        implementation("org.springframework.boot:spring-boot-starter-actuator")

        implementation("io.projectreactor:reactor-tools")


        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-rx3")
        implementation("io.smallrye.reactive:mutiny:1.6.0")
        implementation("io.smallrye.reactive:mutiny-kotlin:2.0.0")


        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("io.projectreactor:reactor-test")
    }

}
