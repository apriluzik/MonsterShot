package com.mapriluzikgmail.monstershot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class GameoverActivity extends AppCompatActivity {

    TextView tvChampion;
    TextView tvScore;

    CircleImageView imgChampion;
    boolean isChampion= false;  //챔피언인가??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        tvChampion= (TextView) findViewById(R.id.tv_champion);
        tvScore= (TextView) findViewById(R.id.tv_score);
        imgChampion= (CircleImageView) findViewById(R.id.img_champion);

        Intent intent= getIntent();
        Bundle data= intent.getBundleExtra("Data");

        int score= data.getInt("Score", 0);
        int coin = data.getInt("Coin", 0);

        int yourScore= score + coin*10;

        String s= String.format("%07d", yourScore);
        tvScore.setText(s);

        if( yourScore>G.champion){
            //챔피언 점수 갱신
            G.champion= yourScore;
            isChampion=true;
        }

        s= String.format("%07d", G.champion);
        tvChampion.setText(s);


        //챔피언 이미지 표시
        if(G.imgUri!=null){
            Uri uri= Uri.parse(G.imgUri);
            //imgChampion.setImageURI(uri);
            Glide.with(this).load(uri).into(imgChampion);
        }

    }//생성자 메소드

    void saveData(){
        //data.xml이라는 문서에 저장..
        SharedPreferences pref= getSharedPreferences("data", MODE_PRIVATE);
        SharedPreferences.Editor editor= pref.edit();
        editor.putInt("Gem", G.gem);
        editor.putInt("Champion", G.champion);
        editor.putInt("Kind", G.kind);

        editor.putString("ImgUri", G.imgUri);

        editor.putBoolean("Music", G.isMusic);
        editor.putBoolean("Sound", G.isSound);
        editor.putBoolean("Vibrate", G.isVibrate);
        editor.commit();
    }

    @Override
    protected void onDestroy() {
        saveData(); //별도의 파일에 저장...
        super.onDestroy();
    }

    public void clickChampion(View v){
        if(!isChampion) return;

        //내 스마트폰에 있는 사진을 선택할 수 있도록...
        //사진보기 앱(갤러리 or 사진 or 파일매니져..)을 실행!!

        if(Build.VERSION.SDK_INT<19){ //API19버전 미만..(Kiket미만)
            Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        }else{// API19 이상!!
            Intent intent= new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, 10);
        }

    }

    //startActivityForResult()메소드의 호출로 실행되었던 화면(Activity)가
    //종료되어 이 액티비티가 다시 보이면..자동으로 실행되는 메소드
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case 10:
                if(resultCode== Activity.RESULT_OK){
                    Uri uri= data.getData();
                    //imgChampion.setImageURI(uri);
                    G.imgUri= uri.toString();
                    Glide.with(this).load(uri).into(imgChampion);

                    //Log.i("TAG", uri.toString());

//                    try {
//                        Bitmap bm= MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                        imgChampion.setImageBitmap(bm);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }





                }

                break;
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void clickRetry(View v){
        Intent intent= new Intent(this, GameActivity.class);
        startActivity(intent);
        finish();
    }

    public void clickExit(View v){
        finish();
    }
}








