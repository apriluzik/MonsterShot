package com.mapriluzikgmail.monstershot;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    ImageView img;
    Timer timer= new Timer();//스케쥴 관리자(비서)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);

        img= (ImageView) findViewById(R.id.img_logo);

        //appear_logo.xml문서로 애니메이션 객체 생성
        Animation ani= AnimationUtils.loadAnimation(this, R.anim.appear_logo);
        img.startAnimation(ani);

        //4.5초후에 startActivity()를 호출하여 MainActivity를 실행
        //하도록 스케쥴관리자에게 스케쥴 등록..
        timer.schedule(task, 3500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    //스케쥴러(Timer)에 등록되어서 작업을 수행할 TimerTask객체 생성
    TimerTask task= new TimerTask() {
        @Override
        public void run() {
            //MainActivity를 실행
            Intent intent= new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }
    };


}









