# PluginBuilderProject
一个简单的gradle插件，练手项目

todo 将android sourceSets 配置改为插件功能写死


//设置为非APP时，改变namaMap返回值，指定输出目录，可以在指定的目录中创建新命名的文件，比如把apk改成ywgameplugin_sdk.jar
//配合sourceSets可以将输出内容直接分发到指定的宿主目录中
def isAPP = false//false将重命名并在对应目标目录生成新的文件，true则标识正常的输出
apkdistconf {
    isApp = isAPP.toBoolean()

    nameMap { name ->
        println 'hello, ' + name
        return "ywgameplugin_sdk.jar"
//        return name
    }
    destDir 'D:/'
}
