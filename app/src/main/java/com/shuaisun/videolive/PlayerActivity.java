package com.shuaisun.videolive;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;

import com.shuaisun.videolive.player.IRenderView;
import com.shuaisun.videolive.player.IjkVideoView;
import com.shuaisun.videolive.player.PlayerManager;

/**
 * Created by shuaisun on 2017/9/17.
 */

public class PlayerActivity extends AppCompatActivity {


    private IjkVideoView videoView;
    private PlayerManager playerManager;

    public static void intentTo(Context context, String url){
        Intent intent=new Intent(context,PlayerActivity.class);
        intent.putExtra("url",url);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_layout);
        videoView = (IjkVideoView) findViewById(R.id.video_view);
//        videoView.setVideoURI(Uri.parse(getIntent().getStringExtra("url")));
//        videoView.set
//        videoView.start();
         playerManager=new PlayerManager(this);
        playerManager.play(getIntent().getStringExtra("url"));
    }

    @Override
    protected void onPause() {
        super.onPause();
        playerManager.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        playerManager.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerManager.onDestroy();
    }
}
