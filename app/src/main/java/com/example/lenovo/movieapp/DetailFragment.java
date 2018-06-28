package com.example.lenovo.movieapp;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.content.res.ColorStateList;
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
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ImageView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;

        import com.google.gson.Gson;
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
public class DetailFragment extends Fragment {

        String post ;
         TextView textView;
         ImageView imageView;
         View rootView;
         static ArrayList<String> strings=new ArrayList<>();
         static ArrayList<String> fav_ids =new ArrayList<>();
    ///
    String title;
    String release;
    String vote;
    String overview;

    static ArrayList<String> overviews=new ArrayList<>();
    static ArrayList<String> realsess =new ArrayList<>();
    static ArrayList<String> titless=new ArrayList<>();
    static ArrayList<String> votees =new ArrayList<>();

    ///


      public View onCreateView(LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState){

             rootView        = inflater.inflate(R.layout.fragment_details, container , false);
          imageView  = (ImageView)rootView.findViewById(R.id.small_image) ;
            textView    = (TextView)rootView.findViewById(R.id.overView);
        Button button= (Button) rootView.findViewById(R.id.d);

        final Intent intent = getActivity().getIntent();

//single
        if(intent != null && intent.hasExtra(intent.EXTRA_TEXT)){

           String overView = intent.getStringExtra(intent.EXTRA_TEXT);
//
            title= intent.getStringExtra("title");
            Log.e("xxxxxxxxxxxxxxxxx",title );
            release= intent.getStringExtra("release");
            vote=  intent.getStringExtra("vote");
            overview=intent.getStringExtra("overview");
            id=intent.getStringExtra("id");

//
           post=intent.getData().toString();
            Log.e(intent.getData().toString(),"ccccccccccccccccccccccccccc" );
            textView.setTextSize(20);
          //  textView.setTextColor(ColorStateList.valueOf(151515));
            textView.isScrollbarFadingEnabled();
            textView.setText(title+"\n"+release+"\n"+overview+"\n"+id+"\n");
//textView.setText(overView);
        }
//2 pane
        else  {


             title =getArguments().getString("oraginal");
             release=getArguments().getString("release");
             vote=getArguments().getString("vote");
             overview=getArguments().getString("overview");
             id=getArguments().getString("id");
            //this.id=id;
            final String poster=getArguments().getString("poster");
            post=poster;
            textView.setTextSize(20);
            textView.isScrollbarFadingEnabled();
            textView.setText(title+"\n"+release+"\n"+overview+"\n"+id+"\n");

        }

          //for both
          Review review=new Review(getActivity(),rootView);
          review.execute();
          Trailer trailer =new Trailer();
          trailer.execute();

          Picasso.with(getContext()).load(post).into(imageView);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

if (MainActivity.saved !=null)
strings=MainActivity.saved;
//                Log.e("ffffffffbuttonffffffffffffff",strings.toString() );
  //              Log.e("ffffffffbuttonffffffffffffff",fav_ids.toString() );

                if(!strings.contains(post)) {

                    strings.add(post);
                    fav_ids.add(id);

              /////
                    realsess.add(release);
                    votees.add(vote);
                    titless.add(title);
                    overviews.add(overview);
                    /////

                    SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    SharedPreferences.Editor editor = sharedPrefs.edit();
                    Gson gson = new Gson();

                    String json = gson.toJson(strings);
                    String ids=gson.toJson(fav_ids);


////
                    String titlesss = gson.toJson(titless);
                    String overess=gson.toJson(overviews);
                    String votess = gson.toJson(votees);
                    String releasess=gson.toJson(realsess);
                    ////
                    editor.putString("fav", json);
                    editor.putString("id",ids);
                    ////

                    editor.putString("title", titlesss);
                    editor.putString("overview",overess);
                    editor.putString("release", votess);
                    editor.putString("vote",releasess);

                    ////
                    editor.commit();


                   // Log.e("ffffffffbuttonffffffffffffff",strings.toString() );
                    //Log.e("ffffffffbuttonffffffffffffff",fav_ids.toString() );

                    // Log.e("ffffffffffffffffffffffffffffffff", intent.getData().toString());
                    //String poster_path=n;
                  }

            }
        });


        return rootView;
    }

    String id;

public class Trailer extends AsyncTask <Integer, String, ArrayList> {

     public    ArrayList<String> resultStrs = new ArrayList<>();
    String basePart = "https://api.themoviedb.org/3/movie/";
     String changeeUrl = "/videos?";
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String JsonStr = null;
   // String format = "jeson";
   // Jsonparsing json;
    String line;
    private ListView TrailerListView;

    private ArrayAdapter TrailerAdapter;
    //  private ListView listView;

    @Override
    protected ArrayList doInBackground(Integer... integers) {
        try {

            Uri uribuiltConnection = Uri.parse(basePart + GridViewFragment.h + changeeUrl).buildUpon().appendQueryParameter("api_key", "980fda5d85eadbdfe3393b685de6fd26").build();

            uribuiltConnection = Uri.parse(basePart +GridViewFragment.h + changeeUrl + "api_key=980fda5d85eadbdfe3393b685de6fd26").buildUpon().build();

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
            }}

        try{

            return getTrailersKeys(JsonStr);
        }

        catch (JSONException e) {
        }
        return null;



    }


    ArrayList<String> resultStrs2 = new ArrayList<>();
    private ArrayList getTrailersKeys(String JsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String MDB_KEY = "key";
        final String MDB_RESULTS = "results";
        final String MDB_NAME = "name";
        String name="";

        JSONObject jsonFileObject = new JSONObject(JsonStr);
        JSONArray MoviesArray = jsonFileObject.getJSONArray(MDB_RESULTS);
        String videoUrl="";
        ArrayList<String> resultStrs = new ArrayList<>();

        for (int i = 0; i < MoviesArray.length(); i++) {
            JSONObject aMovie = MoviesArray.getJSONObject(i);
            videoUrl = "https://www.youtube.com/watch?v=" +aMovie.getString(MDB_KEY);
            name=aMovie.getString(MDB_NAME);
            resultStrs2.add(name);
            resultStrs.add(videoUrl);
        }

        return resultStrs;
    }
    @Override
    protected void onPostExecute(final ArrayList ArrayTrailer) {
        ArrayList<String> strings = null;
        if (ArrayTrailer != null) {
/*
            try {
                strings = resultStrs2;

            } catch (JSONException e) {
                e.printStackTrace();
            }
*/
            TrailerAdapter = new ArrayAdapter(getActivity(), R.layout.list_item_trailer, R.id.TextOfTrailer, resultStrs2);
            TrailerListView = (ListView) rootView.findViewById(R.id.TrailerListView);
            TrailerListView.setAdapter(TrailerAdapter);
            setListViewHeightBasedOnChildren(TrailerListView);
            TrailerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(ArrayTrailer.get(position).toString()));
                    Log.e("fffffffffffffffffffff",ArrayTrailer.toString() );
                   startActivity(intent);
                }
            });





        }

    }
    private  void setListViewHeightBasedOnChildren(ListView listView) {
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
}