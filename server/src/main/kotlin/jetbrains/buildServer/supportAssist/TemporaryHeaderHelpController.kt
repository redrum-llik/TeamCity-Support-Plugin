package com.demoDomain.teamcity.demoPlugin.controllers

import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.web.openapi.PlaceId
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.web.openapi.SimplePageExtension

class TemporaryHeaderHelpController(
    descriptor: PluginDescriptor,
    places: PagePlaces
) {
    init {

        // For the Sakura UI we register the Plugin using the SAKURA-prefixed PlaceIDs. Keep in mind the new syntax:
        // new PlaceID(String) - constructor accepts any string. Plugins for SAKURA-prefixed PlaceIDs are available only
        // in the Sakura UI and are loading asynchronously.
        SimplePageExtension(
            places,
            PlaceId("SAKURA_BEFORE_CONTENT"),
            PLUGIN_NAME,
            descriptor.getPluginResourcesPath("basic-plugin.jsp")
        ).addCssFile("basic-plugin.css").register()

        // For the Classic UI we continue using the regular PlaceIds. Those plugins are rendered on the Server
        // and, generally speaking, stay the same, as they were the last decade
        SimplePageExtension(
            places,
            PlaceId.BEFORE_CONTENT,
            PLUGIN_NAME,
            descriptor.getPluginResourcesPath("basic-plugin.jsp")
        ).addCssFile("basic-plugin.css").register()
    }

    companion object {
        private const val PLUGIN_NAME = "SakuraUI-Plugin"
    }
}