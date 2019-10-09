package com.vlasova.retrofit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class newsAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflater;
    ArrayList<HashMap<String, String>> articleArrayList;

    newsAdapter(Context context, ArrayList<HashMap<String, String>> arrayList ){
        this.context = context;
        articleArrayList = arrayList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return articleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return articleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null){
            view = inflater.inflate(R.layout.news, parent, false);

            HashMap<String, String> hashMap = articleArrayList.get(position);
            ImageView img = view.findViewById(R.id.img);
            String s = hashMap.get("getUrlToImage");
            Picasso.get()
                    .load(s).placeholder(R.drawable.ic_launcher_background)
                    .fit()
                    .centerCrop()
                    .into(img);
            ((TextView) view.findViewById(R.id.title)).setText(hashMap.get("title"));
            ((TextView) view.findViewById(R.id.description)).setText(hashMap.get("description"));
        }

        return view;
    }

    /*ArrayList<HashMap<String, String>> getArrayList(){
        ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();

    }*/
}
