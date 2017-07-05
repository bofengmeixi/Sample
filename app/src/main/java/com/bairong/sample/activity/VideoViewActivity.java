package com.bairong.sample.activity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;

import com.bairong.sample.R;
import com.bairong.sample.custom.MyVideoView;

import java.io.File;

public class VideoViewActivity extends AppCompatActivity {
    private final String videoUrl = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private final String videoUrl2="rtsp://v2.cache2.c.youtube.com/CjgLENy73wIaLwm3JbT_%ED%AF%80%ED%B0%819HqWohMYESARFEIJbXYtZ29vZ2xlSARSB3Jlc3VsdHNg_vSmsbeSyd5JDA==/0/0/0/video.3gp";
    private MyVideoView vv_video;
    private MediaController mController;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_view);
        vv_video= (MyVideoView) findViewById(R.id.vv_video);
        button= (Button) findViewById(R.id.button);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                button.setVisibility(View.GONE);
                vv_video.setVisibility(View.VISIBLE);
                vv_video.start();
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });
        // 实例化MediaController
        mController=new MediaController(this);
        File file=new File("/sdcard/Android/test.mp4");
        if(file.exists()){
            // 设置播放视频源的路径
            //vv_video.setVideoPath(file.getAbsolutePath());
            vv_video.setVideoURI(Uri.parse(videoUrl));

            //vv_video.
            // 为VideoView指定MediaController
            //vv_video.setMediaController(mController);
            // 为MediaController指定控制的VideoView
            //mController.setMediaPlayer(vv_video);
            // 增加监听上一个和下一个的切换事件，默认这两个按钮是不显示的
            mController.setPrevNextListeners(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(VideoViewActivity.this, "下一个",Toast.LENGTH_SHORT).show();
                }
            }, new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(VideoViewActivity.this, "上一个",Toast.LENGTH_SHORT).show();
                }
            });
            vv_video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    vv_video.setVisibility(View.GONE);
                    button.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}