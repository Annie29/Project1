package org.lauriewhite.project1;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A class to represent the data about one movie.
 * This class is responsible for parsing the JSON data
 * about a single movie.  As the project is expanded,
 * this class will also expand to hold more information.
 * @author Laurie White
 * @version 7/27/2015.
 */
public class MovieData {

    private final String LOG_TAG = MovieData.class.getSimpleName();

    public MovieData (JSONObject jsonData) {
        final String TMD_title = "original_title";
        final String TMD_image = "poster_path";
        try{
            mTitle = jsonData.getString(TMD_title);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "No name for movie");
        }
        try{
            mPoster = jsonData.getString(TMD_image);
        } catch (JSONException e) {
            Log.w(LOG_TAG, "No poster for movie");
        }
    }

    private String mPoster;
    //  TODO: Well, this is interesting...since I can't access the element,
    //  I can't build the whole API, dammit

    /**
     * Give the relative path to the poster for this movie
     * @return the relative path to the poster
     */
    public String getPosterURL() {
        return mPoster;
    }

    private String mTitle;
    public String getTitle() {
        return mTitle;
    }
}
