package com.example.me.haft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WorkoutSlidePageFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , @Nullable Bundle savedInstanceState) {



        ViewGroup rootView = (ViewGroup) inflater.inflate
                (R.layout.workout_slide_page, container, false);

        String name=getArguments().getString("name");
        String description=getArguments().getString("description");

        TextView nameTextView = (TextView) rootView.findViewById(R.id.exercise_name_textView);
        TextView descriptionTextView= (TextView) rootView.findViewById(R.id.desription_textView);
        ImageView IconView = (ImageView) rootView.findViewById(R.id.exercise_iconView);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.exercise_Imageview);

        TextView nameView=(TextView) rootView.findViewById(R.id.exercise_nameView);

        View exerciseLayout=rootView.findViewById(R.id.exercise_layout);
        View restLayout=rootView.findViewById(R.id.rest_page_layout);

        nameTextView.setText(name);
        descriptionTextView.setText(description);

        nameView.setText(name);

        if (getArguments().getBoolean("isRest")){
            exerciseLayout.setVisibility(View.GONE);
        }
        else{
            restLayout.setVisibility(View.GONE);
        }
        return rootView;
    }

    //TODO: unify nameView and nameTextView

}
