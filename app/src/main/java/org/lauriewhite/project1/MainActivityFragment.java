package org.lauriewhite.project1;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private MovieCollection mCollection;

    @Override
    public void onStart() {
        super.onStart();
        updatePosters();
        Log.d("MAIN FRAGMENT!!!", "In onStart");
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        updatePosters();
        Log.d("In onCreateView", "updated!!!" );
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    private void updatePosters() {
        Log.d("MAIN FRAGMENT!!!", "In updatePosters");
        FetchMoviesTask fetchMoviesTask = new FetchMoviesTask(getActivity());
        fetchMoviesTask.execute();
        Log.d("MAIN FRAGMENT!!!", "IS mCollection empty? " + (mCollection == null));
        //GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
        //gridView.setAdapter(new ImageAdapter(this.getActivity(), mCollection));
        /*mForecastAdapter =
                new ArrayAdapter<String>(
                        getActivity(), // The current context (this activity)
                        R.layout.list_item_forecast, // The name of the layout ID.
                        R.id.list_item_forecast_textview, // The ID of the textview to populate.
                        new ArrayList<String>());
*/
//        fetchMoviesTask.execute();
//        //  TODO: Finish this, show the movies.
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, Void> {

        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();


        private Context mContext;
        // Will contain the raw JSON response as a string.
        private String moviesJsonStr = null;

        public FetchMoviesTask(Context context) {
            mContext = context;
        }

        @Override
        /**
         * params[0] = zip code for location
         * params[1] = unit type (imperial/metric)
         */
        protected Void doInBackground(String... params) {

            // If there's no zip code, there's nothing to look up.  Verify size of params.
            // if (params.length == 0) {
            //     return null;
            // }

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            String format = "json";
            String units = "metric";

            try {
                // Construct the URL for the theMovieDB query
                // Possible parameters are available at TMDB's forecast API page, at
                // https://www.themoviedb.org/documentation/api/discover
                final String MOVIE_BASE_URL = getString(R.string.API_URL_base) + getString(R.string.API_discover);

                Log.d(LOG_TAG, "URL !!! is " + MOVIE_BASE_URL);
                //  TODO:  Update this to use shared preferences to get the sort order
                Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                        .appendQueryParameter(getString(R.string.API_sort_query),
                                getString(R.string.API_sort_by_pop))
                        .appendQueryParameter(getString(R.string.API_key_query),
                                getString(R.string.API_key))
                        .build();

                URL url = new URL(builtUri.toString());
                Log.d(LOG_TAG, "Final URL !!! is " + url.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    Log.d(LOG_TAG, "!!! input Stream is NULL");
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                    Log.d(LOG_TAG, "Read line!!! " + line);
                }

                Log.d(LOG_TAG, "Done with all reads!!! >" + buffer.toString() + "<");
                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                moviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            mCollection = new MovieCollection(moviesJsonStr);
            //return mCollection;
            // This will only happen if there was an error getting or parsing the forecast.
            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
//            GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
//            gridView.setAdapter(new ImageAdapter(getActivity(), movieDatas));
            Log.d(LOG_TAG, "!!!! In onPostExecute");
            //mCollection = movieDatas;
            mCollection = new MovieCollection(moviesJsonStr);
            GridView gridView = (GridView) getActivity().findViewById(R.id.gridview);
            gridView.setAdapter(new ImageAdapter(mContext, mCollection));

        }


    }


}
