package com.tonikamitv.loginregister.Retrofit.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.inthecheesefactory.thecheeselibrary.view.BaseCustomViewGroup;
import com.inthecheesefactory.thecheeselibrary.view.state.BundleSavedState;
import com.tonikamitv.loginregister.R;

/**
 * Created by nuuneoi on 11/16/2014.
 */
public class RetorfitListItem extends BaseCustomViewGroup {
    TextView txt_pass ;
    TextView txt_age ;
    TextView txt_username ;
    TextView txt_name ;
    TextView txt_id ;


    public RetorfitListItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public RetorfitListItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs, 0, 0);
    }

    public RetorfitListItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, 0);
    }

    @TargetApi(21)
    public RetorfitListItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initInflate();
        initInstances();
        initWithAttrs(attrs, defStyleAttr, defStyleRes);
    }

    private void initInflate() {
        inflate(getContext(), R.layout.list_item_user, this);
    }

    private void initInstances() {
         txt_pass = (TextView) findViewById(R.id.txt_pass);
         txt_age = (TextView) findViewById(R.id.txt_age);
         txt_username = (TextView) findViewById(R.id.txt_username);
         txt_name = (TextView) findViewById(R.id.txt_name);
         txt_id = (TextView) findViewById(R.id.txt_id);

        // findViewById here
    }

    private void initWithAttrs(AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        /*
        TypedArray a = getContext().getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.StyleableName,
                defStyleAttr, defStyleRes);

        try {

        } finally {
            a.recycle();
        }
        */
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();

        BundleSavedState savedState = new BundleSavedState(superState);
        // Save Instance State(s) here to the 'savedState.getBundle()'
        // for example,
        // savedState.getBundle().putString("key", value);

        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        BundleSavedState ss = (BundleSavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());

        Bundle bundle = ss.getBundle();
        // Restore State from bundle here
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = width *4/6;
        int newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(
                height,MeasureSpec.EXACTLY
        );
        super.onMeasure(widthMeasureSpec, newHeightMeasureSpec);
        setMeasuredDimension(width,height);

    }
    public  void  setNameText(String text){
        txt_name.setText(text);
    }
    public  void  setID(String text){
        txt_id.setText(text);
    }
    public  void  setAgeText(String text){
        txt_age.setText(text);
    }
    public  void  setPassText(String text){
        txt_pass.setText(text);
    }
    public  void  setUserText(String text){
        txt_username.setText(text);
    }
}
