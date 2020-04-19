package com.mjiayou.myannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // 声明注解的作用域：TYPE-类；FIELD-成员变量；METHOD-方法；PARAMETER；CONSTRUCTOR-构造方法；LOCAL_VARIABLE；ANNOTATION_TYPE-注解类；PACKAGE-包；TYPE_PARAMETER；TYPE_USE；
@Retention(RetentionPolicy.CLASS) // 生命周期：SOURCE-源码；CLASS-编译器；RUNTIME-运行期
public @interface BindPath {
    String value();
}
