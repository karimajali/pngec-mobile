package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityFailedVoterEditDetailsBindingImpl extends ActivityFailedVoterEditDetailsBinding  {

    @Nullable
    private static final androidx.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    @Nullable
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.rlHeader, 1);
        sViewsWithIds.put(R.id.btnBack, 2);
        sViewsWithIds.put(R.id.tvTitle, 3);
        sViewsWithIds.put(R.id.btnHome, 4);
        sViewsWithIds.put(R.id.ivVoterImage, 5);
        sViewsWithIds.put(R.id.tvVoterName, 6);
        sViewsWithIds.put(R.id.ivVoterQRCode, 7);
        sViewsWithIds.put(R.id.llMandatryDataFields, 8);
        sViewsWithIds.put(R.id.tvMandatoryDataFields, 9);
        sViewsWithIds.put(R.id.etLastName, 10);
        sViewsWithIds.put(R.id.etGivenName, 11);
        sViewsWithIds.put(R.id.etGender, 12);
        sViewsWithIds.put(R.id.etLocationOrVillage, 13);
        sViewsWithIds.put(R.id.etOccupation, 14);
        sViewsWithIds.put(R.id.cbDOBIsYear, 15);
        sViewsWithIds.put(R.id.etDateOfBirth, 16);
        sViewsWithIds.put(R.id.etPersonWithDisability, 17);
        sViewsWithIds.put(R.id.etReligion, 18);
        sViewsWithIds.put(R.id.etNationalIDCardNumber, 19);
        sViewsWithIds.put(R.id.etClan, 20);
        sViewsWithIds.put(R.id.etComment, 21);
        sViewsWithIds.put(R.id.etStatus, 22);
        sViewsWithIds.put(R.id.rlPPHeader, 23);
        sViewsWithIds.put(R.id.tvPPTitle, 24);
        sViewsWithIds.put(R.id.tvPPArrow, 25);
        sViewsWithIds.put(R.id.llPersonalParticulars, 26);
        sViewsWithIds.put(R.id.etFamilyNumber, 27);
        sViewsWithIds.put(R.id.etFamilyName, 28);
        sViewsWithIds.put(R.id.etFamilyOrderNo, 29);
        sViewsWithIds.put(R.id.etPlaceOfBirth, 30);
        sViewsWithIds.put(R.id.etProvinceOfBirth, 31);
        sViewsWithIds.put(R.id.etFathersFirstName, 32);
        sViewsWithIds.put(R.id.etFathersLastName, 33);
        sViewsWithIds.put(R.id.etMothersFirstName, 34);
        sViewsWithIds.put(R.id.etMothersLastName, 35);
        sViewsWithIds.put(R.id.rlPRHeader, 36);
        sViewsWithIds.put(R.id.tvPRTitle, 37);
        sViewsWithIds.put(R.id.tvPRArrow, 38);
        sViewsWithIds.put(R.id.llPlaceOfResidence, 39);
        sViewsWithIds.put(R.id.etProvince, 40);
        sViewsWithIds.put(R.id.etOpenElectorate, 41);
        sViewsWithIds.put(R.id.etLLG, 42);
        sViewsWithIds.put(R.id.etWardNoAndName, 43);
        sViewsWithIds.put(R.id.rlRuralHeader, 44);
        sViewsWithIds.put(R.id.tvRuralTitle, 45);
        sViewsWithIds.put(R.id.tvRuralArrow, 46);
        sViewsWithIds.put(R.id.llRural, 47);
        sViewsWithIds.put(R.id.etSubClan, 48);
        sViewsWithIds.put(R.id.etRestHouse, 49);
        sViewsWithIds.put(R.id.rlUrbanHeader, 50);
        sViewsWithIds.put(R.id.tvUrbanTitle, 51);
        sViewsWithIds.put(R.id.tvUrbanArrow, 52);
        sViewsWithIds.put(R.id.llUrban, 53);
        sViewsWithIds.put(R.id.etSectionNo, 54);
        sViewsWithIds.put(R.id.etLotNo, 55);
        sViewsWithIds.put(R.id.etStreetName, 56);
        sViewsWithIds.put(R.id.etSuburb, 57);
        sViewsWithIds.put(R.id.llButtons, 58);
        sViewsWithIds.put(R.id.btnSubmit, 59);
        sViewsWithIds.put(R.id.btnUpdateForm11Number, 60);
        sViewsWithIds.put(R.id.rlBottom, 61);
        sViewsWithIds.put(R.id.tvBottomData, 62);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityFailedVoterEditDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 63, sIncludes, sViewsWithIds));
    }
    private ActivityFailedVoterEditDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatButton) bindings[59]
            , (androidx.appcompat.widget.AppCompatButton) bindings[60]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[20]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[28]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[27]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[29]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[32]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[33]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[42]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[10]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[55]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[34]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[35]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[41]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[30]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[40]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[31]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[18]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[49]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[54]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[22]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[56]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[48]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[57]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[43]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[5]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[7]
            , (android.widget.LinearLayout) bindings[58]
            , (android.widget.LinearLayout) bindings[8]
            , (android.widget.LinearLayout) bindings[26]
            , (android.widget.LinearLayout) bindings[39]
            , (android.widget.LinearLayout) bindings[47]
            , (android.widget.LinearLayout) bindings[53]
            , (android.widget.RelativeLayout) bindings[61]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[23]
            , (android.widget.RelativeLayout) bindings[36]
            , (android.widget.RelativeLayout) bindings[44]
            , (android.widget.RelativeLayout) bindings[50]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[62]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[9]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[25]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[24]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[38]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[37]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[46]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[45]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[52]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[51]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[6]
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
        if (BR.editVoterDetailsViewModel == variableId) {
            setEditVoterDetailsViewModel((com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setEditVoterDetailsViewModel(@Nullable com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel EditVoterDetailsViewModel) {
        this.mEditVoterDetailsViewModel = EditVoterDetailsViewModel;
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
        flag 0 (0x1L): editVoterDetailsViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}