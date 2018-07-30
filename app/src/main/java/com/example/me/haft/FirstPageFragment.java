package com.example.me.haft;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;



public class FirstPageFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.first_page, container, false);

        rootView.findViewById(R.id.start_btn).setOnClickListener(new ClickListener(1));
        rootView.findViewById(R.id.workouts_btn).setOnClickListener(new ClickListener(2));
        rootView.findViewById(R.id.circuits_btn).setOnClickListener(new ClickListener(3));
        rootView.findViewById(R.id.voice_over_btn).setOnClickListener(new ClickListener(4));
        /*WorkoutDBHandler dbHandler=new WorkoutDBHandler(getContext(),null,null,1);
        try {
            dbHandler.createDataBase();
            dbHandler.openDataBase();

        }
        catch (IOException e){
            e.printStackTrace();
        }*/

        return rootView;
    }

    private class ClickListener implements View.OnClickListener {

        private int index;

        public ClickListener(int indext) {
            this.index = indext;
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (index){
                case 1:
                    intent = new Intent(getActivity(), WorkoutActivity.class);
                    intent.putExtra("workout set", MainActivity.workoutSet);
                    getActivity().startActivityForResult(intent, 1);
                    break;
                default:
                    Toast.makeText(getContext(), " " + index, Toast.LENGTH_SHORT).show();
            }

        }
    }


}
