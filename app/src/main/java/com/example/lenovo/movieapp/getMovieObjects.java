package com.example.lenovo.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class getMovieObjects extends AsyncTask <Void , ArrayList<MovieObject>,ArrayList<MovieObject>> {

    String basePart = "https://api.themoviedb.org/3/movie/";
    ArrayList<MovieObject> Movie = new ArrayList<MovieObject>();
    static private String changeeUrl = "popular?";
    //Context context;
    GridviewAdapter GridViewAdapter;
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String JsonStr = null;
    String format = "jeson";
    Jsonparsing json;
    String line;

    public getMovieObjects(GridviewAdapter g) {
        GridViewAdapter = g;
    }


    @Override
    protected ArrayList<MovieObject> doInBackground(Void... params) {

        try {
            Uri uribuiltConnection = Uri.parse(basePart + changeeUrl).buildUpon().appendQueryParameter("api_key", "980fda5d85eadbdfe3393b685de6fd26").build();
            URL url = new URL(uribuiltConnection.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }


            JsonStr = buffer.toString();


        } catch (IOException e) {

           // Log.e("cccccccccccccccccccccccccccccc","ccccccccccccccccccccccccccc");

            while(json==null)
            {}
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                }
            }





                json = new Jsonparsing(JsonStr);

            try {
                Movie = json.getMovieObjects();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {

                return json.getMovieObjects();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return Movie;
        }
    }


    public void onPostExecute(ArrayList<MovieObject> Movies) {
        this.Movie = Movies;
        GridViewAdapter.update(Movies);
        GridViewAdapter.notifyDataSetChanged();

    }

    public void onPostExecute(boolean flage) {


            if (flage == true) {
                changeeUrl = "popular?";
            } else {
                changeeUrl = "top_rated?";
            }
        }


}

 class GridviewAdapter extends BaseAdapter {

     List<MovieObject> movieObject = new ArrayList<MovieObject>();
     Context context;
     int postion;

     public GridviewAdapter(Context context, ArrayList<MovieObject> movieObject) {
         this.movieObject = movieObject;
         this.context = context;
     }

     @Override
     public int getCount() {
         return movieObject.size();
     }

     @Override
     public Object getItem(int position) {
         return movieObject.get(position);
     }

     @Override
     public long getItemId(int position) {
         this.postion = position;
         return position;
     }

     public void update(ArrayList<MovieObject> movieObjects) {
         this.movieObject = movieObjects;
     }

     @Override
     public View getView(int position, View convertView, ViewGroup parent) {

         ImageView imageView;

         if (convertView == null) {

             imageView = new ImageView(this.context);
   if(MainActivity.mTwoPane) {
       imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
   }
             else{
       imageView.setLayoutParams(new GridView.LayoutParams(350, 350));

   }
         } else {
             imageView = (ImageView) convertView;

         }
         Log.e("ffff","i am here ");

         Picasso.with(context).load("http://image.tmdb.org/t/p/" + "w185" + movieObject.get(position).getPOSTER_PATH()).into(imageView);

         return imageView;
     }}


     class Jsonparsing {

         String jsonstr;

         public Jsonparsing(String jsonStr) {

             this.jsonstr = jsonStr;
         }

         public ArrayList<MovieObject> getMovieObjects() throws JSONException {

             ArrayList<MovieObject> MovieObjectf = new ArrayList<MovieObject>();
             String RESULTS = "results";
             String poster_path = "poster_path";
             String original_title = "original_title";
             String overview = "overview";
             String release_date = "release_date";
             String id = "id";

             MovieObject MO = null;

             JSONObject Movies = new JSONObject(this.jsonstr);
             JSONArray MovieArray = Movies.getJSONArray(RESULTS);
             for (int i = 0; i < MovieArray.length(); i++) {
                 MO = new MovieObject();
                 JSONObject objectData = MovieArray.getJSONObject(i);
                 MO.setPOSTER_PATH(objectData.getString(poster_path));
                 MO.setORIGINAL_TITLE(objectData.getString(original_title));
                 MO.setOVER_VIEW(objectData.getString(overview));
                 MO.setRELEASE_DATE(objectData.getString(release_date));
                 MO.setID(objectData.getString(id));
                 MovieObjectf.add(MO);

             }

             return MovieObjectf;
         }

         //parsing review




 }