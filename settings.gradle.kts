plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "multi-module-aspectj-gradle"

include("library")
include("library:account-service")
findProject(":library:account-service")?.name = "account-service"
include("application:api-app")
findProject(":application:api-app")?.name = "api-app"
