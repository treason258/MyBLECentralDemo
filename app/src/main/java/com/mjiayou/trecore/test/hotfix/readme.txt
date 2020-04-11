
1、找到修复文件 CrashClass.class 所在目录
/Users/treason/Documents/Projects/TreCore/app/build/intermediates/classes/official/debug/com

2、把com文件夹复制到操作目录下
/Users/treason/Downloads/HotFix/

3、删除没有问题的class文件

4、把需要修复的class文件打包成dex
/Users/treason/Archives/dev/android-sdk-macosx/build-tools/26.0.0/dx --dex --output=/Users/treason/Downloads/HotFix/out.dex  /Users/treason/Downloads/HotFix/

5、把生成的 /Users/treason/Downloads/HotFix/out.dex 文件复制到SD卡根目录
