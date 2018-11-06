package com.example.me.haft;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class LearnFragment extends Fragment {

    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container
            , Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.learn_page,container,false);

        gridView= (GridView) rootView.findViewById(R.id.gridview);


        Bundle bundle=getArguments();
        String[] names=bundle.getStringArray("workoutSets names");
        int[] iconIds=bundle.getIntArray("workoutSets iconIds");
        Log.d("debug","names="+names[0]);
        if (names!=null){
            setGridViewData(names,iconIds);
        }

        return rootView;
    }

    //sets adapter for the gridView in the fragment's layout and determines the gridView's
    //OnClickListener.onClick() to run the onGridItemClicked() method of the instance of
    //OnGridItemClickListener interface if the instance isn't null;
    public void setGridViewData(String[] names,int[] iconIds){

        LearnGridAdapter learnGridAdapter=new LearnGridAdapter(getActivity(),names,iconIds);
        gridView.setAdapter(learnGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (itemClickListener!=null){
                    itemClickListener.onGridItemClicked(i);
                }
            }
        });

    }

    //OnGridItemClickListener is an interface that contains onGridItemClickListener method
    //the interface has a private instance and a setter for it. if instance is not null the
    //method will be called in gridView.onItemClickListener. the method will be defined in
    //LearnActivity by calling the setter of instance, so there will be access to another
    //fragment (LearnPagerFragment). this structure is observer pattern.

    public interface OnGridItemClickListener{
        void onGridItemClicked(int position);
    }

    private OnGridItemClickListener itemClickListener;

    public void setItemClickListener(OnGridItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
