import dev.slne.surf.surfapi.gradle.util.registerRequired
import dev.slne.surf.surfapi.gradle.util.withSurfApiBukkit

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

group = "dev.slne.surf"
version = "1.21.1.0.0-SNAPSHOT"

dependencies {
    compileOnly("com.nexomc:nexo:1.1.0")
    implementation(platform("com.intellectualsites.bom:bom-newest:1.52"))
    compileOnly("com.intellectualsites.plotsquared:plotsquared-core")
    compileOnly("com.intellectualsites.plotsquared:plotsquared-bukkit") { isTransitive = false }
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.HotbarValuationPlugin")
    authors.add("ammo")

    generateLibraryLoader(false)

    foliaSupported(true)

    serverDependencies {
        registerRequired("Nexo")
        registerRequired("PlotSquared")
    }

    runServer {
        withSurfApiBukkit()
    }
}