package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityDeviceOperatorReportSelectionBindingImpl extends ActivityDeviceOperatorReportSelectionBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlTopBar, 1);
        sViewsWithIds.put(R.id.btnBack, 2);
        sViewsWithIds.put(R.id.tvTitle, 3);
        sViewsWithIds.put(R.id.btnHome, 4);
        sViewsWithIds.put(R.id.rlStartDate, 5);
        sViewsWithIds.put(R.id.etStartDate, 6);
        sViewsWithIds.put(R.id.ivStartDate, 7);
        sViewsWithIds.put(R.id.rlEndDate, 8);
        sViewsWithIds.put(R.id.etEndDate, 9);
        sViewsWithIds.put(R.id.ivEndDate, 10);
        sViewsWithIds.put(R.id.llDPO, 11);
        sViewsWithIds.put(R.id.rlDPO, 12);
        sViewsWithIds.put(R.id.etDPO, 13);
        sViewsWithIds.put(R.id.ivDPO, 14);
        sViewsWithIds.put(R.id.llProvince, 15);
        sViewsWithIds.put(R.id.rlProvince, 16);
        sViewsWithIds.put(R.id.etProvince, 17);
        sViewsWithIds.put(R.id.ivProvince, 18);
        sViewsWithIds.put(R.id.rlDistrict, 19);
        sViewsWithIds.put(R.id.etDistrict, 20);
        sViewsWithIds.put(R.id.ivDistrict, 21);
        sViewsWithIds.put(R.id.rlLLG, 22);
        sViewsWithIds.put(R.id.etLLG, 23);
        sViewsWithIds.put(R.id.ivLLG, 24);
        sViewsWithIds.put(R.id.rlWardName, 25);
        sViewsWithIds.put(R.id.etWardName, 26);
        sViewsWithIds.put(R.id.ivWardName, 27);
        sViewsWithIds.put(R.id.rlLocation, 28);
        sViewsWithIds.put(R.id.etLocation, 29);
        sViewsWithIds.put(R.id.ivLocation, 30);
        sViewsWithIds.put(R.id.btnProceed, 31);
        sViewsWithIds.put(R.id.rlBottom, 32);
        sViewsWithIds.put(R.id.tvBottomData, 33);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDeviceOperatorReportSelectionBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 34, sIncludes, sViewsWithIds));
    }
    private ActivityDeviceOperatorReportSelectionBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatButton) bindings[31]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[29]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[26]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[21]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[10]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[24]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[30]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[18]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[7]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[27]
            , (android.widget.LinearLayout) bindings[11]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[32]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.widget.RelativeLayout) bindings[28]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[25]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[33]
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
        if (BR.registrationViewModel == variableId) {
            setRegistrationViewModel((com.pngelectoral.ui.viewModel.RegistrationViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setRegistrationViewModel(@Nullable com.pngelectoral.ui.viewModel.RegistrationViewModel RegistrationViewModel) {
        this.mRegistrationViewModel = RegistrationViewModel;
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
        flag 0 (0x1L): registrationViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}