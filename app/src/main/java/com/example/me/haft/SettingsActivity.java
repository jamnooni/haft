package com.example.me.haft;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{

    String[] keys={"every_day_reminder_time","lazy_reminder_time","lazy_reminder_text"
            , "every_day_reminder_text","sound_volume","rest_time","exercise_time"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        initializePreferences();

        //addPreferencesFromResource(R.xml.reminder);
    }

    private void initializePreferences() {
        SharedPreferences sp= PreferenceManager.getDefaultSharedPreferences(this);



        for (int i=0;i<keys.length;i++){
            String key=keys[i];
            findPreference(key).setSummary(sp.getString(key," "));
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sp, String key) {
        if (Arrays.asList(keys).contains(key)) {
            findPreference(key).setSummary(sp.getString(key, " "));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
