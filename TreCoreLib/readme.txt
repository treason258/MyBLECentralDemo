/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
            佛祖保佑       永无BUG
*/

// ******************************** test ********************************
// **************** test ****************
<!-- ******************************* test ******************************* -->
<!-- **************** test **************** -->
## ******************************** test ********************************
## **************** test ****************








// ******************************** 接入指南 ********************************

// **************** 1-project-setting.gradle ****************

include ':TreCoreLib'
project(':TreCoreLib').projectDir = new File('../TreCore/TreCoreLib')

// **************** 2-project-build.gradle ****************

buildscript {
    repositories {
        maven {
            url 'http://maven.aliyun.com/nexus/content/groups/public/'
            artifactUrls 'https://repo1.maven.org/maven2'
        }
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.3'
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.5'
    }
}

allprojects {
    repositories {
        maven {
            url 'http://maven.aliyun.com/nexus/content/groups/public/'
            artifactUrls 'https://repo1.maven.org/maven2'
        }
    }
}

ext {
    compileSdkVersion = 25
    buildToolsVersion = '25.0.3'

    minSdkVersion = 15
    targetSdkVersion = 19

    dependencies = [
        'supportV4'     : 'com.android.support:support-v4:25.3.1',
        'appcompatV7'   : 'com.android.support:appcompat-v7:25.3.1',
        'recyclerviewV7': 'com.android.support:recyclerview-v7:25.3.1',
        'constraint'    : 'com.android.support.constraint:constraint-layout:1.0.2',
    ]

    applicationId = 'com.mjiayou.trecore'
    versionCode = 10
    versionName = '2.0.0'
}

// **************** 3-app-build.gradle ****************

dependencies {
    compile project(':TreCoreLib')
}

// ******************************** log ********************************

新环境测试提交，请忽略333