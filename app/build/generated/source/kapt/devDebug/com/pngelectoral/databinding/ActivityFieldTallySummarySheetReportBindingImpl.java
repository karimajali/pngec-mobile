package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityFieldTallySummarySheetReportBindingImpl extends ActivityFieldTallySummarySheetReportBinding  {

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
        sViewsWithIds.put(R.id.rlDeviceReport, 5);
        sViewsWithIds.put(R.id.llSummaryHeader, 6);
        sViewsWithIds.put(R.id.llProvince, 7);
        sViewsWithIds.put(R.id.tvProvince, 8);
        sViewsWithIds.put(R.id.tvDistrict, 9);
        sViewsWithIds.put(R.id.tvLLG, 10);
        sViewsWithIds.put(R.id.tvWard, 11);
        sViewsWithIds.put(R.id.llLocation, 12);
        sViewsWithIds.put(R.id.tvLocation, 13);
        sViewsWithIds.put(R.id.tvDeviceId, 14);
        sViewsWithIds.put(R.id.tvDeviceReportRunTime, 15);
        sViewsWithIds.put(R.id.llVotersCount, 16);
        sViewsWithIds.put(R.id.tvUpdatedCount, 17);
        sViewsWithIds.put(R.id.tvGhostCount, 18);
        sViewsWithIds.put(R.id.tvDuplicateCount, 19);
        sViewsWithIds.put(R.id.tvTransferOutCount, 20);
        sViewsWithIds.put(R.id.tvDeceasedCount, 21);
        sViewsWithIds.put(R.id.tvUnderAgeCount, 22);
        sViewsWithIds.put(R.id.tvEditTotalCount, 23);
        sViewsWithIds.put(R.id.tvNewCount, 24);
        sViewsWithIds.put(R.id.tvWithInProvinceCount, 25);
        sViewsWithIds.put(R.id.tvFromDifferentProvinceCount, 26);
        sViewsWithIds.put(R.id.tvProvisionalCount, 27);
        sViewsWithIds.put(R.id.tvNewEnrolmentTotalCount, 28);
        sViewsWithIds.put(R.id.llGrandTotal, 29);
        sViewsWithIds.put(R.id.tvGrandTotalCount, 30);
        sViewsWithIds.put(R.id.llVotersPhotoCount, 31);
        sViewsWithIds.put(R.id.tvWithPreviousPhotoCount, 32);
        sViewsWithIds.put(R.id.tvNewPhotoCount, 33);
        sViewsWithIds.put(R.id.tvTotalWithPhotoCount, 34);
        sViewsWithIds.put(R.id.tvTotalWithNoPhotoCount, 35);
        sViewsWithIds.put(R.id.tvTotalRecordsCount, 36);
        sViewsWithIds.put(R.id.cardPrint, 37);
        sViewsWithIds.put(R.id.rlBottom, 38);
        sViewsWithIds.put(R.id.tvBottomData, 39);
        sViewsWithIds.put(R.id.rlProgressBar, 40);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityFieldTallySummarySheetReportBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 41, sIncludes, sViewsWithIds));
    }
    private ActivityFieldTallySummarySheetReportBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (com.google.android.material.button.MaterialButton) bindings[37]
            , (android.widget.LinearLayout) bindings[29]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[16]
            , (android.widget.LinearLayout) bindings[31]
            , (android.widget.RelativeLayout) bindings[38]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[40]
            , (android.widget.RelativeLayout) bindings[1]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[39]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[26]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[30]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[24]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[28]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[33]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[27]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[36]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[35]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[34]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[22]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[25]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[32]
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