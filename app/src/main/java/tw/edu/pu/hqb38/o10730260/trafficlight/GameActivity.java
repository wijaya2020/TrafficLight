package tw.edu.pu.hqb38.o10730260.trafficlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class GameActivity extends AppCompatActivity {

    GameSurfaceView GameSV;
    Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //設定全螢幕顯示
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        //設定螢幕為橫式
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_game);
        Intent it = getIntent();
        int GreenSec = it.getIntExtra("GreenSec",0);
        int YellowSec = it.getIntExtra("YellowSec",0);
        int RedSec = it.getIntExtra("RedSec",0);

        GameSV = (GameSurfaceView) findViewById(R.id.GameSV);

        //設定初始測試之燈號秒數

        //GameSV.SetLightSec(6,2,3);
        GameSV.SetLightSec(GreenSec,YellowSec,RedSec);

        handler= new Handler();

    }

    //利用手指觸控，控制小男孩走路
    public boolean onTouchEvent (MotionEvent event){
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            GameSV.BoyMoving = true;
            handler.post(runnable);
        }
        else if (event.getAction() == MotionEvent.ACTION_UP){
            GameSV.BoyMoving =  false;
            handler.removeCallbacks(runnable);  //銷毀執行緒
        }
        return true;
    }

    //處理小男孩走路
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Canvas canvas = GameSV.getHolder().lockCanvas();
            GameSV.drawSomething(canvas);
            GameSV.getHolder().unlockCanvasAndPost(canvas);
            handler.postDelayed(runnable, 50);
        }
    };
}
