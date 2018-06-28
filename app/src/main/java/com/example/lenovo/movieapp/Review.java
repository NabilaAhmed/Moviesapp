package com.example.lenovo.movieapp;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

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


public class Review extends AsyncTask<Integer, String, ArrayList<String>> {
ArrayAdapter arrayAdapter ;
     public    ArrayList<String> resultStrs = new ArrayList<>();
    private static final String TAG = "____________________________________________Re";
    String basePart = "https://api.themoviedb.org/3/movie/";
    static String changeeUrl = "/reviews?";
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String JsonStr = null;
   // String format = "jeson";
    Jsonparsing json;
    Context context;
    View  view;
    String line;
    private ListView ReviewListView;

    public Review(Context context, View view) {
        this.context = context;
        this.view = view;
    }

    // static public String hi;

    @Override
    protected ArrayList<String> doInBackground(Integer... integers) {
        String s = null;
        try {
            Uri uribuiltConnection = Uri.parse(basePart + GridViewFragment.h + changeeUrl + "api_key=980fda5d85eadbdfe3393b685de6fd26").buildUpon().build();

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

          // json = new Jsonparsing(JsonStr);


        }
        try {
            return reviewPasrsing(JsonStr);

        } catch (JSONException e) {

        }



        return null;
    }

    public ArrayList<String> reviewPasrsing(String hi) throws JSONException {

        final String AUTHOR = "author";
        final String CONTENT = "content";
        final String RESULTS = "results";

        JSONObject jsonFileObject = new JSONObject(hi);
        JSONArray MoviesArray = jsonFileObject.getJSONArray(RESULTS);
        String review = "";


        for (int i = 0; i < MoviesArray.length(); i++) {

            JSONObject aMovie = MoviesArray.getJSONObject(i);
            review = aMovie.getString(AUTHOR) + ":\n" + aMovie.getString(CONTENT) + "\n\n";


            resultStrs.add(review);
        }
//hi=resultStrs.toString();

        return resultStrs;
    }

    @Override
    protected void onPostExecute(ArrayList Auther_content) {
        // super.onPostExecute(strings);
        arrayAdapter = new ArrayAdapter(context, R.layout.list_item_review, R.id.TextOfReview, Auther_content);
        ReviewListView = (ListView) view.findViewById(R.id.reviewList);
        ReviewListView.setAdapter(arrayAdapter);
        setListViewHeightBasedOnChildren(ReviewListView);
    }
    private static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = ListView.MeasureSpec.makeMeasureSpec(listView.getWidth(), ListView.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, ListView.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
    }


 }
