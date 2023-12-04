package com.github.lany192.oss

import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project


class AliyunOssPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        Logger.make(project)
        val isApp = project.plugins.hasPlugin(AppPlugin::class.java)
        //only application module needs this plugin to generate register code
        if (isApp) {
            Logger.i("Project enable arouter-register plugin")
        } else {
            Logger.i("不是在app模块注册，当前插件未启用")
        }
    }
}