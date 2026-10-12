package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivitySettingsBindingImpl extends ActivitySettingsBinding  {

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
        sViewsWithIds.put(R.id.rlSelectWorkingRollFilter, 4);
        sViewsWithIds.put(R.id.rlDownloadGeoStructureData, 5);
        sViewsWithIds.put(R.id.rlDownloadOccupationData, 6);
        sViewsWithIds.put(R.id.rlDownloadData, 7);
        sViewsWithIds.put(R.id.rlRejected, 8);
        sViewsWithIds.put(R.id.rlUpdatedVotersData, 9);
        sViewsWithIds.put(R.id.rlSyncedVotersData, 10);
        sViewsWithIds.put(R.id.rlReverseSync, 11);
        sViewsWithIds.put(R.id.rlLastDateOfEnrolment, 12);
        sViewsWithIds.put(R.id.rlProvisionalCutOffDate, 13);
        sViewsWithIds.put(R.id.rlIssueOfWritDate, 14);
        sViewsWithIds.put(R.id.rlGenderLessReport, 15);
        sViewsWithIds.put(R.id.rlDOReport, 16);
        sViewsWithIds.put(R.id.rlDOReportStaging, 17);
        sViewsWithIds.put(R.id.rlFieldTallySummeryReport, 18);
        sViewsWithIds.put(R.id.rlDOBAndStatusDiscrepancyReport, 19);
        sViewsWithIds.put(R.id.rlCopyDatabase, 20);
        sViewsWithIds.put(R.id.rlCopyImages, 21);
        sViewsWithIds.put(R.id.rlAuditSetting, 22);
        sViewsWithIds.put(R.id.rlBiometricTest, 23);
        sViewsWithIds.put(R.id.rlBottom, 24);
        sViewsWithIds.put(R.id.tvBottomData, 25);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivitySettingsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 26, sIncludes, sViewsWithIds));
    }
    private ActivitySettingsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.widget.RelativeLayout) bindings[23]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[20]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[17]
            , (android.widget.RelativeLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[5]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.RelativeLayout) bindings[15]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[14]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[8]
            , (android.widget.RelativeLayout) bindings[11]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[10]
            , (android.widget.RelativeLayout) bindings[9]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[25]
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
        if (BR.settingsViewModel == variableId) {
            setSettingsViewModel((com.pngelectoral.ui.viewModel.SettingsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setSettingsViewModel(@Nullable com.pngelectoral.ui.viewModel.SettingsViewModel SettingsViewModel) {
        this.mSettingsViewModel = SettingsViewModel;
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
        flag 0 (0x1L): settingsViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}