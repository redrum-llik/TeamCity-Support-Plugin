package jetbrains.buildServer.supportAssist.controllers

import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.web.openapi.PlaceId
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.SimplePageExtension

class DebugHeaderHelpController(
    descriptor: PluginDescriptor,
    places: PagePlaces
): SimplePageExtension(
    places,
    PlaceId("SAKURA_HEADER_USERNAME_BEFORE"),
    PLUGIN_NAME,
    descriptor.getPluginResourcesPath("basic-plugin.jsp")
) {
    init {
        addCssFile("basic-plugin.css")
        register()
    }

    companion object {
        private const val PLUGIN_NAME = "supportPlugin"
    }
}