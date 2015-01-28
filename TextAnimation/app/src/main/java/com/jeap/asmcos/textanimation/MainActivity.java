package com.jeap.asmcos.textanimation;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import  com.jeap.asmcos.textanimation.SimpleGestureFilter.SimpleGestureListener;




class MyAdapter extends ArrayAdapter {
    private final Context context;
    private final Integer [] items;
    private int ResourceId;
    public MyAdapter( Context  context, int ResourceId, Integer [] items) {
        super(context,ResourceId , items);
        this.context = context;
        this.items = items;
        this.ResourceId = ResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // 2. Get rowView from inflater
        View rowView = inflater.inflate(ResourceId, parent, false);
        // 3. Get the two text view from the rowView
        TextView labelView = (TextView) rowView.findViewById(R.id.text1);

        // 4. Set the text for textView
        Integer a = items[position];
        switch(a.intValue())
        {
           case 2:
            labelView.setTextColor(Color.rgb(30, 30, 30));
            labelView.setBackgroundResource(R.drawable.cell_rectangle_2);

            break;
           case 4:
             labelView.setTextColor(Color.rgb(30, 30, 30));
             labelView.setBackgroundResource(R.drawable.cell_rectangle_4);

             break;

            case 8:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_8);
                break;

            case 16:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_16);
                break;

            case 32:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_32);
                break;
            case 64:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_64);

                break;
            case 128:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_128);
                break;

            case 256:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_256);
                break;

            case 512:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_512);
                break;

            case 1024:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_1024);
                break;

            case 2048:
                labelView.setBackgroundResource(R.drawable.cell_rectangle_2048);
                break;

           default:
                break;

        }


        if (a.intValue() != 0)
            labelView.setText(""+a);

        // 5. return rowView
        return rowView;
    }
}


public class MainActivity extends ActionBarActivity implements SimpleGestureListener {
    Animation tAnim;
    Button btnStart;
    GridView mygrid;
    MyAdapter  gridAdapter ;
    TextView temp = null;
    Integer []   items={ 0,8,0,0,
            0,2,2,0,
            0,4,0,0,
            0,16,0,32,};
    Integer[] b;
    private SimpleGestureFilter detector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detector = new SimpleGestureFilter(this,this);

      //  t = (TextView) findViewById(R.id.text1);
        btnStart = (Button) findViewById(R.id.button);
        tAnim =  AnimationUtils.loadAnimation(getApplicationContext(),
               R.anim.fade_in);



        mygrid =(GridView) findViewById(R.id.gridView1);



        gridAdapter = new MyAdapter(this,R.layout.list1,items);
        mygrid.setAdapter(gridAdapter);




        btnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
/*
        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                if (temp != null)
                    temp.clearAnimation();
                temp = (TextView) v;

                System.out.println(temp.getText());

                // start the animation
                temp.startAnimation(tAnim);

            }
        });

  */
    }



    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class

        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        String str = "";

        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT :
                b = items.clone();
                right();
                items = b.clone();
                add_random();

                str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :
                b = items.clone();
                left();
                items = b.clone();
                add_random();
                str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :
                b = items.clone();
                down();
                items = b.clone();
                add_random();
                str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :
                b = items.clone();
                up();
                items = b.clone();
                add_random();
                str = "Swipe Up";
                break;

        }
        gridAdapter = new MyAdapter(this,R.layout.list1,items);
        mygrid.setAdapter(gridAdapter);


        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
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


    public Void add_random(){
        Random r = new Random();
        int i=0;
        while (true) {
            i = r.nextInt(15);
            if (items[i] == 0)
                break;
        }
        items[i] = 2;
        return null;
    }

    public Void right(  ){
        int i,j;
        int t,t1;

        for (i = 0 ; i <  4 ;i++){
            for (j = 0 ; j <  3; j++){
                t  = b[j  + i * 4];
                t1 = b[j  + i * 4 + 1];
                if (t1 == 0  && t != 0  ) {
                    b[j  + i * 4 + 1] = t;
                    b[j  + i * 4] = 0;
                    right( );
                }
                if (t1 == t && t1 != 0) {
                    b[j  + i * 4 + 1] = t * 2;
                    b[j  + i * 4] = 0;
                    right( );
                }

            }
        }

        return null;
    }

    public Void left(  ){
        int i,j;
        int t,t1;

        for (i = 0 ; i <  4 ;i++){
            for (j = 0 ; j <  3; j++){
                t  = b[j  + i * 4];
                t1 = b[j  + i * 4 + 1];
                if (t == 0  && t1 != 0  ) {
                    b[j  + i * 4 + 1] = 0;
                    b[j  + i * 4] = t1;
                    left();
                }
                if (t1 == t && t1 != 0) {
                    b[j  + i * 4 + 1] = 0;
                    b[j  + i * 4] = t * 2;
                    left();
                }

            }
        }

        return null;
    }


    public Void up(  ){
        int i,j;
        int t,t1;

        for (i = 0 ; i <  4 ;i++){
            for (j = 0 ; j <  3; j++){
                t  = b[j * 4   + i ];
                t1 = b[(j + 1) * 4  + i ];
                if (t == 0  && t1 != 0  ) {
                    b[(j + 1) * 4  + i ] = 0;
                    b[j * 4  + i] = t1;
                    up();
                }
                if (t1 == t && t1 != 0) {
                    b[(j + 1) * 4  + i ] = 0;
                    b[j * 4  + i ] = t * 2;
                    up();
                }

            }
        }
        return null;
    }


    public Void down(  ){
        int i,j;
        int t,t1;

        for (i = 0 ; i <  4 ;i++){
            for (j = 0 ; j <  3; j++){
                t  = b[j * 4   + i ];
                t1 = b[(j + 1) * 4  + i ];
                if (t1 == 0  && t != 0  ) {
                    b[(j + 1) * 4  + i ] = t;
                    b[j * 4  + i] = 0;
                    down();
                }
                if (t1 == t && t1 != 0) {
                    b[(j + 1) * 4  + i ] = t * 2;
                    b[j * 4  + i ] = 0;
                    down();
                }

            }
        }
        return null;
    }


}
