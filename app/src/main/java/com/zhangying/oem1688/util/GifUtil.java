package com.zhangying.oem1688.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class GifUtil {


    /**
     * 解析gif图片
     * @param gifPath gif图片地址
     * @param pngDir 解析后图片保存目录
     * @return
     */
    public static String[] gifToPng(String gifPath, String pngDir) {
        final int count = 6;
        String[] commands = new String[count];
        int index = 0;
        commands[index++] = "ffmpeg";
        commands[index++] = "-i";
        commands[index++] = gifPath;
        commands[index++] = "-c:v";
        commands[index++] = "png";
        commands[index++] = pngDir + File.separator + "image%d.png";
        for (int i = 0 ; i < index; i++) {
            LogUtil.e("TAG==接续成png", "index=" + index + ",count=" + count+"======="+"commend=" + commands[i]);
        }
        return commands;
    }

    /**
     * 特定gif生成调色板
     * @param gifPath 输入gif图片文件地址
     * @param globalPalettePicPath 输出gif图片文件地址
     * @return
     */
    public static String[] getglobalPalettePicPath(String gifPath, String globalPalettePicPath) {
        int count = 7;

        String[] commands = new String[count];
        int index = 0;
        commands[index++] = "ffmpeg";

        commands[index++] = "-i";
        commands[index++] = gifPath;
    /*commands[index++] = "-i";
    commands[index++] = waterImageUrl;*/
        commands[index++] = "-vf";
        commands[index++] = "palettegen";

        commands[index++] = "-y";
        commands[index++] = globalPalettePicPath;

            for (int i = 0 ; i < index; i++) {
                LogUtil.e("TAG===调色板", "index=" + index + ",count=" + count+"======="+"commend=" + commands[i]);
            }
        return commands;
    }


    /**
     * png图片合成gif
     * @param pngDir png文件夹地址 该文件夹下的png图片命名需要满足正则image%d.png
     * @param gifPath 生成的gif地址
     * @param globalPalettePicPath 全局颜色列表png图地址
     * @param delay 帧间隔 ms
     * @param threadNum 线程数 0为默认最佳
     * @return
     */
    public static String[] pngToGif(String pngDir, String gifPath, String globalPalettePicPath, long delay, int threadNum) {
        final int count = 15;
        String[] commands = new String[count];
        int index = 0;
        commands[index++] = "ffmpeg";
        commands[index++] = "-r";
        commands[index++] = String.valueOf(1000.0f / delay);
        commands[index++] = "-threads";
        commands[index++] = String.valueOf(threadNum);
        commands[index++] = "-c:v";
        commands[index++] = "png";
        commands[index++] = "-i";
        commands[index++] = pngDir + File.separator + "image%d.png";

        commands[index++] = "-i";
        commands[index++] = globalPalettePicPath;
        commands[index++] = "-lavfi";
        commands[index++] = "paletteuse=bayer";

        commands[index++] = "-y";
        commands[index++] = gifPath;
            for (int i = 0 ; i < index; i++) {
                LogUtil.e("TAG===生成GIF", "index=" + index + ",count=" + count+"======commend=" + commands[i]);
            }
        return commands;
    }

    public static String createGif(String filename, List<String> paths, int fps, int width, int height) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        AnimatedGifEncoder localAnimatedGifEncoder = new AnimatedGifEncoder();
        localAnimatedGifEncoder.start(baos);//start
        localAnimatedGifEncoder.setRepeat(0);//设置生成gif的开始播放时间。0为立即开始播放
        localAnimatedGifEncoder.setDelay(fps);
        if (paths.size() > 0) {
            for (int i = 0; i < paths.size(); i++) {
                Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
//                Bitmap resizeBm = ImageUtil.resizeImage(bitmap, width, height);
                localAnimatedGifEncoder.addFrame(bitmap);
            }
        }
        localAnimatedGifEncoder.finish();//finish

        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/LiliNote");
        if (!file.exists()) file.mkdir();
        String path = Environment.getExternalStorageDirectory().getPath() + "/LiliNote/" + filename + ".gif";
        FileOutputStream fos = new FileOutputStream(path);
        baos.writeTo(fos);
        baos.flush();
        fos.flush();
        baos.close();
        fos.close();

        return path;
    }

}
