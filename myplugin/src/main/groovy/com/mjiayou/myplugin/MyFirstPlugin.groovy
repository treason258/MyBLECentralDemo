package com.mjiayou.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class MyFirstPlugin implements Plugin<Project> {

    void apply(Project project) {
        log("******************************** apply-start ********************************");
        log("Hello MyFirstPlugin v1.0.8")
        log("******************************** apply-end ********************************");
    }

    private void log(String msg) {
        System.out.println("matengfei888 | MyFirstPlugin | " + msg);
    }
}