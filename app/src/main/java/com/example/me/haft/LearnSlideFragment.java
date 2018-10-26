package com.example.me.haft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LearnSlideFragment extends Fragment {

    TextView titleView,descriptionView;
    ImageView imageView;

    String title,description;
    int imageId;


    public LearnSlideFragment() {
    }

    public LearnSlideFragment(String title, String description, int imageId) {
        this.title = title;
        this.description = description;
        this.imageId = imageId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.learn_slide_layout,container,false);



        titleView= (TextView) rootView.findViewById(R.id.title);
        descriptionView= (TextView) rootView.findViewById(R.id.description);
        imageView= (ImageView) rootView.findViewById(R.id.image);


        setData(title,description,imageId);


        return rootView;
    }

    public void setData(String title,String description,int imageId){
        titleView.setText(title);
        descriptionView.setText(description);
        //imageView.setImageResource(imageId);
    }
}
