package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityDpoGeoStructureUpdateBindingImpl extends ActivityDpoGeoStructureUpdateBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlTopBar, 1);
        sViewsWithIds.put(R.id.tvTitle, 2);
        sViewsWithIds.put(R.id.rlRegion, 3);
        sViewsWithIds.put(R.id.etRegion, 4);
        sViewsWithIds.put(R.id.ivLocation, 5);
        sViewsWithIds.put(R.id.rlProvince, 6);
        sViewsWithIds.put(R.id.etProvince, 7);
        sViewsWithIds.put(R.id.ivProvince, 8);
        sViewsWithIds.put(R.id.rlDistrict, 9);
        sViewsWithIds.put(R.id.etDistrict, 10);
        sViewsWithIds.put(R.id.ivDistrict, 11);
        sViewsWithIds.put(R.id.rlLLG, 12);
        sViewsWithIds.put(R.id.etLLG, 13);
        sViewsWithIds.put(R.id.ivLLG, 14);
        sViewsWithIds.put(R.id.rlWardName, 15);
        sViewsWithIds.put(R.id.etWardName, 16);
        sViewsWithIds.put(R.id.ivWardName, 17);
        sViewsWithIds.put(R.id.rlLocation, 18);
        sViewsWithIds.put(R.id.etLocation, 19);
        sViewsWithIds.put(R.id.ivRegion, 20);
        sViewsWithIds.put(R.id.btnSave, 21);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDpoGeoStructureUpdateBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 22, sIncludes, sViewsWithIds));
    }
    private ActivityDpoGeoStructureUpdateBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatButton) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[11]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[14]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[5]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[20]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[17]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[12]
            , (android.widget.RelativeLayout) bindings[18]
            , (android.widget.RelativeLayout) bindings[6]
            , (android.widget.RelativeLayout) bindings[3]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[2]
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
        if (BR.dpoGeoStructureUpdateViewModel == variableId) {
            setDpoGeoStructureUpdateViewModel((com.pngelectoral.ui.viewModel.DpoGeoStructureUpdateViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setDpoGeoStructureUpdateViewModel(@Nullable com.pngelectoral.ui.viewModel.DpoGeoStructureUpdateViewModel DpoGeoStructureUpdateViewModel) {
        this.mDpoGeoStructureUpdateViewModel = DpoGeoStructureUpdateViewModel;
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
        flag 0 (0x1L): dpoGeoStructureUpdateViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}