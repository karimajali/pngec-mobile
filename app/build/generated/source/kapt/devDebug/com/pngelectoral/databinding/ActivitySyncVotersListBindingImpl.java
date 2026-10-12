package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivitySyncVotersListBindingImpl extends ActivitySyncVotersListBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlHeader, 1);
        sViewsWithIds.put(R.id.btnHome, 2);
        sViewsWithIds.put(R.id.tvTitle, 3);
        sViewsWithIds.put(R.id.btnFilter, 4);
        sViewsWithIds.put(R.id.rlMainContent, 5);
        sViewsWithIds.put(R.id.cbSelectAll, 6);
        sViewsWithIds.put(R.id.tvOr, 7);
        sViewsWithIds.put(R.id.cbSelectTwentyFive, 8);
        sViewsWithIds.put(R.id.rvSyncVotersList, 9);
        sViewsWithIds.put(R.id.cardSync, 10);
        sViewsWithIds.put(R.id.rlNoContent, 11);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySyncVotersListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds));
    }
    private ActivitySyncVotersListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (com.google.android.material.button.MaterialButton) bindings[10]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[6]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[8]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[11]
            , (androidx.recyclerview.widget.RecyclerView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            );
        this.mboundView0 = (android.widget.RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x2L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean setVariable(int variableId, @Nullable Object variable)  {
        boolean variableSet = true;
        if (BR.syncVotersListViewModel == variableId) {
            setSyncVotersListViewModel((com.pngelectoral.ui.viewModel.SyncVotersListViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSyncVotersListViewModel(@Nullable com.pngelectoral.ui.viewModel.SyncVotersListViewModel SyncVotersListViewModel) {
        this.mSyncVotersListViewModel = SyncVotersListViewModel;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;
    /* flag mapping
        flag 0 (0x1L): syncVotersListViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}