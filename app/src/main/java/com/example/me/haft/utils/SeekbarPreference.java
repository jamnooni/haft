package com.example.me.haft.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.me.haft.R;

public class SeekbarPreference extends DialogPreference implements SeekBar.OnSeekBarChangeListener{

    private int progress =0;
    private SeekBar mSeekbar;
    private TextView mTextView;
    private String addText="";
    private int defaultVal,minVal,maxVal,step;
    public SeekbarPreference(Context context, AttributeSet attrs) {
        super(context,attrs);

        TypedArray a=context.obtainStyledAttributes(attrs,R.styleable.SeekbarPreference);
        CharSequence s=a.getString(R.styleable.SeekbarPreference_addText);
        if (s!=null){
            addText=s.toString();
        }
        defaultVal =a.getInteger(R.styleable.SeekbarPreference_defaultVal,0);
        minVal =a.getInteger(R.styleable.SeekbarPreference_minVal,0);
        maxVal =a.getInteger(R.styleable.SeekbarPreference_maxVal,10);
        step=a.getInteger(R.styleable.SeekbarPreference_step,1);

        setDialogLayoutResource(R.layout.time_seekbar);
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);



        setDialogIcon(null);
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        mSeekbar= (SeekBar) view.findViewById(R.id.progress_seekbar);
        mTextView=(TextView) view.findViewById(R.id.progress_text);

        mSeekbar.setProgress(progress);
        mTextView.setText(String.valueOf(minVal+(progress*step))+addText);

        mSeekbar.setOnSeekBarChangeListener(this);
        mSeekbar.setMax((maxVal-minVal)/step);


    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult){
            progress=mSeekbar.getProgress();

            String progressStr=String.valueOf(minVal+(progress*step))+addText;

            if (callChangeListener(progressStr))
                persistString(progressStr);
        }
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {
        String progressStr=null;

        if (restorePersistedValue){
            if (defaultValue==null) {
                progressStr = getPersistedString(defaultVal+addText);
            }
            else{
                progressStr=getPersistedString(defaultValue.toString());
            }
        }
        else{
            progressStr=defaultValue.toString();
        }

        progress=(Integer.valueOf(progressStr.substring(0,progressStr.length()
                -addText.length()))-minVal)/step;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        mTextView.setText(String.valueOf(minVal+(i*step))+addText);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

