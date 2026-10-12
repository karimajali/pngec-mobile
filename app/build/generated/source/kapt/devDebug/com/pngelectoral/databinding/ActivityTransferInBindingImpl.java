package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityTransferInBindingImpl extends ActivityTransferInBinding  {

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
        sViewsWithIds.put(R.id.llRegion, 9);
        sViewsWithIds.put(R.id.rlRegion, 10);
        sViewsWithIds.put(R.id.etRegion, 11);
        sViewsWithIds.put(R.id.ivRegion, 12);
        sViewsWithIds.put(R.id.llProvince, 13);
        sViewsWithIds.put(R.id.rlProvince, 14);
        sViewsWithIds.put(R.id.etProvince, 15);
        sViewsWithIds.put(R.id.ivProvince, 16);
        sViewsWithIds.put(R.id.rlDistrict, 17);
        sViewsWithIds.put(R.id.etDistrict, 18);
        sViewsWithIds.put(R.id.ivDistrict, 19);
        sViewsWithIds.put(R.id.rlLLG, 20);
        sViewsWithIds.put(R.id.etLLG, 21);
        sViewsWithIds.put(R.id.ivLLG, 22);
        sViewsWithIds.put(R.id.rlWardName, 23);
        sViewsWithIds.put(R.id.etWardName, 24);
        sViewsWithIds.put(R.id.ivWardName, 25);
        sViewsWithIds.put(R.id.btnSearch, 26);
        sViewsWithIds.put(R.id.rlBottom, 27);
        sViewsWithIds.put(R.id.tvBottomData, 28);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityTransferInBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds));
    }
    private ActivityTransferInBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatButton) bindings[26]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[21]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[24]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[19]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[22]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[16]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[12]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[25]
            , (android.widget.LinearLayout) bindings[13]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[27]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[23]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[28]
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