package dev.sebastiano.example.config

import io.ktor.application.Application

sealed class Environment(
    open val config: Configuration,
    open val name: String
) {

    class Development private constructor(config: Configuration) : Environment(config, "dev") {

        constructor(application: Application) : this(
            Configuration(
                version = application.getConfigProperty("environments.dev.version")
            )
        )
    }

    override fun toString(): String {
        return "Environment { name: $name }"
    }

    companion object {
        private lateinit var currentEnvironment: Environment

        fun current(application: Application): Environment {
            if (!this::currentEnvironment.isInitialized) {
                currentEnvironment = Development(application)
                // TODO: use right environment when there are multiple ones defined
//                currentEnvironment = if (application.isDevMode) {
//                    Development(application)
//                } else {
//                    Production(application)
//                }
            }

            return currentEnvironment
        }
    }
}

private fun Application.getConfigProperty(key: String) =
    environment.config.property(key).getString()
