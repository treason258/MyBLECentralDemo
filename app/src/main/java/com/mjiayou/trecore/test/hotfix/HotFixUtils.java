package com.mjiayou.trecore.test.hotfix;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class HotFixUtils {

    public static String DEX_NAME = "out.dex";
    public static String DIR_DEX = "dex";
    public static String DIR_DEX_CACHE = "cache";

    private static HashSet<File> mDexFiles = new HashSet<>();

    static {
        mDexFiles.clear();
    }

    public static void loadDex(Context context) {
        if (context == null) {
            return;
        }

        // 获取修复文件在SD卡的文件名字
        String dexFileName = HotFixUtils.DEX_NAME;
        // 获取修复文件在SD卡的完整路径
        File dexFileInSDCard = new File(Environment.getExternalStorageDirectory(), dexFileName);

        // 创建修复包文件夹，如果不存在则创建
        File dexFolder = context.getDir(DIR_DEX, Context.MODE_PRIVATE); // 将修复包复制到当前私有目录
        if (!dexFolder.exists()) {
            dexFolder.mkdirs();
        }
        // 如果修复包文件已存在则删除
        File dexFileInApp = new File(dexFolder, dexFileName);
        if (dexFileInApp.exists()) {
            dexFileInApp.delete();
        }

        // 复制文件操作
        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(dexFileInSDCard);
            fileOutputStream = new FileOutputStream(dexFileInApp);
            int length;
            byte[] buffer = new byte[1024];
            while ((length = fileInputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // 遍历判断这个文件夹下面所有的文件是不是都是修复包
        for (File dexFile : dexFolder.listFiles()) {
            if (dexFile.getName().startsWith("classes") || dexFile.getName().endsWith(".dex")) {
                mDexFiles.add(dexFile);
            }
        }

        // 创建一个dex的缓存目录
        String optimizedFolderPath = dexFolder.getAbsolutePath() + File.separator + DIR_DEX_CACHE;
        File optimizedFile = new File(optimizedFolderPath);
        if (!optimizedFile.exists()) {
            optimizedFile.mkdirs();
        }

        // 遍历修复包逐个处理
        try {
            for (File dexFile : mDexFiles) {
                //// 第一步、先获取当前应用的 dexElements 对象
                // http://androidxref.com/8.0.0_r4/xref/libcore/dalvik/src/main/java/dalvik/system/BaseDexClassLoader.java
                // http://androidxref.com/8.0.0_r4/xref/libcore/dalvik/src/main/java/dalvik/system/DexPathList.java
                PathClassLoader pathClassLoader = (PathClassLoader) context.getClassLoader(); // 获取当前应用的 pathClassLoader
                Class<?> BaseDexClassLoader = Class.forName("dalvik.system.BaseDexClassLoader"); // 通过反射获取到 BaseDexClassLoader 类对象

                Field pathListFiled = BaseDexClassLoader.getDeclaredField("pathList"); // 获取 BaseDexClassLoader 类中的 pathList 成员变量的反射对象
                pathListFiled.setAccessible(true); // 打开权限
                Object systemPathListValue = pathListFiled.get(pathClassLoader); // 获取 pathList 这个变量在当前系统 pathClassLoader 中的值

                Field dexElementsField = systemPathListValue.getClass().getDeclaredField("dexElements"); // 获取 DexPathList 类中的 dexElements 成员变量的反射对象
                dexElementsField.setAccessible(true); // 打开权限
                Object systemDexElementsValue = dexElementsField.get(systemPathListValue); // 获取 dexElements 在当前系统 systemPathListValue 中的值

                //// 第二步、获取修复包的 dexElements 对象
                DexClassLoader fixDexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), optimizedFolderPath, null, context.getClassLoader());
                Object fixPathListValue = pathListFiled.get(fixDexClassLoader); // 获取到 pathList 这个变量在修复包 fixDexClassLoader 中的值
                Object fixDexElementsValue = dexElementsField.get(fixPathListValue); // 获取 dexElements 这个变量在修复包 fixPathListValue 中的值

                //// 第三步、合并数组
                // 得到数组的长度
                int systemElementsLength = Array.getLength(systemDexElementsValue);
                int fixElementsLength = Array.getLength(fixDexElementsValue);
                int newLength = systemElementsLength + fixElementsLength;
                Class<?> componentType = systemDexElementsValue.getClass().getComponentType(); // 获取 Elements 对象类型
                Object newElementArray = Array.newInstance(componentType, newLength); // 创建 Element 数组
                // 将两个数组中的内容收入到新的数组中
                for (int i = 0; i < newLength; i++) {
                    if (i < fixElementsLength) { // 首先放修复包的dex文件
                        Array.set(newElementArray, i, Array.get(fixDexElementsValue, i));
                    } else {
                        Array.set(newElementArray, i, Array.get(systemDexElementsValue, i - fixElementsLength));
                    }
                }

                //// 第四步、将合并好的 dexElement 的数组值赋值给当前应用的 dexElement 的成员变量
                dexElementsField.set(systemPathListValue, newElementArray);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
