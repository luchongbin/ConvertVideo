package com.luchongbin.convertvideo;

import android.Manifest;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.luchongbin.convertvideolibrary.VideoLCB;
import com.luchongbin.lcbpermissionsmanager.LCBCPListener;
import com.luchongbin.lcbpermissionsmanager.LCBCPOptions;
import com.luchongbin.lcbpermissionsmanager.LCBCheckPermissions;

import java.io.File;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,LCBCPListener {
    private static final int REQUEST_FOR_VIDEO_FILE = 1000;
    private TextView tv_input, tv_output, tv_indicator, tv_progress;
    private String outputDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).getAbsolutePath();

    private String inputPath;

    private ProgressBar pb_compress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        tv_input = findViewById(R.id.tv_input);
        tv_output = findViewById(R.id.tv_output);
        tv_indicator = findViewById(R.id.tv_indicator);
        tv_progress = findViewById(R.id.tv_progress);
        pb_compress = findViewById(R.id.pb_compress);
        Button btn_select = findViewById(R.id.btn_select);
        btn_select.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_select:
                //检测权限
                String[] mPermissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
                LCBCheckPermissions.getInstance(this).request(new LCBCPOptions.Builder()
                        .setPermissions(mPermissions)
                        .build(),this);
                break;
        }
    }
    //权限申请成功
    @Override
    public void onGranted() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_FOR_VIDEO_FILE);

    }

    @Override
    public void onDenied(List<String> list) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_FOR_VIDEO_FILE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                try {
                    inputPath = Util.getFilePath(this, data.getData());

                    MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                    retriever.setDataSource(inputPath);
                    String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                    String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                    String bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
                    String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                    File f = new File(inputPath);
                    long fileSize = f.length();

                    String before = "输入原视频路径:" +inputPath+ "\n" + "宽:" + width + "\n" + "高:" + height + "\n" + "bitrate:" + bitrate + "\n"
                            + "文件大小:" + Formatter.formatFileSize(MainActivity.this,fileSize) +"\n" +"duration(ms):"+duration;
                    tv_input.setText(before);

                    final String destPath = outputDir + File.separator + "VIDEOSIMMER_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".mp4";
                    VideoLCB.convertVideo(inputPath, destPath, 720, 1280, 720 * 1280 * 7, new VideoLCB.ProgressListener() {
                        @Override
                        public void onStart() {
                            pb_compress.setVisibility(View.VISIBLE);
                            tv_indicator.setText("");

                        }

                        @Override
                        public void onFinish(boolean result) {
                            if (result) {
                                tv_progress.setText("100%");
                                pb_compress.setVisibility(View.INVISIBLE);
                                tv_indicator.setText("压缩完成");
                                MediaMetadataRetriever retriever = new MediaMetadataRetriever();
                                retriever.setDataSource(destPath);
                                String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
                                String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
                                String bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);

                                File f = new File(destPath);
                                long fileSize = f.length();

                                String after = "输出视频路径:" +destPath+ "\n" + "宽:" + width + "\n" + "高:" + height + "\n" + "bitrate:" + bitrate + "\n"
                                        + "文件大小:" + Formatter.formatFileSize(MainActivity.this,fileSize);
                                tv_output.setText(after);


                            } else {

                                tv_progress.setText("0%");
                                tv_indicator.setText("压缩失败!");
                                pb_compress.setVisibility(View.INVISIBLE);
                                Util.writeFile(MainActivity.this, "Failed Compress!!!" + new SimpleDateFormat("HH:mm:ss").format(new Date()));
                            }
                        }


                        @Override
                        public void onProgress(float percent) {
                            tv_progress.setText(String.valueOf(percent) + "%");
                        }
                    });


                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
