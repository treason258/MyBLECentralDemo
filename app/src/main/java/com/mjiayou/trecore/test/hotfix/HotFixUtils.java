package com.mjiayou.trecore.test.hotfix;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class HotFixUtils {

    private static HashSet<File> loadedDex = new HashSet<>();

    static {
        loadedDex.clear();
    }

    public static void loadDex(Context context) {
        // 得到所有修复包
        if (context == null) {
            return;
        }
        // 加载修复包
        File filesDir = context.getDir("odex", Context.MODE_PRIVATE);
        File[] files = filesDir.listFiles();
        // 遍历 判断这个文件夹下面所有的文件是不是都是修复包
        for (File file : files) {
            if (file.getName().startsWith("classes") || file.getName().endsWith(".dex")) {
                loadedDex.add(file);
            }
        }

        //创建一个odex的缓存目录
        String optimizeDir = filesDir.getAbsolutePath() + File.separator + "cache_dex";
        File fopt = new File(optimizeDir);
        if (!fopt.exists()) {
            fopt.mkdirs();
        }

        for (File dex : loadedDex) {
            // http://androidxref.com/8.0.0_r4/xref/libcore/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java
            // http://androidxref.com/8.0.0_r4/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java
            try {
                //// 第一步 先获取当前应用的 dexElements 的对象

                PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader();
                // 通过反射获取到BaseDexClassLoader类对象
                Class<?> BaseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader");

                // 获取 BaseDexClassLoader 类中的 pathList 的反射对象
                Field pathListFiled = BaseDexClassLoader.getDeclaredField("pathList");
                // 先打开权限
                pathListFiled.setAccessible(true);
                // 获取 pathList 这个变量在当前类加载器中的值
                Object pathListValue = pathListFiled.get(pathClassLoader);

                // 获取 dexElements 成员变量的反射对象
                Field dexElementsField = pathListValue.getClass().getDeclaredField("dexElements");
                // 打开权限
                dexElementsField.setAccessible(true);
                // 获取当前应用的 dexElements 在当前应用中的值
                Object systemDexElementValues = dexElementsField.get(pathListValue);


                //// 第二步 获取修复包的 dexElements 的对象
                DexClassLoader dexClassLoader = new DexClassLoader(dex.getAbsolutePath(), optimizeDir, null, context.getClassLoader());
                // 获取到 pathList 在修复包中的值
                Object myPathListValue = pathListFiled.get(dexClassLoader);
                Object fixDexElementValues = dexElementsField.get(myPathListValue);

                //// 第三步 合并数组
                // 得到连个数组的长度
                int length = Array.getLength(systemDexElementValues);
                int fixLength = Array.getLength(fixDexElementValues);
                int newLength = length + fixLength;
                // 创建一个新的数组
                Class<?> componentType = systemDexElementValues.getClass().getComponentType();
                // 创建了一个 Element[] 数组
                Object newElementArray = Array.newInstance(componentType, newLength);
                // 将两个数组中的内容收入到新的数组中
                for (int x = 0; x < newLength; x++) {
                    // 首先放修复包的dex文件
                    if (x < fixLength) {
                        Array.set(newElementArray, x, Array.get(fixDexElementValues, x));
                    } else {
                        Array.set(newElementArray, x, Array.get(systemDexElementValues, x - fixLength));
                    }
                }

                //// 将合并好的 dexElement 的数组值赋值给当前应用的 dexElement 的成员变量
                dexElementsField.set(pathListValue, newElementArray);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
