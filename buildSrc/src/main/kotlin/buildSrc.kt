import org.gradle.api.Project
import java.io.File

fun isCi() = System.getenv("CI") != null ||
    System.getenv("CONTINUOUS_INTEGRATION") != null

fun Project.buildConfigFile(path: String) = File(rootDir, "build-config/$path")
