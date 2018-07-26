package com.example.me.haft;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

        AchievementsAdapter adapter=new AchievementsAdapter(getActivity(),items);
        listView.setAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
