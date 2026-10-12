package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityRollLookUpVoterDetailsBindingImpl extends ActivityRollLookUpVoterDetailsBinding  {

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
        sViewsWithIds.put(R.id.rlVoterImage, 5);
        sViewsWithIds.put(R.id.ivVoterImage, 6);
        sViewsWithIds.put(R.id.tvVoterName, 7);
        sViewsWithIds.put(R.id.ivVoterQRCode, 8);
        sViewsWithIds.put(R.id.llMandatryDataFields, 9);
        sViewsWithIds.put(R.id.tvMandatoryDataFields, 10);
        sViewsWithIds.put(R.id.etLastName, 11);
        sViewsWithIds.put(R.id.etGivenName, 12);
        sViewsWithIds.put(R.id.etDateOfBirth, 13);
        sViewsWithIds.put(R.id.etPlaceOfBirth, 14);
        sViewsWithIds.put(R.id.etProvinceOfBirth, 15);
        sViewsWithIds.put(R.id.etGender, 16);
        sViewsWithIds.put(R.id.etOccupation, 17);
        sViewsWithIds.put(R.id.etStatus, 18);
        sViewsWithIds.put(R.id.etComment, 19);
        sViewsWithIds.put(R.id.etDateAndTime, 20);
        sViewsWithIds.put(R.id.etSubmittedBy, 21);
        sViewsWithIds.put(R.id.rlPPHeader, 22);
        sViewsWithIds.put(R.id.tvPPTitle, 23);
        sViewsWithIds.put(R.id.tvPPArrow, 24);
        sViewsWithIds.put(R.id.llPersonalParticulars, 25);
        sViewsWithIds.put(R.id.etFamilyNumber, 26);
        sViewsWithIds.put(R.id.etFamilyName, 27);
        sViewsWithIds.put(R.id.etFamilyOrderNo, 28);
        sViewsWithIds.put(R.id.etNationalIDCardNumber, 29);
        sViewsWithIds.put(R.id.etPersonWithDisability, 30);
        sViewsWithIds.put(R.id.etReligion, 31);
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
        sViewsWithIds.put(R.id.etLocationOrVillage, 44);
        sViewsWithIds.put(R.id.rlRuralHeader, 45);
        sViewsWithIds.put(R.id.tvRuralTitle, 46);
        sViewsWithIds.put(R.id.tvRuralArrow, 47);
        sViewsWithIds.put(R.id.llRural, 48);
        sViewsWithIds.put(R.id.etClan, 49);
        sViewsWithIds.put(R.id.etSubClan, 50);
        sViewsWithIds.put(R.id.etRestHouse, 51);
        sViewsWithIds.put(R.id.rlUrbanHeader, 52);
        sViewsWithIds.put(R.id.tvUrbanTitle, 53);
        sViewsWithIds.put(R.id.tvUrbanArrow, 54);
        sViewsWithIds.put(R.id.llUrban, 55);
        sViewsWithIds.put(R.id.etSectionNo, 56);
        sViewsWithIds.put(R.id.etLotNo, 57);
        sViewsWithIds.put(R.id.etStreetName, 58);
        sViewsWithIds.put(R.id.etSuburb, 59);
        sViewsWithIds.put(R.id.rlBottom, 60);
        sViewsWithIds.put(R.id.tvBottomData, 61);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityRollLookUpVoterDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 62, sIncludes, sViewsWithIds));
    }
    private ActivityRollLookUpVoterDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[49]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[27]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[26]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[28]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[32]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[33]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[42]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[44]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[57]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[34]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[35]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[29]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[41]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[30]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[40]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[31]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[51]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[56]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[58]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[50]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[21]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[59]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[43]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[6]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[25]
            , (android.widget.LinearLayout) bindings[39]
            , (android.widget.LinearLayout) bindings[48]
            , (android.widget.LinearLayout) bindings[55]
            , (android.widget.RelativeLayout) bindings[60]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[22]
            , (android.widget.RelativeLayout) bindings[36]
            , (android.widget.RelativeLayout) bindings[45]
            , (android.widget.RelativeLayout) bindings[52]
            , (android.widget.RelativeLayout) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[61]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[24]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[23]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[38]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[37]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[47]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[46]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[3]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[54]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[53]
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
        if (BR.voterDetailsViewModel == variableId) {
            setVoterDetailsViewModel((com.pngelectoral.ui.viewModel.VoterDetailsViewModel) variable);
        }
        else {
            variableSet = false;
        }
            return variableSet;
    }

    public void setVoterDetailsViewModel(@Nullable com.pngelectoral.ui.viewModel.VoterDetailsViewModel VoterDetailsViewModel) {
        this.mVoterDetailsViewModel = VoterDetailsViewModel;
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
        flag 0 (0x1L): voterDetailsViewModel
        flag 1 (0x2L): null
    flag mapping end*/
    //end
}