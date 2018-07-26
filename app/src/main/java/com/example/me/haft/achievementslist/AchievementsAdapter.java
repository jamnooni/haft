package com.example.me.haft.achievementslist;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.me.haft.R;

import java.util.ArrayList;

public class AchievementsAdapter extends ArrayAdapter{
    private Context context;
    private ArrayList<Item> items;
    private LayoutInflater inflater;

    public AchievementsAdapter(Context context,ArrayList<Item> items){
        super(context,0,items);
        this.context=context;
        this.items=items;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view=convertView;
        Item item=items.get(position);
        if(item!=null){
            if(item.isSection()){
                SectionItem sectionItem=(SectionItem) item;
                view=inflater.inflate(R.layout.achievement_section,null);
                TextView titleView= (TextView) view.findViewById(R.id.section_title);
                titleView.setText(sectionItem.getTitle());
            }
            else{
                EntryItem entryItem=(EntryItem)item;
                view=inflater.inflate(R.layout.achievement_item,null);
                TextView titleView= (TextView) view.findViewById(R.id.achievement_title);
                TextView descriptionView= (TextView) view.findViewById(R.id.achievement_description);
                ImageView iconView= (ImageView) view.findViewById(R.id.achievement_icon);
                titleView.setText(entryItem.getTitle());
                descriptionView.setText(entryItem.getDescription());
                iconView.setImageResource(entryItem.getIconId());
                if (entryItem.isUnlocked()){
                    view.findViewById(R.id.unlocked_icon).setVisibility(View.VISIBLE);
                }
            }
        }
        //TODO: set view clickable
        return view;
    }
}
