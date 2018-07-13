package com.example.me.haft;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutSlidePageFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate
                (R.layout.workout_slide_page, container, false);

        int index=getArguments().getInt("index");
        TextView textView= (TextView) rootView.findViewById(R.id.content_text_view);
        textView.setText("page number:"+index);

        int[] colors={Color.BLUE, Color.RED, Color.YELLOW, Color.GRAY};
        rootView.setBackgroundColor(colors[index%4]);

        return rootView;
    }
}
