package com.jeap.asmcos.jeapsms;

import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MainActivity extends ActionBarActivity {

    Button btn ;
    private EditText numId;
    String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn   = (Button) findViewById(R.id.button);
        numId = (EditText) findViewById(R.id.editText);

        /***********************/

        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            System.out.println("name:"+name+"phone:"+phoneNumber);

        }
        phones.close();

        /*****************************/
        btn.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                url = "http://android.shixisheng.cc/display_sms/" +  numId.getText();
                new URLHTTPAsyncTask().execute(url);


            }
        });

        /******************************/


    }

    private class URLHTTPAsyncTask extends AsyncTask<String,Void, String> {


        protected String doInBackground(String...urls) {
            String data = null;


            try {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements name = doc.getElementsByClass("name");
                Elements phone  = doc.getElementsByClass("phone");
                Elements content  =  doc.getElementsByClass("content");


                //data = span.get(0).text();
                System.out.println(phone.get(0).text());
                sendSMS(phone.get(0).text(), name.get(0).text() +":"+content.get(0).text());
            }catch (Exception e){
                System.out.println(e);
            }
            return data;
        }

        protected void onPostExecute(String data) {

        }

    }


    private void sendSMS(String phoneNumber, String message)
    {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, null, null);
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
