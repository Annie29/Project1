package org.lauriewhite.project1;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Create an adapter to process images for a GridView.
 *
 * @author Laurie White
 * @version 27 July 2015
 * Original code taken from the sample at
 * http://developer.android.com/guide/topics/ui/layout/gridview.html#example
 *
 */
public class ImageAdapter extends ArrayAdapter {
    private Context mContext;
    private MovieCollection mData;

    public ImageAdapter(Context c, MovieCollection data) {
        super(c, R.layout.one_movie_layout, data.getData());
        Log.d("IMAGE ADAPTER !!!", "Creating an image adapter");
        mContext = c;
        mData = data;
    }

    public int getCount() {
        if (mData == null){
            return 0;
        }
        return mData.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @Override
    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d ("Image Adapter!!!!", " Entered getView");
        ImageView imageView;
        if (convertView == null) {
            /*// if it's not recycled, initialize some attributes
            LayoutInflater layoutInflater = ((Activity)mContext).getLayoutInflater();
            convertView = layoutInflater.inflate(R.layout.grid_view_posters, parent, false);
           // convertView.setLayoutParams(new GridView.LayoutParams(-1, -1));
            //convertView.setScaleType(ImageView.ScaleType.CENTER_CROP);
*/
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //imageView.setPadding(8, 8, 8, 8);
        }
        else {
            imageView = (ImageView) convertView;
        }

        String url = mContext.getString(R.string.API_URL_image_base)
                + mContext.getString(R.string.API_image_size_preferred)
                + mData.get(position).getPosterURL();
        Log.d("Image Adapter ", "!!! URL is " + url);
        Picasso.with(mContext)
                .load(url)
                .into(imageView);

        return imageView;


        //imageView.setImageResource(mThumbIds[position]);
        //return imageView;
    }

    /*// references to our images
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };*/
}