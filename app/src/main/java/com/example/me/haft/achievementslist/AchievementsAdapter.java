package com.example.me.haft.achievementslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.me.haft.R;

import java.util.ArrayList;

public class AchievementsAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater inflater;

    public AchievementsAdapter(Context context,ArrayList<Item> items){
        this.context=context;
        this.items=items;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        int type=1;
        if (items.get(position).isSection()){
            type=0;
        }
        return type;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView==null){
            if (getItemViewType(position)==0){
                SectionItem sectionItem=(SectionItem) items.get(position);
                convertView=inflater.inflate(R.layout.achievement_section,null);
                TextView titleView= (TextView) convertView.findViewById(R.id.section_title);
                titleView.setText(sectionItem.getTitle());
            }
            else{
                EntryItem entryItem=(EntryItem)items.get(position);
                convertView=inflater.inflate(R.layout.achievement_item,null);
                TextView titleView= (TextView) convertView.findViewById(R.id.achievement_title);
                TextView descriptionView= (TextView) convertView.findViewById(R.id.achievement_description);
                ImageView iconView= (ImageView) convertView.findViewById(R.id.achievement_icon);
                titleView.setText(entryItem.getTitle());
                descriptionView.setText(entryItem.getDescription());
                iconView.setImageResource(entryItem.getIconId());
                if (entryItem.isUnlocked()){
                    convertView.findViewById(R.id.unlocked_icon).setVisibility(View.VISIBLE);
                }
            }
        }
        //TODO: decrease inflates
        //TODO: set view clickable
        Log.d("debug","achvtAdapter.java>>getview called, position:"+position);
        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
