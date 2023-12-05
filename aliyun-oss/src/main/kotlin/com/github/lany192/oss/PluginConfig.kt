package com.github.lany192.oss

import org.gradle.api.Project
import org.json.simple.JSONObject

open class PluginConfig {

    var endpoint = "https://oss-cn-shanghai.aliyuncs.com"

    var bucketName = ""

    var objectName = ""

    var accessKeyId = ""

    var accessKeySecret = ""

    var filePath = ""

    var dependsOn: Array<String> = emptyArray()

    constructor(project: Project) {
        println("插件所在位置：" + project.name)
    }

    fun toJson(): String {
        val json = JSONObject()
        json["endpoint"] = endpoint
        json["bucketName"] = bucketName
        json["objectName"] = objectName
        json["accessKeyId"] = accessKeyId
        json["accessKeySecret"] = accessKeySecret

        json["filePath"] = filePath

        json["dependsOn"] = dependsOn
        return json.toJSONString()
    }
}