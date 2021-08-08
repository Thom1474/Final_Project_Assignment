package com.example.final_project_assignment;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
/*
* Soccer games Api
* Submitted by Alan Thomas
* CST2335_022
* Final Project
* */
public class Activity_Soccer_games extends AppCompatActivity {
    String StringData;
    ArrayList<Soccer_News> Items = new ArrayList<>();
    MyAdapterData adapt = new MyAdapterData();
    Boolean Mobile_Device;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soccer_layout);
        FrameLayout SoccerLayout = findViewById(R.id.SoccerfragmentLocation);
        Toolbar Soccer_T_Bar = findViewById(R.id.Soccerbar);
        setSupportActionBar(Soccer_T_Bar);

        SharedPreferences Popup = getSharedPreferences("SharedPref", MODE_PRIVATE);
        StringData = Popup.getString("rating", "");
        Mobile_Device = SoccerLayout == null;
        AlertDialog.Builder alert = new AlertDialog.Builder(Activity_Soccer_games.this);
        alert.setTitle(getString(R.string.Rating));
        final EditText retrive = new EditText(Activity_Soccer_games.this);
        retrive.setText(StringData);
        LinearLayout.LayoutParams linear_layout = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        retrive.setLayoutParams(linear_layout);
        alert.setView(retrive);
        alert.setPositiveButton(getString(R.string.Yes),(dialog,which)->{
            StringData = retrive.getText().toString();
            dialog.cancel();
        });
        alert.show();

        ListView mySoccerList = findViewById(R.id.ListView1);
        mySoccerList.setAdapter(adapt);
        mySoccerList.setOnItemClickListener((parent, view, position, id)->{
            Bundle dataToPass = new Bundle();
            dataToPass.putString("Title", Items.get(position).getTitle() );
            dataToPass.putString("Date", Items.get(position).getDate() );
            dataToPass.putString("Image", Items.get(position).getImage() );
            if(!Mobile_Device)
            {
                Fragment_Soccer Fragment_Data = new Fragment_Soccer();
                Fragment_Data.setArguments( dataToPass );
                getSupportFragmentManager().beginTransaction().replace(R.id.SoccerfragmentLocation, Fragment_Data).commit();
            }
            else
            {
                Intent forward = new Intent(Activity_Soccer_games.this, Empty_data.class);
                forward.putExtras(dataToPass);
               startActivityForResult(forward,100);
            }

        });



        SoccerQuery sQuery = new SoccerQuery();
        sQuery.execute("https://www.goal.com/en/feeds/news?fmt=rss");

    }

    @Override
    protected void onPause() {

        super.onPause();
        SharedPreferences popdown = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        SharedPreferences.Editor TextEdit =popdown.edit();
        TextEdit.putString("rating",StringData);
        TextEdit.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater infl = getMenuInflater();
        infl.inflate(R.menu.soccer_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem I) {
        String Soccer_Message = null;
        switch(I.getItemId())
        {
            case R.id.item1:
                finish();
                Soccer_Message = getString(R.string.sccoerhome);
                break;
            case R.id.item2:
                androidx.appcompat.app.AlertDialog.Builder build_b1 = new androidx.appcompat.app.AlertDialog.Builder(this);
                build_b1.setTitle(getString(R.string.Instr))
                        .setMessage(getString(R.string.Ins)).setCancelable(false).setPositiveButton(getString(R.string.OK),
                        (click, arg) -> {
                        })
                        .create().show();
                Soccer_Message = getString(R.string.shelp);
                break;
            case R.id.item3:
                Intent favouriteNav = new Intent(Activity_Soccer_games.this, Favourites_page.class);
                startActivity ( favouriteNav );
                Soccer_Message = getString(R.string.sFavo);
                break;
        }
        Toast.makeText(this, Soccer_Message, Toast.LENGTH_LONG).show();

        return true;
    }
    private class SoccerQuery extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... str) {
            try{
                URL url= new URL(str[0]);
                HttpURLConnection urlConnection=(HttpURLConnection) url.openConnection();
                InputStream response=urlConnection.getInputStream();
                XmlPullParserFactory f = XmlPullParserFactory.newInstance();
                f.setNamespaceAware(false);
                XmlPullParser pareser=f.newPullParser();
                pareser.setInput(response,"UTF-8");

                Soccer_News news=null;
                int status=0;
                int Type_of_event=pareser.getEventType();
                while(Type_of_event!=XmlPullParser.END_DOCUMENT) {
                    if (Type_of_event == XmlPullParser.START_TAG) {
                        if(pareser.getName().equals("item")){
                            news=new Soccer_News();
                        }
                        else if(pareser.getName().equals("title")){
                            if(news!=null) {
                                status = 1;
                            }
                        }else if(pareser.getName().equals("pubDate")){
                            if(news!=null) {
                                status = 2;
                            }
                        }
                        else if(pareser.getName().equals("media:thumbnail")){
                            news.Snews_image=pareser.getAttributeValue(null,"url");
                        }

                    }else if(Type_of_event ==  XmlPullParser.TEXT){
                        if(status==1){
                            news.Snews_title=pareser.getText();
                        }else if(status==2){
                            news.Snews_date=pareser.getText();
                        }
                        status=0;

                    }else if(Type_of_event ==  XmlPullParser.END_TAG){
                        if(pareser.getName().equals("item")){
                            Items.add(news);
                        }

                    }
                    Type_of_event = pareser.next();
                }

                publishProgress(100);

            }
            catch (MalformedURLException e) {

                e.printStackTrace();

            }
            catch (IOException e) {

                e.printStackTrace();
            }
            catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... Ival) {
            super.onProgressUpdate(Ival);
        }

        @Override
        protected void onPostExecute(String ScExecute) {
            super.onPostExecute(ScExecute);
            adapt.notifyDataSetChanged();
        }
    }


    private class MyAdapterData extends BaseAdapter {

        public int getCount() {
            return Items.size();
        }

        public Object getItem(int position) {
            return Items.get(position);
        }

        public long getItemId(int position) {
            return position;
        }


        public View getView(int position, View old, ViewGroup parent) {

            LayoutInflater infl = getLayoutInflater();
            Soccer_News news = Items.get(position);
            View newView = null;

            newView = infl.inflate(R.layout.news_layout, parent, false);
            TextView tView = newView.findViewById(R.id.newTxt);
            tView.setText(news.Snews_title);

            return newView;
        }
    }
}
