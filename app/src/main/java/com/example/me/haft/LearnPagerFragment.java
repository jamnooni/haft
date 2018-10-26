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

        viewPager= (ViewPager) rootView.findViewById(R.id.pager);


        viewPager.setAdapter(new ScreenSlidePagerAdapter(getFragmentManager()));

        viewPager.addOnPageChangeListener(new PagerChangeListener());


        textView= (TextView) rootView.findViewById(R.id.textView);



        //insure completion of createView
        setData(workoutSet,selectedIndex);

        return rootView;
    }



    public void setData(WorkoutSet workoutSet,int selectedIndex){
        Log.d("debug","getView is null? "+(getView()==null));
        if (workoutSet!=null) {
            this.workoutSet = workoutSet;
            this.selectedIndex = selectedIndex;

            viewPager.setCurrentItem(selectedIndex);
        }
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
            textView.setText("Exercise"+" "+position+" "
                    +getString(R.string.of)+" "+workoutSet.size());
        }
        @Override
        public void onPageScrollStateChanged(int state) {}
    }

}
