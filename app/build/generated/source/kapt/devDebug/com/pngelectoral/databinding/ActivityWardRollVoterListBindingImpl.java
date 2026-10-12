package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityWardRollVoterListBindingImpl extends ActivityWardRollVoterListBinding  {

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
        sViewsWithIds.put(R.id.btnPhotoSplitReport, 4);
        sViewsWithIds.put(R.id.btnGenderSplitReport, 5);
        sViewsWithIds.put(R.id.btnFilter, 6);
        sViewsWithIds.put(R.id.llSearch, 7);
        sViewsWithIds.put(R.id.etLastName, 8);
        sViewsWithIds.put(R.id.etFirstName, 9);
        sViewsWithIds.put(R.id.etElectorId, 10);
        sViewsWithIds.put(R.id.rlGender, 11);
        sViewsWithIds.put(R.id.etGender, 12);
        sViewsWithIds.put(R.id.ivGender, 13);
        sViewsWithIds.put(R.id.rvWardRollVotersList, 14);
        sViewsWithIds.put(R.id.tvNoVoters, 15);
        sViewsWithIds.put(R.id.rlBottom, 16);
        sViewsWithIds.put(R.id.tvBottomData, 17);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityWardRollVoterListBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds));
    }
    private ActivityWardRollVoterListBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[6]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[10]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[8]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[13]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[1]
            , (androidx.recyclerview.widget.RecyclerView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
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
        if (BR.wardRollViewModel == variableId) {
            setWardRollViewModel((com.pngelectoral.ui.viewModel.WardRollViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setWardRollViewModel(@Nullable com.pngelectoral.ui.viewModel.WardRollViewModel WardRollViewModel) {
        this.mWardRollViewModel = WardRollViewModel;
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
        flag 0 (0x1L): wardRollViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}