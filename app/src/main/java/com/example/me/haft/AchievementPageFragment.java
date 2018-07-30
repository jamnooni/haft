package com.example.me.haft;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.me.haft.achievementslist.AchievementsAdapter;
import com.example.me.haft.achievementslist.EntryItem;
import com.example.me.haft.achievementslist.Item;
import com.example.me.haft.achievementslist.SectionItem;

import java.util.ArrayList;

public class AchievementPageFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.achievement_page,container,false);
        ListView listView= (ListView) rootView.findViewById(R.id.achivements_list);

        ArrayList<Item> items=new ArrayList<Item>();
        items.add(new SectionItem("7 MONTH CHALLENGE"));
        items.add(new EntryItem("Unbreak My Heart","restore your hearts by missing less than 3 workout days in a month",R.drawable.baseline_mood_black_18dp,false));
        items.add(new EntryItem("Creating A Habit","Stay on the 7 Month Challenge for 3 months",R.drawable.baseline_golf_course_black_18dp,false));
        items.add(new EntryItem("So Close I Can Taste It","Stay on the 7 Month Challenge for 6 months",R.drawable.baseline_directions_bike_black_18dp,true));
        items.add(new EntryItem("7 Month Champion","Complete the 7 Month Challenge",R.drawable.baseline_rowing_black_18dp,false));
        items.add(new SectionItem("CONSECUTIVE DAYS"));
        items.add(new EntryItem("Challenge Accepted","Finnish your first workout",R.drawable.baseline_smoking_rooms_black_18dp,true));


        AchievementsAdapter adapter=new AchievementsAdapter(getActivity(),items);
        listView.setAdapter(adapter);




        return rootView;
    }
}
