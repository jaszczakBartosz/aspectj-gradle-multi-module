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