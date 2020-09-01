package com.example.flyingfish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import static java.lang.Math.*;

public class flyingFishView extends View {
    private Bitmap fish[]=new Bitmap[2];
    private int  fishx=10;
    private int fishy;
    private int fishSpeed;
    private int canvasWidth,canvasHeight;
    private boolean touch=false;

    private int score,lifecouterofFish;

    private int yellowX,yellowY, yellowSpeed=16;
    private Paint yellowPaint=new Paint();

    private int greenX,greenY,greenSpeed=20;
    private Paint greenPain=new Paint();


    private int redX,redY,redSpeed=20;
    private Paint redPaint=new Paint();



    private Bitmap backgroundImage;
    private Paint scorepaint=new Paint();
    private Paint highscore=new Paint();
    private Bitmap life[]=new Bitmap[2];
    public flyingFishView(Context context) {
        super(context);
        fish[0]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish2);
        backgroundImage=BitmapFactory.decodeResource(getResources(),R.drawable.background);

        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

       greenPain.setColor(Color.GREEN);
        greenPain.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);


        scorepaint.setColor(Color.WHITE);
        scorepaint.setTextSize(70);
        scorepaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorepaint.setAntiAlias(true);


       highscore.setColor(Color.WHITE);
       highscore.setTextSize(40);

       highscore.setAntiAlias(true);


        life[0]=BitmapFactory.decodeResource(getResources(),R.drawable.hearts);
        life[1]=BitmapFactory.decodeResource(getResources(),R.drawable.heart_grey);

        fishy=550;
        score=0;
        lifecouterofFish=3;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvasWidth=canvas.getWidth();
        canvasHeight=canvas.getHeight();


        canvas.drawBitmap(backgroundImage,0,0,null);
       int minFishY=fish[0].getHeight();
       int maxFishY=canvasHeight-fish[0].getHeight()*3;
       fishy=fishy+fishSpeed;
       if (fishy < minFishY){
            fishy=minFishY;
        }
        if (fishy > maxFishY){
            fishy=maxFishY;
        }
        fishSpeed=fishSpeed+2;
        if (touch){
            canvas.drawBitmap(fish[1],fishx,fishy,null);
            touch=false;
        }else {
            canvas.drawBitmap(fish[0],fishx,fishy,null);
        }

        yellowX=yellowX-yellowSpeed;

        if (hitBallCheckar(yellowX,yellowY)){
            score=score+10;
            yellowX=-100;
        }
        if (yellowX<0){
            yellowX=canvasWidth+21;
           yellowY=(int) Math.floor(Math.random() * (maxFishY -minFishY))+ minFishY;
        }
        canvas.drawCircle(yellowX,yellowY,20,yellowPaint);

       greenX=greenX-greenSpeed;

        if (hitBallCheckar(greenX,greenY)){
            score=score+20;
            greenX=-100;
        }
        if (greenX<0){
            greenX=canvasWidth+21;
            greenY=(int) Math.floor(Math.random() * (maxFishY -minFishY))+ minFishY;
        }
        canvas.drawCircle(greenX,greenY,20,greenPain);
        //redball

        redX=redX-redSpeed;

        if (hitBallCheckar(redX,redY)){

            redX=-100;
            lifecouterofFish--;
            if (lifecouterofFish ==0){
                Toast.makeText(getContext(),"Game Over",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getContext(),Game_Over_Activity.class);

                String m=String.valueOf(score);
                intent.putExtra("sc",m);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(intent);


            }
        }
        if (redX<0){
            redX=canvasWidth+21;
           redY=(int) Math.floor(Math.random() * (maxFishY -minFishY))+ minFishY;
        }


        SharedPreferences sp;
        sp= getContext().getSharedPreferences("com.example.flyingfish",Context.MODE_PRIVATE);

        int scoreig=sp.getInt("highscore",0);
        if (scoreig>score){
            scoreig=score;
        }







        canvas.drawCircle(redX,redY,30,redPaint);

        canvas.drawText("Score: "+score,20,60,scorepaint);


        for (int i = 0; i < 3; i++) {
            int x=(int) (580 +life[0] .getWidth() *1.5 * i);
            int y=30;
            if (i<lifecouterofFish){
                canvas.drawBitmap(life[0],x,y,null);
            }else {
                canvas.drawBitmap(life[1],x,y,null);
            }

        }


        canvas.drawBitmap(life[0],580,10,null);
        canvas.drawBitmap(life[0],640,10,null);
        canvas.drawBitmap(life[0],700,10,null);
    }



    public boolean hitBallCheckar(int x,int y){
        if (fishx<x && x< (fishx+fish[0].getWidth()) && fishy < y && y<( fishy+fish[0].getHeight())){
            return true;
        }
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
       if (event.getAction() == MotionEvent.ACTION_DOWN){
           touch=true;
           fishSpeed=-22;

       }
        return true;
    }
}
