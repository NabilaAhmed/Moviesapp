package com.example.lenovo.movieapp;



import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements NameSelected{
// switch between tab and phone
 static  ArrayList<String> saved ;
    static ArrayList<String> saveID;
    static ArrayList<String> savedTitle;
    static ArrayList<String> savedRelease;
    static ArrayList<String> savedOverView;
    static ArrayList<String> saveedvote;
   public static boolean mTwoPane;
    GridViewFragment gridViewFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
try {
    super.onCreate(savedInstanceState);
}
catch (Exception c){

}


            setContentView(R.layout.activity_main);


        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        String json = sharedPrefs.getString("fav", null);
        String idfav = sharedPrefs.getString("id", null);
        ////
        String titlefav = sharedPrefs.getString("title", null);
        String overviewfav = sharedPrefs.getString("overview", null);
        String releasefav = sharedPrefs.getString("release", null);
        String votefav = sharedPrefs.getString("vote", null);


        ////

        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> arrayList = gson.fromJson(json, type);

        Type typeid = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> idfavs = gson.fromJson(idfav, typeid);

///
        Type typetitle = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> titles = gson.fromJson(titlefav, typetitle);

        Type typover = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> overs = gson.fromJson(overviewfav, typover);
        Type typereleases = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> releases = gson.fromJson(releasefav, typereleases);

        Type typevote = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String>  vots= gson.fromJson(votefav, typevote);

//        Log.e("Hello",arrayList.toString());

        saved=arrayList;
        saveID=idfavs;
        savedTitle=titles;
        savedRelease=releases;
        savedOverView=overs;
        saveedvote=vots;


        ///





//        Log.e("Hello",arrayList.toString());




      //      Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);


            FrameLayout frameLayoutpane2= (FrameLayout) findViewById(R.id.fr2);

            if(null==frameLayoutpane2){mTwoPane=false;}
            else {mTwoPane=true;}

        if (null == savedInstanceState && boo) {
            gridViewFragment = new GridViewFragment(true);
            gridViewFragment.setName(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fr1, gridViewFragment).commit();
        }

        else {
            gridViewFragment = new GridViewFragment(false);
            gridViewFragment.setName(this);
            getSupportFragmentManager().beginTransaction().add(R.id.fr1, gridViewFragment).commit();
        }

                                                        }



//Sttings Options

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menu){
            startActivity(new Intent(this,SettingsActivity.class));
            Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                                      }


              return super.onOptionsItemSelected(item);
                                                        }
  static   String sort;

    @Override
    protected void onRestart() {

        super.onRestart();
        SharedPreferences sortAs= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        sort=sortAs.getString("Settings","Pop");
        if(sort.contentEquals("Pop")){
            boo=true;
            gridViewFragment.g.onPostExecute(true);
            startActivity(new Intent(this,MainActivity.class));
                                        }
        else
            if(sort.contentEquals("Top"))
            {
                boo=true;
            Toast.makeText(MainActivity.this, "TopRated", Toast.LENGTH_SHORT).show();
            gridViewFragment.g.onPostExecute(false);
            startActivity(new Intent(this,MainActivity.class));
            }
            else
                if (sort.contentEquals("Fav")) {

                    boo=false;
                    startActivity(new Intent(this,MainActivity.class));}
    }




static  boolean boo=true;
    @Override
    public void setTabJsonAndPosition(String orignal_title, String release_date, String vote, String over_view, String id, String poster) {
// one pane
        if(!mTwoPane)
        {
           // Log.e("ccccccccccccccccccccccc" ,"iam go");
        Intent intent = new Intent(this ,DetailActivity.class).putExtra(Intent.EXTRA_TEXT,"Original Title :"+orignal_title+"\n"+"Release Date :"+release_date+"\n"+"Vote Average :"+vote+"\n"+"Over View :"+over_view+"\n"+"id :"+id+"\n");
       // intent.putExtra("id ",id);

            intent.putExtra("title",orignal_title);
            intent.putExtra("release",release_date);
            intent.putExtra("vote",vote);
            intent.putExtra("overview",over_view);
            Log.e("vvvvvvvvvvvvvvvvv",id );
            intent.putExtra("id",id);
            intent.setData(Uri.parse(poster));
        startActivity(intent);
        }
        //two pane
else {
         //   Log.e("ccccccccccccccccccccccc" ,"iam g000o");

            DetailFragment detailFragment=new DetailFragment();
            Bundle bundle =new Bundle();
            bundle.putString("oraginal",orignal_title);
            bundle.putString("release",release_date);
            bundle.putString("vote",vote);
            bundle.putString("overview",over_view);
            bundle.putString("id",id);
            bundle.putString("poster",poster);
            detailFragment.setArguments(bundle);
            android.support.v4.app.Fragment topFragment = getSupportFragmentManager().findFragmentById(R.id.fr2);
            if (topFragment != null) {
                getSupportFragmentManager().beginTransaction().remove(topFragment).commit();
            }
            getSupportFragmentManager().beginTransaction().add(R.id.fr2,detailFragment).commit();
        }

    }
}
