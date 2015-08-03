package org.lauriewhite.project1;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A class to represent the data retrieved on all movies.
 * This class is responsible for parsing all of the JSON data
 * about the collection.
 * @author Laurie White
 * @version 7/27/2015.
 */
//  TODO: Remove vestiges of trying to make this a List
public class MovieCollection  implements Iterable<MovieData> {
    /**
     * Create a new MovieCollection from a string representing its JSON object.
     * @param jsonData the JSON object's representation
     */
    public MovieCollection(String jsonData){
        mData = new ArrayList<MovieData>();
        //  TODO: Consider changing the parameter to a JSON object.
        try {
            getMovieDataFromJson(jsonData);
        }
        catch (org.json.JSONException e)
        {
            Log.d("Movie Collection!!!",  "You still don't get JSON!");
        }
        Log.d("Movie Collection!!!", "Done constructing, there are " + mData.size() + " items");
    }

    /**
     * Get the value at the given index or null if there is no such index
     * in the collection
     * @param index
     * @return the MovieData at the given position
     */
    public MovieData get(int index)
    {
        if (index >= 0 && index < mData.size()) {
            return mData.get(index);
        }
        return null;
    }

    /**
     * How many movies are in this collection?
     * @return
     */
    public int size() {
        if (mData == null)
            return 0;
        return mData.size();
    }

    public List<MovieData> getData(){
        return mData;
    }

    /**
     * Take the String representing all the movies in JSON Format and
     * pull out the data we need to construct the Strings needed for the wireframes.
     *
     * Fortunately parsing is easy:  constructor takes the JSON string and converts it
     * into an Object hierarchy for us.
     */
    //  TODO: Should this return MovieDatas?  or just update the instance field?
    private void getMovieDataFromJson(String moviesJsonStr)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String TMD_LIST = "results";

        JSONObject movieJson = new JSONObject(moviesJsonStr);
        JSONArray movieArray = movieJson.getJSONArray(TMD_LIST);

        // The MovieDatabase (themoviedb.org) returns
        //  TODO:  Fix this up, based on the actual stuff that gets returned!

        // Go through the JSON objects representing each movie
        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject oneMovieJson = movieArray.getJSONObject(i);
            MovieData oneMovie = new MovieData(oneMovieJson);
            if (oneMovie != null) {
                mData.add(oneMovie);
            }
        }
    }



    /**
     * To allow iteration through the movies in this collection.
     * @return an iterator on the collection
     */
    public Iterator<MovieData> iterator() {
        return new MovieDataIterator();
    }

    private List<MovieData> mData = null;

    private class MovieDataIterator implements Iterator<MovieData> {
        private int mCurrent;

        public MovieDataIterator() {
            mCurrent = 0;
        }


        @Override
        public boolean hasNext() {
            return mCurrent < mData.size();
        }

        @Override
        public MovieData next() {
            if (mData.size() == 0)
                return null;

            MovieData result = mData.get(mCurrent);
            if (hasNext())
            {
                mCurrent++;
            }
            else
            {
                mCurrent = 0;
            }
            return result;
        }

        @Override
        public void remove() {

        }


    }

}
