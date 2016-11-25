package com.ym.pluginbuilder

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * 项目名称：PluginBuilderProject
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/24 15:58
 * 修改人：wengyiming
 * 修改时间：2016/11/24 15:58
 * 修改备注：
 *
 * 官方教程：https://docs.gradle.org/current/userguide/custom_plugins.html
 * 教程：http://kvh.io/cn/embrace-android-studio-gradle-plugin.html
 * android gradle build :https://android.googlesource.com/platform/tools/build/+/729497f/gradle/src/main/groovy/com/android/build/gradle/AndroidPlugin.groovy
 * android gradle build : https://github.com/google/android-gradle-dsl
 * http://google.github.io/android-gradle-dsl/javadoc/
 *
 * http://blog.csdn.net/qinxiandiqi/article/details/37925629
 */

public class Pluginbuilder implements Plugin<Project> {


    @Override
    void apply(Project project) {
        Boolean isApp = true;
        project.extensions.create('apkdistconf', ApkDistExtension);
        //只可以在 android application 或者 android lib 项目中使用
        if (!project.android) {
            throw new IllegalStateException('Must apply \'com.android.application\' or \'com.android.library\' first!')
        }

        project.afterEvaluate {
            Closure nameMap = project['apkdistconf'].nameMap
            String destDir = project['apkdistconf'].destDir
            isApp = project['apkdistconf'].isApp
            //枚举每一个 build variant
            if (!isApp) {
                println "当前项目不是APP独立项目，打包的APK将被输出到---->>$destDir" + nameMap("")
//                project.logger.info("当前项目不是APP独立项目，打包的APK将被输出到---->>$destDir" + nameMap(""))
                project.android.applicationVariants.all { variant ->
                    variant.outputs.each { output ->
                        File file = output.outputFile
                        output.outputFile = new File(destDir, nameMap(file.getName()))
                    }
                }
            } else {
                println "当前项目是APP独立项目，打包的APK将被输出到---->>build/output/apk"
//                project.logger.info("当前项目是APP独立项目，打包的APK将被输出到---->>build/output/apk")
            }
        }
    }
}
