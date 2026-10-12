package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ItemTransferInVoterBindingImpl extends ItemTransferInVoterBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.llBasicInfo, 1);
        sViewsWithIds.put(R.id.ivVoterImage, 2);
        sViewsWithIds.put(R.id.tvVoterName, 3);
        sViewsWithIds.put(R.id.ivVoterNameArrow, 4);
        sViewsWithIds.put(R.id.tvVoterId, 5);
        sViewsWithIds.put(R.id.llGenderAndDob, 6);
        sViewsWithIds.put(R.id.tvGender, 7);
        sViewsWithIds.put(R.id.tvDob, 8);
        sViewsWithIds.put(R.id.llOccupation, 9);
        sViewsWithIds.put(R.id.tvOccupation, 10);
        sViewsWithIds.put(R.id.tvNID, 11);
        sViewsWithIds.put(R.id.llGeoOne, 12);
        sViewsWithIds.put(R.id.tvRegion, 13);
        sViewsWithIds.put(R.id.tvProvince, 14);
        sViewsWithIds.put(R.id.llGeoTwo, 15);
        sViewsWithIds.put(R.id.tvDistrict, 16);
        sViewsWithIds.put(R.id.tvLLG, 17);
        sViewsWithIds.put(R.id.tvWard, 18);
        sViewsWithIds.put(R.id.tvLocation, 19);
        sViewsWithIds.put(R.id.tvCvlanName, 20);
        sViewsWithIds.put(R.id.rlVoterLocation, 21);
        sViewsWithIds.put(R.id.tvVoterLocation, 22);
        sViewsWithIds.put(R.id.tvVoterStatus, 23);
    }
    // views
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ItemTransferInVoterBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 24, sIncludes, sViewsWithIds));
    }
    private ItemTransferInVoterBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.cardview.widget.CardView) bindings[0]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (android.widget.LinearLayout) bindings[1]
            , (android.widget.LinearLayout) bindings[6]
            , (android.widget.LinearLayout) bindings[12]
            , (android.widget.LinearLayout) bindings[15]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.RelativeLayout) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[8]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[22]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            );
        this.cvParent.setTag(null);
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