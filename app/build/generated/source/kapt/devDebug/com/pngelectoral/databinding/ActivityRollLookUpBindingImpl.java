package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityRollLookUpBindingImpl extends ActivityRollLookUpBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlTopBar, 1);
        sViewsWithIds.put(R.id.btnHome, 2);
        sViewsWithIds.put(R.id.tvTitle, 3);
        sViewsWithIds.put(R.id.etLastName, 4);
        sViewsWithIds.put(R.id.etFirstName, 5);
        sViewsWithIds.put(R.id.rlGender, 6);
        sViewsWithIds.put(R.id.etGender, 7);
        sViewsWithIds.put(R.id.ivGender, 8);
        sViewsWithIds.put(R.id.rlRegion, 9);
        sViewsWithIds.put(R.id.etRegion, 10);
        sViewsWithIds.put(R.id.ivRegion, 11);
        sViewsWithIds.put(R.id.rlProvince, 12);
        sViewsWithIds.put(R.id.etProvince, 13);
        sViewsWithIds.put(R.id.ivProvince, 14);
        sViewsWithIds.put(R.id.rlDistrict, 15);
        sViewsWithIds.put(R.id.etDistrict, 16);
        sViewsWithIds.put(R.id.ivDistrict, 17);
        sViewsWithIds.put(R.id.rlLLG, 18);
        sViewsWithIds.put(R.id.etLLG, 19);
        sViewsWithIds.put(R.id.ivLLG, 20);
        sViewsWithIds.put(R.id.rlWardName, 21);
        sViewsWithIds.put(R.id.etWardName, 22);
        sViewsWithIds.put(R.id.ivWardName, 23);
        sViewsWithIds.put(R.id.btnSearch, 24);
        sViewsWithIds.put(R.id.rlBottom, 25);
        sViewsWithIds.put(R.id.tvBottomData, 26);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityRollLookUpBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 27, sIncludes, sViewsWithIds));
    }
    private ActivityRollLookUpBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatButton) bindings[24]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[22]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[17]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[20]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[11]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[23]
            , (android.widget.RelativeLayout) bindings[25]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[26]
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
        if (BR.transferInViewModel == variableId) {
            setTransferInViewModel((com.pngelectoral.ui.viewModel.TransferInViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setTransferInViewModel(@Nullable com.pngelectoral.ui.viewModel.TransferInViewModel TransferInViewModel) {
        this.mTransferInViewModel = TransferInViewModel;
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
        flag 0 (0x1L): transferInViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}