# AspectJ Bytecode Weaving in a Multi-Module Gradle Project

This project demonstrates how to set up AspectJ bytecode weaving in a multi-module/library setup using Gradle.

## Project Structure

The project consists of the following modules:
- `library`
    - `account-service`
    - `logger`
    - `security`
- `application`
    - `api-app`

## Prerequisites

Ensure you have the following installed:
- JDK 11 or higher
- Gradle 7.0 or higher

## Setup

### 1. Apply AspectJ Plugin

Add the AspectJ plugin to your `build.gradle.kts` files.

#### Root `build.gradle.kts`

```kotlin
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "io.freefair.aspectj.post-compile-weaving")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.aspectj:aspectjrt:1.9.7")
    }
}
```

### 2. Configure Submodules

Ensure each submodule has the necessary configurations.

#### `library/account-service/build.gradle.kts`

```kotlin
plugins {
    id("java-library")
}

group = "com.bartoszjaszczak"
version = "0.0.1-SNAPSHOT"

dependencies {
    aspect(project(":library:security"))
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(kotlin("stdlib-jdk8"))
}

kotlin {
    jvmToolchain(19)
}
```

#### `application/api-app/build.gradle.kts`

```kotlin
plugins {
    id("org.springframework.boot") version "3.1.3"
    kotlin("plugin.spring") version "1.9.0"
}

group = "com.bartoszjaszczak"
version = "0.0.1-SNAPSHOT"

dependencies {
    aspect(project(":library:logger"))
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":library:account-service"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
```

### 3. Define Aspects

Create your AspectJ aspects in the appropriate module.

#### `library/logger/src/main/kotlin/com/bartoszjaszczak/logger/LoggerAspect.kt`

```kotlin
package com.bartoszjaszczak.logger

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.util.StopWatch

@Aspect
class LoggerAspect {

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Around("@annotation(com.bartoszjaszczak.logger.Log)")
    fun logExecutionTime(joinPoint: ProceedingJoinPoint): Any? {
        val stopWatch = StopWatch()
        stopWatch.start()
        val proceed = joinPoint.proceed()
        stopWatch.stop()
        logger.info("${joinPoint.signature} executed in ${stopWatch.totalTimeMillis} ms")
        return proceed
    }
}
```

#### `library/security/src/main/kotlin/com/bartoszjaszczak/security/user/UserInfoAspect.kt`

```kotlin
package com.bartoszjaszczak.security.user

import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

@Aspect
class UserInfoAspect {

    @Autowired
    private lateinit var userProvider: UserProvider

    private val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Before("@annotation(com.bartoszjaszczak.security.user.UserInfo) && execution(* *(..))")
    fun logExecutionTime(joinPoint: JoinPoint) {
        val currentUser = userProvider.currentUser() ?: "Unknown"
        logger.info("User: ${currentUser}, accessing method ${joinPoint.signature}")
    }
}
```

#### `library/account/src/main/kotlin/com/bartoszjaszczak/account/AccountService.kt` Usage with `UserInfo` Aspect on private method

```kotlin
package com.bartoszjaszczak.account

import com.bartoszjaszczak.security.user.UserInfo

class AccountService {

  private val userAccount = mapOf("admin" to 1000)

  fun userBalance(username: String) = internalBalance(username)

  @UserInfo
  private fun internalBalance(username: String) = userAccount[username]
}
```

### 4. Build and Run

Build the project using Gradle:

```sh
./gradlew build
```

Run the application:

```sh
./gradlew :application:api-app:bootRun
```

## Conclusion

This setup allows you to use AspectJ for bytecode weaving in a multi-module Gradle project. Adjust the configurations and dependencies as needed for your specific use case.
