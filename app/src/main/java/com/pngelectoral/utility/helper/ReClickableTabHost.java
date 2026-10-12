package com.pngelectoral.utility.helper;

import android.content.Context;
import android.util.AttributeSet;

import androidx.fragment.app.FragmentTabHost;

public class ReClickableTabHost extends FragmentTabHost {

    private ReClickListener reClickListener;

    public void setReClickListener(ReClickListener reClickListener) {
        this.reClickListener = reClickListener;
    }

    public ReClickableTabHost(Context context) {
        super(context);
    }

    public ReClickableTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setCurrentTab(int index) {
        if (index == getCurrentTab() && reClickListener != null) {
            reClickListener.reClickedTabId(index);
        } else {
            if(reClickListener!=null){
                reClickListener.firstClick(index);
            }
            super.setCurrentTab(index);
        }

    }
}
