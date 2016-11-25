package com.ym.pluginbuilder

import groovy.lang.Closure
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
 * 教程：http://kvh.io/cn/embrace-android-studio-gradle-plugin.html
 */

public class Pluginbuilder implements Plugin<Project> {


    @Override
    void apply(Project project) {

        project.extensions.create('apkdistconf', ApkDistExtension);

        project.afterEvaluate {

            //只可以在 android application 或者 android lib 项目中使用
            if (!project.android) {
                throw new IllegalStateException('Must apply \'com.android.application\' or \'com.android.library\' first!')
            }

            //配置不能为空
            if (project.apkdistconf.nameMap == null || project.apkdistconf.destDir == null) {
                project.logger.info('apkdistconf conf should be set!')
                return
            }


            Boolean isApp = true;
            isApp = project['apkdistconf'].isApp
            Closure nameMap = project['apkdistconf'].nameMap
            String destDir = project['apkdistconf'].destDir

            //枚举每一个 build variant
            if (!isApp) {
                project.android.applicationVariants.all { variant ->
                    variant.outputs.each { output ->
                        File file = output.outputFile
                        output.outputFile = new File(destDir, nameMap(file.getName()))
                    }
                }
            }
        }
    }
}
