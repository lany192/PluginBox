package com.github.lany192.oss

import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class OssPlugin : Plugin<Project> {

    private val pluginName = "ossPlugin"

    override fun apply(project: Project) {
        val isApp = project.plugins.hasPlugin(AppPlugin::class.java)
        if (isApp) {
            project.extensions.create(pluginName, PluginConfig::class.java, project)
            project.afterEvaluate {
                val ossPlugin = project.extensions.findByName(pluginName)
                val extension = ossPlugin as PluginConfig
                println("插件信息：" + extension.toJson())
            }
        } else {
            println("不是在app模块注册，当前插件未启用")
        }
    }
}