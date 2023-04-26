import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.sqlDelight)
}

kotlin {
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)

                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.composeIcons.fontAwesome)

                implementation(libs.viewmodel)
                implementation(libs.viewmodel.compose)
                implementation(libs.viewmodel.odyssey)

                implementation(libs.navigation.core)
                implementation(libs.navigation.compose)

                implementation(libs.settings)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.sqlDelight.driver.sqlite)

                // Coroutines for sql delight
                implementation("app.cash.sqldelight:coroutines-extensions:2.0.0-alpha05")
            }
        }

    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "zlagoda.ukma.edu.ua.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
  databases {
    create("MyDatabase") {
      // Database configuration here.
      // https://cashapp.github.io/sqldelight
      packageName.set("zlagoda.ukma.edu.ua.db")
    }
  }
}
