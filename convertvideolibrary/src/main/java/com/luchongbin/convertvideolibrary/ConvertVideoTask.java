package com.luchongbin.convertvideolibrary;

import android.os.AsyncTask;

import com.luchongbin.convertvideolibrary.listener.ConvertVideoProgressListener;


/**
 * Created by luchongbin on 2018/11/21.
 */
public class ConvertVideoTask extends AsyncTask<Object, Float, Boolean> {
    private VideoLCB.ProgressListener mListener;


    public ConvertVideoTask(VideoLCB.ProgressListener listener) {
        mListener = listener;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (mListener != null) {
            mListener.onStart();
        }
    }

    @Override
    protected Boolean doInBackground(Object... paths) {
        return new ConvertVideoEncoder().convertVideo((String)paths[0], (String)paths[1], (Integer)paths[2],(Integer)paths[3],(Integer)paths[4], new ConvertVideoProgressListener() {
            @Override
            public void onProgress(float percent) {
                publishProgress(percent);
            }
        });
    }

    @Override
    protected void onProgressUpdate(Float... percent) {
        super.onProgressUpdate(percent);
        if (mListener != null) {
            mListener.onProgress(percent[0]);
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (mListener != null) {
            if (result) {
                mListener.onFinish(true);
            } else {
                mListener.onFinish(false);
            }
        }
    }
}
