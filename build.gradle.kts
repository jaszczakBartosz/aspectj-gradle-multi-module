import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.freefair.gradle.plugins.aspectj.AjcAction

plugins {
    id("io.spring.dependency-management") version "1.1.3"
    id("io.freefair.aspectj.post-compile-weaving") version "8.3"
    kotlin("jvm") version "1.9.0"
}

apply{
    plugin("io.freefair.aspectj.post-compile-weaving")
}

repositories {
    mavenCentral()
}

subprojects {
    apply {
        plugin("java")
        plugin("kotlin")
        plugin("io.freefair.aspectj.post-compile-weaving")
    }

    kotlin {
        jvmToolchain(19)
    }

    dependencies {
        val implementation by configurations
        val runtimeOnly by configurations
        val aspect by configurations

        implementation("org.aspectj:aspectjrt:1.9.20")
        implementation("org.aspectj:aspectjweaver:1.9.20")
        aspect("org.springframework.security:spring-security-aspects:5.8.5")
        runtimeOnly("org.springframework.security:spring-security-aspects:5.8.5")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    repositories {
        mavenLocal()
        mavenCentral()
    }

    tasks.named<KotlinCompile>("compileTestKotlin") {
        configure<AjcAction> {
            options.aspectpath.from(sourceSets["main"].output)
            options.compilerArgs.addAll(listOf("-showWeaveInfo", "-verbose", "-warn:none", "-Xlint:ignore"))
        }
    }

    tasks.named<KotlinCompile>("compileKotlin") {
        configure<AjcAction> {
            options.compilerArgs.addAll(listOf("-showWeaveInfo", "-verbose", "-warn:none", "-Xlint:ignore"))
        }
    }
}

allprojects {
    apply {
        plugin("io.spring.dependency-management")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.1.3")
        }
    }
}

group = "com.bartoszjaszczak"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_19
}