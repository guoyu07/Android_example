package com.jeap.asmcos.jeapweather;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.os.CountDownTimer;
import com.jeap.asmcos.jeapweather.MainActivity2;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;






public class MainActivity extends ActionBarActivity
        {
    ImageView img;
    TextView text ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView)findViewById(R.id.avatarImageView);
        text = (TextView) findViewById(R.id.guide_content);










        img.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


                switch (event.getAction()) {


                    case MotionEvent.ACTION_MOVE:
                        img.setImageResource(R.drawable.a27263260);
                        text.setText("天气预报");

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(MainActivity.this, MainActivity2.class);  //从启动动画ui跳转到主ui
                                startActivity(intent);
                                MainActivity.this.finish();    // 结束启动动画界面
                            }
                        }, 2000);

                        break;

                    default:

                        break;
                }

                return true;
            }
        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
