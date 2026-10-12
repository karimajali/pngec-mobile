package com.pngelectoral.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment implements CommonFragmentComponentHandler {

    public BaseActivity mActivity;

    public View rootView;

    public T binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
            rootView = binding.getRoot();
            init();
        } else {
            if (container != null)
                container.removeView(rootView);
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mActivity = (BaseActivity) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        if(mActivity!=null){
            mActivity.setCurrentFragment(this);
        }
    }

 /*   @Override
    public void onDestroyView() {
        if (rootView != null) {
            ViewGroup parentViewGroup = (ViewGroup) rootView.getParent();
            if (parentViewGroup != null) {
                parentViewGroup.removeView(rootView);
                rootView = null;
            }
        }
        super.onDestroyView();
    }*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            mActivity.setCurrentFragment(this);
        }catch (NullPointerException e){
            e.printStackTrace();
        }


    }


    public void push(BaseFragmentTab fragment, int containerId, Boolean addBackStack, Boolean animation) {
        mActivity.push(fragment, containerId, addBackStack, animation);
    }

    public void popAll() {
        mActivity.popAll();
    }

    public void popUpTo(String className) {
        mActivity.popUpTo(className);
    }

    public void pop() {
        mActivity.pop();
    }

    public void exitApp() {
        mActivity.finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}