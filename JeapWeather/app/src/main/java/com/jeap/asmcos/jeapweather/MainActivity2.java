package com.jeap.asmcos.jeapweather;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;

import java.util.HashMap;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;




public class MainActivity2 extends ActionBarActivity
        implements TencentLocationListener {

    HashMap<String , String> city;
    private TencentLocationManager mLocationManager;
    private static TextView temp ;
    private static TextView city1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2);
        temp = (TextView) findViewById(R.id.temp_content);
        city1  = (TextView) findViewById(R.id.city1);;

        city1.setText ("BeiJing!");

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        city = new HashMap<String ,String >();
        city.put("北京市", "101010100");
        city.put("上海市" ,"101020100" );



        mLocationManager = TencentLocationManager.getInstance(this);
        // 设置坐标系为 gcj-02, 缺省坐标为 gcj-02, 所以通常不必进行如下调用
//        mLocationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);

        TencentLocationRequest request = TencentLocationRequest.create();

        // 修改定位请求参数, 周期为 5000 ms
        request.setInterval(5000);
        request.setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_POI);

        // 开始定位
        mLocationManager.requestLocationUpdates(request, this);





    }

    private class URLHTTPAsyncTask extends AsyncTask<String,Void, String> {
        TextView t ;
        public URLHTTPAsyncTask(TextView t){
            this.t = t;
        }
        protected String doInBackground(String...urls) {
            String data = null;


            try {
                    Document doc = Jsoup.connect(urls[0]).get();
                    Elements node = doc.getElementsByClass("days7");
                    Elements span  = node.get(0).getElementsByTag("span");


                    data = span.get(0).text();
                }catch (Exception e){
                    System.out.println(e);
            }
            return data;
        }

        protected void onPostExecute(String data) {
            t.setText(data);
        }

    }

    @Override
    public void onLocationChanged(TencentLocation location, int error,
                                  String reason) {
        String msg = null;
        if (error == TencentLocation.ERROR_OK) {
            // 定位成功
            StringBuilder sb = new StringBuilder();
            sb.append("(纬度=").append(location.getLatitude()).append(",经度=")
                    .append(location.getLongitude()).append(",精度=")
                    .append(location.getAccuracy()).append("), 来源=")
                    .append(location.getProvider()).append(", 地址=")
                    .append(location.getAddress()).append(",城市=")
                    .append(location.getCity());
            msg = sb.toString();

            String mycity = location.getCity();

            city1.setText(location.getCity()+location.getStreet());
            String url = "http://m.weather.com.cn/mweather/" + city.get(mycity) +".shtml";
            System.out.println(url);
            new URLHTTPAsyncTask(temp ).execute(url);



        } else {
            // 定位失败
            msg = "定位失败: " + reason;
        }
        System.out.println(msg);
        // TODO 不将回调信息打印到 UI
        // updateLocationStatus(msg);

    }

    @Override
    public void onStatusUpdate(String name, int status, String desc) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity2, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_activity2, container, false);
            return rootView;
        }
    }
}
