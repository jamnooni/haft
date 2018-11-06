package com.example.me.haft;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class LearnPagerFragment extends Fragment{

    ViewPager viewPager;

    WorkoutSet workoutSet;
    int selectedIndex=0;
    TextView textView;


    public LearnPagerFragment() {
    }

    public LearnPagerFragment(WorkoutSet workoutSet, int selectedIndex) {
        this.workoutSet = workoutSet;
        this.selectedIndex = selectedIndex;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.learn_pager_layout,container,false);

        //get viewPager view
        viewPager= (ViewPager) rootView.findViewById(R.id.pager);


        //set adapter
        viewPager.setAdapter(new ScreenSlidePagerAdapter(getFragmentManager()));

        //set page change listener
        viewPager.addOnPageChangeListener(new PagerChangeListener());

        //get title view
        textView= (TextView) rootView.findViewById(R.id.textView);

        //show selected slide
        viewPager.setCurrentItem(selectedIndex);


        return rootView;
    }



    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {


        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Workout workout= workoutSet.get(position);

            /*Bundle args = new Bundle();
            args.putString("name", workout.getName());
            args.putString("description",workout.getDescription());*/
            //args.putInt("imageId",workout.getImageId());

            Log.d("debug","description:"+workout.getDescription());

            LearnSlideFragment slideFragment=new LearnSlideFragment(workout.getName(),
                    workout.getDescription(),workout.getImageId());


            return slideFragment;
        }

        @Override
        public int getCount() {
            return workoutSet.size();
        }
    }

    private class PagerChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
        @Override
        public void onPageSelected(int position) {
            textView.setText(String.format("Excercise %d of %d",position+1,workoutSet.size()));
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    }

}
