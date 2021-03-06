package com.mapriluzikgmail.monstershot;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by alfo00 on 2017-06-28.
 */

public class Enemy {

    int width, height;

    Bitmap img;
    int x, y;
    int w, h;
    boolean isDead= false;  //총맞아 죽었는가?
    boolean isOut= false;   //화면나가서 죽었는가?
    boolean wasShow= false;  //화면에 보였던 적이 있는가??

    Rect screenRec; //화면사이즈의 사각형 객체참조변수

    double radian;   //이동각도
    int speed;       //이동속도

    int angle;       //회전각도

    int kind;        //적군종류
    Bitmap[] imgs;
    int index;       //날개짓 이미지번호
    int loop=0;

    int hp;

    Bitmap[] imgGs;   //게이지이미지 원본들..
    Bitmap imgG;      //현재 보여줄 게이지 이미지.

    public Enemy(int width, int height, Bitmap[][] imgEnemy, int chx, int chy, Bitmap[][] imgGauge) {
        this.width= width; this.height= height;
        screenRec= new Rect(0,0, width, height);

        Random rnd= new Random();

        //0:white  1:yellow  2:pink;
        int n= rnd.nextInt(10);
        kind= n<6?0:n<9?1:2;
        imgs= imgEnemy[kind];
        img= imgs[index];
        w= img.getWidth()/2;
        h= img.getHeight()/2;

        //생명 : white:1  yellow:5   pink:3
        hp= kind==0?1 : kind==1?5 : 3;

        speed= kind==0?w/6 : kind==1?w/8 : w/10;


        int a= rnd.nextInt(360); //0~360;
        x= (int)(width/2 + Math.cos( Math.toRadians(a) )* width);
        y= (int)(height/2- Math.sin( Math.toRadians(a) )* width);

        //플레이어를 바라보는 각도..(이 적군이 이동할 각도)
        calAngle(chx, chy);

        if(kind>0){//while가 아닌경우..게이지를 가짐.
            imgGs= imgGauge[kind-1];
            imgG= imgGs[0];
        }

    }

    void calAngle(int chx, int chy){
        radian= Math.atan2( y-chy, chx- x);
        //회전각도
        angle= (int)(270- Math.toDegrees(radian));
    }

    void damaged(int n){
        hp-=n;
        if(hp<=0){
            isDead=true;
            return;
        }

        imgG= imgGs[imgGs.length-hp];
    }

    void move(int chx, int chy){
        if(kind==2) calAngle(chx, chy);

        //날개짓
        loop++;
        if(loop%3==0){
            index++;
            if(index>3) index=0;
            img= imgs[index];
        }

        //이동
        x= (int)(x + Math.cos(radian)*speed);
        y= (int)(y - Math.sin(radian)*speed);

        //적군의 좌표가 화면안에 들어 왔는가?
        if( screenRec.contains(x, y) ) wasShow=true;

        if(wasShow){
            //화면밖으로 나가면 제거
            if(x<-w || x>width+w || y<-h || y>height+h) isOut=true;
        }
    }

}


















