package com.pngelectoral.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * @author srinivasulu battepadu
 * @About
 */
public abstract class GenericAdapter<T, D> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<T> mArrayList;


    public abstract int getLayoutId();

    public abstract void onBindData(T model, int position, D dataBinding);

    public GenericAdapter(List<T> arrayList) {
        this.mArrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getLayoutId(), parent, false);
        RecyclerView.ViewHolder holder = new ItemViewHolder(dataBinding);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        onBindData(mArrayList.get(position), position, ((ItemViewHolder) holder).mDataBinding);
    }

    @Override
    public int getItemCount() {
        return mArrayList == null ? 0 : mArrayList.size();
    }

    public void addItems(List<T> arrayList) {
        mArrayList.addAll(arrayList);
        this.notifyDataSetChanged();
    }

    public void clearItems() {
        mArrayList.clear();
        this.notifyDataSetChanged();
    }

    public void addItemsWithOutDuplicate(List<T> arrayList) {
        mArrayList.addAll(arrayList);
        this.notifyDataSetChanged();
    }


    public void removeAll() {
        mArrayList.clear();
        this.notifyDataSetChanged();
    }

    public void setData(List<T> arrayList) {
        mArrayList = arrayList;
        this.notifyDataSetChanged();
    }

    public T getItem(int position) {
        return mArrayList.get(position);
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        protected D mDataBinding;

        public ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mDataBinding = (D) binding;


        }
    }
}