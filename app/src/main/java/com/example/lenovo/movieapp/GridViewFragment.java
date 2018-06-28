package com.example.lenovo.movieapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;

import java.util.ArrayList;

public class GridViewFragment extends Fragment {

    NameSelected nameSelected;
    public static GridviewAdapter GridViewAdapter ;
    public static String h;
    public static   getMovieObjects g;
    View rootview = null;
    String posterPath;
    String overView;
    String release_date ;
    String vote_average;
    String original_title;
    static boolean v=true;
    ArrayList<MovieObject> movieObjects=new ArrayList<>();
    public GridViewFragment(boolean b) {
    v=b;
    }

//int n;
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        rootview = inflater.inflate(R.layout.grid_view , container , false);
        final GridView  GridView = (GridView) rootview.findViewById(R.id.GridView);

       if(v) {
           GridViewAdapter =new GridviewAdapter(getActivity(),new ArrayList<MovieObject>());
           g = new getMovieObjects(GridViewAdapter); //oncreate play
           g.execute();
       }

        else {
if(DetailFragment.strings.contains(null)){}
else
           try {
     if (DetailFragment.strings.size() < MainActivity.saved.size()) {
        DetailFragment.fav_ids = MainActivity.saveID;
        DetailFragment.strings = MainActivity.saved;
         DetailFragment.overviews=MainActivity.savedOverView;
         DetailFragment.titless=MainActivity.savedTitle;
         DetailFragment.realsess=MainActivity.savedRelease;
         DetailFragment.votees=MainActivity.saveedvote;
    }
}catch (Exception ex){}
           for (int i = 0; i < DetailFragment.strings.size(); i++) {

               Log.e("string",DetailFragment.strings.toString() );
               movieObjects.add(new MovieObject(DetailFragment.titless.get(i), DetailFragment.realsess.get(i), DetailFragment.strings.get(i), DetailFragment.overviews.get(i), DetailFragment.votees.get(i), DetailFragment.fav_ids.get(i)));
           }
           GridViewAdapter = new GridviewAdapter(getActivity(), movieObjects);
       }


        //not fav


        GridView.setAdapter(GridViewAdapter );

        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieObject m = (MovieObject) GridViewAdapter.getItem(position);
                h=m.getID();
                posterPath = "http://image.tmdb.org/t/p/"+"w185"+m.getPOSTER_PATH();
                overView = m.getOVER_VIEW();
                original_title=m.getORIGINAL_TITLE();
                release_date=m.getRELEASE_DATE();
                vote_average=m.getVOT_AVERAGE();
                nameSelected.setTabJsonAndPosition(original_title,release_date,vote_average,overView,m.getID(),posterPath);




}});

        return rootview;
    }

    public void setName(NameSelected name){
nameSelected=name;
    }

}
