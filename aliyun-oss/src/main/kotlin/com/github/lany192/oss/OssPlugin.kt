package com.github.lany192.oss

import com.aliyun.oss.OSSClientBuilder
import com.aliyun.oss.common.auth.CredentialsProviderFactory
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.json.simple.JSONObject
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest

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
                project.tasks.create("upload2oss") {
                    it.group = "oss"
                    it.description = "上传文件到aliyun oss"
                }
            }
        } else {
            println("不是在app模块注册，当前插件未启用")
        }
    }

    fun uploadFile(config: PluginConfig) {
        val file = File(config.filePath)
        if (!file.exists()) {
            println("要上传的文件不存在：" + config.filePath)
            return
        }
        val md5 = getFileMD5(file)

        val credentialsProvider = CredentialsProviderFactory.newDefaultCredentialProvider(
            config.accessKeyId, config.accessKeySecret
        )
        val ossClient = OSSClientBuilder().build(config.endpoint, credentialsProvider)

        try {
            ossClient?.putObject(config.bucketName, config.objectName, file)

            val json = JSONObject()
            json["url"] = "https://xzwcn.oss-cn-shanghai.aliyuncs.com/" + config.objectName
            json["md5"] = md5
            json["size"] = file.length()
            println("上传成功:" + json.toJSONString())
        } catch (e: Exception) {
            println("上传失败:" + e.message)
        } finally {
            ossClient?.shutdown()
        }
    }

    private fun getFileMD5(file: File): String {
        val md5Digest = MessageDigest.getInstance("MD5")
        // 使用缓冲区读取文件内容
        val buffer = ByteArray(8192)
        val fis = FileInputStream(file)
        var bytesRead: Int
        while (fis.read(buffer).also { bytesRead = it } != -1) {
            md5Digest.update(buffer, 0, bytesRead)
        }
        fis.close()
        // 获取计算得到的MD5值
        val md5Bytes = md5Digest.digest()
        // 将字节数组转换为十六进制字符串表示
        val md5String = StringBuilder()
        for (i in md5Bytes.indices) {
            md5String.append(
                Integer.toString((md5Bytes[i].toInt() and 0xff) + 0x100, 16).substring(1)
            )
        }
        return md5String.toString()
    }
}