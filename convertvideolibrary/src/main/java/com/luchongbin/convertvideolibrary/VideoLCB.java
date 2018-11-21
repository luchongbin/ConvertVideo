package com.luchongbin.convertvideolibrary;


/**
 * Created by luchongbin on 2018/11/21.
 */
public class VideoLCB {


    public static ConvertVideoTask convertVideo(String srcPath, String destPath, int outputWidth, int outputHeight, int bitrate, ProgressListener listener) {
        ConvertVideoTask task = new ConvertVideoTask(listener);
        task.execute(srcPath, destPath, outputWidth, outputHeight, bitrate);
        return task;
    }


    public static interface ProgressListener {

        void onStart();
        void onFinish(boolean result);
        void onProgress(float progress);

    }

}
