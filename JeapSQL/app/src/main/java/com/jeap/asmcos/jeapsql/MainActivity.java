package com.jeap.asmcos.jeapsql;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.ArrayList;
import java.util.Arrays;


class Item {

    private String city;
    private String code;

    public Item(String city, String code) {
        super();
        this.city = city;
        this.code = code;
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }
}


@Table(name = "CityInfo")
class CityInfo extends Model {

    public CityInfo(){
        super();
    }

    @Column(name = "Name")
    public String name;

    @Column(name = "Code")
    public String code;

}

class MyAdapter extends ArrayAdapter<Item> {

    private final Context context;
    private final ArrayList<Item> itemsArrayList;
   private int ResourceId;
    public MyAdapter( Context  context,int ResourceId, ArrayList<Item> itemsArrayList) {

        super(context,ResourceId , itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
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
        TextView labelView = (TextView) rowView.findViewById(R.id.label);
        TextView valueView = (TextView) rowView.findViewById(R.id.value);

        // 4. Set the text for textView
        labelView.setText(itemsArrayList.get(position).getCity());
        valueView.setText(itemsArrayList.get(position).getCode());

        // 5. return rowView
        return rowView;
    }
}


public class MainActivity extends ActionBarActivity {

    ListView mylist;
    MyAdapter listAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Item> items = new ArrayList<Item>();
        items.add(new Item("北京市","1010100"));
        items.add(new Item("上海市","1020100"));
        items.add(new Item("天津市","1030100"));

        mylist =(ListView) findViewById(R.id.listView);


       listAdapter = new MyAdapter(this,R.layout.list1,items);


        mylist.setAdapter(listAdapter);

        ActiveAndroid.initialize(this);

        int i ;
        for (i = 0 ; i < items.size();i++) {
            CityInfo a = new CityInfo();
            a.name = items.get(i).getCity();
            a.code = items.get(i).getCode();
            a.save();
        }
       CityInfo b =  new Select()
                .from(CityInfo.class)
                .where("code=?","1020100")
                .executeSingle();
        System.out.println(b.name);
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
