package com.zhangying.oem1688.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class FileUtil {

    private static String cateAreaFile = "catearea.json";

    //将产品分类和地区信息写入本地
    public static void writeCateAreaToFile(String strContent){
        writeTxtToFile(strContent, Constant.PATH_SAVE_DEVICE, cateAreaFile, false);
    }

    //本地读取产品分类和地区信息
    public static String readCateAreaFromFile(String strContent){
        return readTxtFromFile(cateAreaFile);
    }

    /**
     * 字符串写入本地txt
     *
     * @param strContent 文件内容
     * @param filePath   文件地址
     * @param fileName   文件名
     * @param append     追加还是覆盖，true：追加、false：覆盖
     * @return 写入结果
     */
    private static boolean writeTxtToFile(String strContent, String filePath, String fileName, boolean append) {
        boolean isSavaFile = false;
        makeFilePath(filePath, fileName);
        String strFilePath = filePath + fileName;
        try {
            File file = new File(strFilePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            if (append) {
                //如果为追加则在原来的基础上继续写文件
                RandomAccessFile raf = new RandomAccessFile(file, "rwd");
                raf.seek(file.length());
                raf.write(strContent.getBytes());
                raf.close();
            } else {
                //重写文件，覆盖掉原来的数据
                FileOutputStream out = new FileOutputStream(file);
                out.write(strContent.getBytes());
                out.flush();
            }

            isSavaFile = true;
        } catch (Exception e) {
            isSavaFile = false;
        }
        return isSavaFile;
    }

    /**
     * 生成文件
     *
     * @param filePath 文件地址
     * @param fileName 文件名
     */
    private static File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * 生成文件夹
     */
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 读取本地文件
     */
    public static String readTxtFromFile(String fileName) {
        String path = Constant.PATH_SAVE_DEVICE + fileName;
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File(path);
        if (!file.exists()) {
            return "";
        }
        if (file.isDirectory()) {
            return "";
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputReader = new InputStreamReader(instream);
                    BufferedReader buffReader = new BufferedReader(inputReader);
                    String line;
                    while ((line = buffReader.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                return "";
            } catch (IOException e) {
                return "";
            }
        }
        return stringBuilder.toString();
    }

    public static class Constant {
        public static String PATH_SAVE_DEVICE = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator;

    }

}
