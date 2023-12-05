package com.github.lany192.oss

import org.gradle.api.Project
import org.json.simple.JSONObject

open class PluginConfig {

    var bucketEndpoint = ""

    var accessKeyId = ""

    var accessKeySecret = ""

    var bucketName = ""

    var dependsOn: Array<String> = emptyArray()

    constructor(project: Project) {
        println("插件所在位置："+project.name)
    }

    fun toJson(): String {
        val json = JSONObject()
        json["bucketEndpoint"] = bucketEndpoint
        json["accessKeyId"] = accessKeyId
        json["accessKeySecret"] = accessKeySecret
        json["bucketName"] = bucketName
        json["dependsOn"] = dependsOn
        return json.toJSONString()
    }
}