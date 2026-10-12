package com.pngelectoral.utility.helper;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pngelectoral.R;


public class TabCustomView extends LinearLayout {

    private RelativeLayout tabInnerLayout;
    public ImageView tabImage;
    public TextView tabTitleTxt;

    public TabCustomView(Context context) {
        super(context);
    }

    public TabCustomView(Context context, int drawableInActive, int drawableActive,
                         String title, boolean haveBadge) {
        super(context);
        this.tabInnerLayout = (RelativeLayout) LayoutInflater.from(context).
                inflate(R.layout.component_tab_view, null);

        if (drawableActive != -1) {
            StateListDrawable listDrawable = new StateListDrawable();
            listDrawable.addState(SELECTED_STATE_SET, this.getResources().getDrawable(drawableActive));
            listDrawable.addState(ENABLED_STATE_SET, this.getResources().getDrawable(drawableInActive));

            tabImage = tabInnerLayout.findViewById(R.id.imgTabImageCTV);
            tabImage.setImageDrawable(listDrawable);
            StateListDrawable listBgDrawable = new StateListDrawable();
            tabInnerLayout.setBackground(listBgDrawable);
            tabTitleTxt = tabInnerLayout.findViewById(R.id.txtTabTitleCTV);
            tabTitleTxt.setText(title);
            tabTitleTxt.setGravity(Gravity.CENTER);
            setGravity(Gravity.CENTER);
        }


        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(
                android.R.attr.selectableItemBackground, outValue, true);
        tabInnerLayout.setBackgroundResource(outValue.resourceId);

        addView(tabInnerLayout);
    }


}