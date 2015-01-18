package com.example.asmcos.helloandroid;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends ActionBarActivity {

    ImageButton imagebutton1;
    static final String tuchongurl = "http://tuchong.com/512768/albums/0/";
    private String [] photos = new String [100];
    static int count = 0;
    static int display_pos = 0;


    private class URLAsyncTask extends AsyncTask<String,Void, Bitmap> {
        ImageButton img;

        public URLAsyncTask(ImageButton bmImage) {
            this.img = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            Bitmap png = null;
            try {
                InputStream in = new URL(urls[0]).openStream();
                png =  BitmapFactory.decodeStream(in);
                in.close();
                return png ;
            } catch(Exception e){
                System.out.println(e);
            }
            return png;
        }

        protected void onPostExecute(Bitmap result) {
            img.setImageBitmap(result);
        }
    }


    private class URLHTTPAsyncTask extends AsyncTask<String,Void, Void> {

        protected Void doInBackground(String...urls) {

            try {
                Document doc = Jsoup.connect(urls[0]).get();

                Elements nodes = doc.getElementsByClass("image-row");

                StringBuffer stringBuffer = new StringBuffer();
                int i = 0;
                for (i = 0; i < nodes.size(); i++) {

                    Element et = nodes.get(i).select("img[src]").first();

                    if (count <= 99) {
                        photos[count] = et.attr("src");
                        count ++ ;
                    }

                }

            }catch(Exception e)
            {
                System.out.println(e);
            }
            return null;
        }


    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagebutton1 = (ImageButton)findViewById(R.id.imagebutton1);



        new URLAsyncTask(imagebutton1).execute("http://photos.tuchong.com/512768/f/30867757.jpg");

        new URLHTTPAsyncTask().execute(tuchongurl);
        imagebutton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                new URLAsyncTask(imagebutton1).execute(photos[display_pos]);

                if( display_pos < count - 1){
                    display_pos ++ ;
                } else {
                    display_pos = 0;
                }

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
