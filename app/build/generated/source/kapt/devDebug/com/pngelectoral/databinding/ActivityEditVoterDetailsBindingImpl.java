package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityEditVoterDetailsBindingImpl extends ActivityEditVoterDetailsBinding  {

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
        sViewsWithIds.put(R.id.scrollView, 5);
        sViewsWithIds.put(R.id.ivVoterImage, 6);
        sViewsWithIds.put(R.id.tvVoterName, 7);
        sViewsWithIds.put(R.id.ivVoterQRCode, 8);
        sViewsWithIds.put(R.id.llMandatryDataFields, 9);
        sViewsWithIds.put(R.id.tvMandatoryDataFields, 10);
        sViewsWithIds.put(R.id.etLastName, 11);
        sViewsWithIds.put(R.id.etGivenName, 12);
        sViewsWithIds.put(R.id.etGender, 13);
        sViewsWithIds.put(R.id.etLocationOrVillage, 14);
        sViewsWithIds.put(R.id.etClan, 15);
        sViewsWithIds.put(R.id.etOccupation, 16);
        sViewsWithIds.put(R.id.cbDOBIsYear, 17);
        sViewsWithIds.put(R.id.etDateOfBirth, 18);
        sViewsWithIds.put(R.id.etPersonWithDisability, 19);
        sViewsWithIds.put(R.id.etReligion, 20);
        sViewsWithIds.put(R.id.etNationalIDCardNumber, 21);
        sViewsWithIds.put(R.id.etComment, 22);
        sViewsWithIds.put(R.id.etStatus, 23);
        sViewsWithIds.put(R.id.rlPPHeader, 24);
        sViewsWithIds.put(R.id.tvPPTitle, 25);
        sViewsWithIds.put(R.id.tvPPArrow, 26);
        sViewsWithIds.put(R.id.llPersonalParticulars, 27);
        sViewsWithIds.put(R.id.etFamilyNumber, 28);
        sViewsWithIds.put(R.id.etFamilyName, 29);
        sViewsWithIds.put(R.id.etFamilyOrderNo, 30);
        sViewsWithIds.put(R.id.etPlaceOfBirth, 31);
        sViewsWithIds.put(R.id.etProvinceOfBirth, 32);
        sViewsWithIds.put(R.id.etFathersFirstName, 33);
        sViewsWithIds.put(R.id.etFathersLastName, 34);
        sViewsWithIds.put(R.id.etMothersFirstName, 35);
        sViewsWithIds.put(R.id.etMothersLastName, 36);
        sViewsWithIds.put(R.id.rlPRHeader, 37);
        sViewsWithIds.put(R.id.tvPRTitle, 38);
        sViewsWithIds.put(R.id.tvPRArrow, 39);
        sViewsWithIds.put(R.id.llPlaceOfResidence, 40);
        sViewsWithIds.put(R.id.etProvince, 41);
        sViewsWithIds.put(R.id.etOpenElectorate, 42);
        sViewsWithIds.put(R.id.etLLG, 43);
        sViewsWithIds.put(R.id.etWardNoAndName, 44);
        sViewsWithIds.put(R.id.rlRuralHeader, 45);
        sViewsWithIds.put(R.id.tvRuralTitle, 46);
        sViewsWithIds.put(R.id.tvRuralArrow, 47);
        sViewsWithIds.put(R.id.llRural, 48);
        sViewsWithIds.put(R.id.etSubClan, 49);
        sViewsWithIds.put(R.id.etRestHouse, 50);
        sViewsWithIds.put(R.id.rlUrbanHeader, 51);
        sViewsWithIds.put(R.id.tvUrbanTitle, 52);
        sViewsWithIds.put(R.id.tvUrbanArrow, 53);
        sViewsWithIds.put(R.id.llUrban, 54);
        sViewsWithIds.put(R.id.etSectionNo, 55);
        sViewsWithIds.put(R.id.etLotNo, 56);
        sViewsWithIds.put(R.id.etStreetName, 57);
        sViewsWithIds.put(R.id.etSuburb, 58);
        sViewsWithIds.put(R.id.llKMABioLayout, 59);
        sViewsWithIds.put(R.id.rlKMABioHeader, 60);
        sViewsWithIds.put(R.id.tvKMABioTitle, 61);
        sViewsWithIds.put(R.id.tvKMABioArrow, 62);
        sViewsWithIds.put(R.id.llKMABio, 63);
        sViewsWithIds.put(R.id.etMKADistrict, 64);
        sViewsWithIds.put(R.id.etMKALlg, 65);
        sViewsWithIds.put(R.id.etMKAWard, 66);
        sViewsWithIds.put(R.id.etMKALocation, 67);
        sViewsWithIds.put(R.id.etMKAClan, 68);
        sViewsWithIds.put(R.id.llButtons, 69);
        sViewsWithIds.put(R.id.btnSubmit, 70);
        sViewsWithIds.put(R.id.btnUpdateStatus, 71);
        sViewsWithIds.put(R.id.llRevert, 72);
        sViewsWithIds.put(R.id.btnRevert, 73);
        sViewsWithIds.put(R.id.rlBottom, 74);
        sViewsWithIds.put(R.id.tvBottomData, 75);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityEditVoterDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 76, sIncludes, sViewsWithIds));
    }
    private ActivityEditVoterDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatButton) bindings[73]
            , (androidx.appcompat.widget.AppCompatButton) bindings[70]
            , (androidx.appcompat.widget.AppCompatButton) bindings[71]
            , (androidx.appcompat.widget.AppCompatCheckBox) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[22]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[29]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[28]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[30]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[33]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[34]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[43]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[56]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[68]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[64]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[65]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[67]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[66]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[35]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[36]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[42]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[31]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[41]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[32]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[20]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[50]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[55]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[57]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[49]
            , (androidx.appcompat.widget.AppCompatEditText) bindings[58]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[44]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[6]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[69]
            , (android.widget.LinearLayout) bindings[63]
            , (android.widget.LinearLayout) bindings[59]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[27]
            , (android.widget.LinearLayout) bindings[40]
            , (android.widget.LinearLayout) bindings[72]
            , (android.widget.LinearLayout) bindings[48]
            , (android.widget.LinearLayout) bindings[54]
            , (android.widget.RelativeLayout) bindings[74]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[60]
            , (android.widget.RelativeLayout) bindings[24]
            , (android.widget.RelativeLayout) bindings[37]
            , (android.widget.RelativeLayout) bindings[45]
            , (android.widget.RelativeLayout) bindings[51]
            , (androidx.core.widget.NestedScrollView) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[75]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[62]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[61]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[26]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[25]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[39]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[38]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[47]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[46]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[53]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[52]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[7]
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