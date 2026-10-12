package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class DialogUpdateStatusBindingImpl extends DialogUpdateStatusBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlHeader, 1);
        sViewsWithIds.put(R.id.ivClose, 2);
        sViewsWithIds.put(R.id.llStatus, 3);
        sViewsWithIds.put(R.id.rlStatus, 4);
        sViewsWithIds.put(R.id.etStatus, 5);
        sViewsWithIds.put(R.id.ivStatus, 6);
        sViewsWithIds.put(R.id.llTransferredOut, 7);
        sViewsWithIds.put(R.id.llRegion, 8);
        sViewsWithIds.put(R.id.rlRegion, 9);
        sViewsWithIds.put(R.id.etRegion, 10);
        sViewsWithIds.put(R.id.ivRegion, 11);
        sViewsWithIds.put(R.id.llProvince, 12);
        sViewsWithIds.put(R.id.rlNewProvince, 13);
        sViewsWithIds.put(R.id.etNewProvince, 14);
        sViewsWithIds.put(R.id.ivNewProvince, 15);
        sViewsWithIds.put(R.id.rlDistrict, 16);
        sViewsWithIds.put(R.id.etDistrict, 17);
        sViewsWithIds.put(R.id.ivDistrict, 18);
        sViewsWithIds.put(R.id.rlLLG, 19);
        sViewsWithIds.put(R.id.etNewLLG, 20);
        sViewsWithIds.put(R.id.ivNewLLG, 21);
        sViewsWithIds.put(R.id.rlWard, 22);
        sViewsWithIds.put(R.id.etWard, 23);
        sViewsWithIds.put(R.id.ivWard, 24);
        sViewsWithIds.put(R.id.rlLocation, 25);
        sViewsWithIds.put(R.id.etNewLocation, 26);
        sViewsWithIds.put(R.id.ivNewLocation, 27);
        sViewsWithIds.put(R.id.etComment, 28);
        sViewsWithIds.put(R.id.btnDone, 29);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DialogUpdateStatusBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 30, sIncludes, sViewsWithIds));
    }
    private DialogUpdateStatusBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatButton) bindings[29]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[28]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[26]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[18]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[21]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[27]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[15]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[11]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[6]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[24]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[3]
            , (android.widget.LinearLayout) bindings[7]
            , (android.widget.RelativeLayout) bindings[16]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[19]
            , (android.widget.RelativeLayout) bindings[25]
            , (android.widget.RelativeLayout) bindings[13]
            , (android.widget.RelativeLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[4]
            , (android.widget.RelativeLayout) bindings[22]
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
                mDirtyFlags = 0x1L;
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
            return variableSet;
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
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}