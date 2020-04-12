package com.mjiayou.myplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

public class MyPlugin implements Plugin<Project> {

    void apply(Project project) {
        System.out.println("================================================================");
        System.out.println("Hello MyPlugin!");
        System.out.println("================================================================");
    }
}