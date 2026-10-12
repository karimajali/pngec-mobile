package com.pngelectoral.databinding;
import com.pngelectoral.R;
import com.pngelectoral.BR;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
@SuppressWarnings("unchecked")
public class ActivityVoterDetailsBindingImpl extends ActivityVoterDetailsBinding  {

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
        sViewsWithIds.put(R.id.rlPPHeader, 21);
        sViewsWithIds.put(R.id.tvPPTitle, 22);
        sViewsWithIds.put(R.id.tvPPArrow, 23);
        sViewsWithIds.put(R.id.llPersonalParticulars, 24);
        sViewsWithIds.put(R.id.etFamilyNumber, 25);
        sViewsWithIds.put(R.id.etFamilyName, 26);
        sViewsWithIds.put(R.id.etFamilyOrderNo, 27);
        sViewsWithIds.put(R.id.etNationalIDCardNumber, 28);
        sViewsWithIds.put(R.id.etPersonWithDisability, 29);
        sViewsWithIds.put(R.id.etReligion, 30);
        sViewsWithIds.put(R.id.etFathersFirstName, 31);
        sViewsWithIds.put(R.id.etFathersLastName, 32);
        sViewsWithIds.put(R.id.etMothersFirstName, 33);
        sViewsWithIds.put(R.id.etMothersLastName, 34);
        sViewsWithIds.put(R.id.rlPRHeader, 35);
        sViewsWithIds.put(R.id.tvPRTitle, 36);
        sViewsWithIds.put(R.id.tvPRArrow, 37);
        sViewsWithIds.put(R.id.llPlaceOfResidence, 38);
        sViewsWithIds.put(R.id.etProvince, 39);
        sViewsWithIds.put(R.id.etOpenElectorate, 40);
        sViewsWithIds.put(R.id.etLLG, 41);
        sViewsWithIds.put(R.id.etWardNoAndName, 42);
        sViewsWithIds.put(R.id.etLocationOrVillage, 43);
        sViewsWithIds.put(R.id.rlRuralHeader, 44);
        sViewsWithIds.put(R.id.tvRuralTitle, 45);
        sViewsWithIds.put(R.id.tvRuralArrow, 46);
        sViewsWithIds.put(R.id.llRural, 47);
        sViewsWithIds.put(R.id.etClan, 48);
        sViewsWithIds.put(R.id.etSubClan, 49);
        sViewsWithIds.put(R.id.etRestHouse, 50);
        sViewsWithIds.put(R.id.rlUrbanHeader, 51);
        sViewsWithIds.put(R.id.tvUrbanTitle, 52);
        sViewsWithIds.put(R.id.tvUrbanArrow, 53);
        sViewsWithIds.put(R.id.llUrban, 54);
        sViewsWithIds.put(R.id.etSubmittedBy, 55);
        sViewsWithIds.put(R.id.etSectionNo, 56);
        sViewsWithIds.put(R.id.etLotNo, 57);
        sViewsWithIds.put(R.id.etStreetName, 58);
        sViewsWithIds.put(R.id.etSuburb, 59);
        sViewsWithIds.put(R.id.btnUpdateStatus, 60);
        sViewsWithIds.put(R.id.btnUpdateVoterDetails, 61);
        sViewsWithIds.put(R.id.rlBottom, 62);
        sViewsWithIds.put(R.id.tvBottomData, 63);
    }
    // views
    @NonNull
    private final android.widget.RelativeLayout mboundView0;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityVoterDetailsBindingImpl(@Nullable androidx.databinding.DataBindingComponent bindingComponent, @NonNull View root) {
        this(bindingComponent, root, mapBindings(bindingComponent, root, 64, sIncludes, sViewsWithIds));
    }
    private ActivityVoterDetailsBindingImpl(androidx.databinding.DataBindingComponent bindingComponent, View root, Object[] bindings) {
        super(bindingComponent, root, 0
            , (androidx.appcompat.widget.AppCompatImageView) bindings[2]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[4]
            , (androidx.appcompat.widget.AppCompatButton) bindings[60]
            , (androidx.appcompat.widget.AppCompatButton) bindings[61]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[48]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[19]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[20]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[13]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[26]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[25]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[27]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[31]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[32]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[16]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[12]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[41]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[11]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[43]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[57]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[33]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[34]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[28]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[17]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[40]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[29]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[14]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[39]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[15]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[30]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[50]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[56]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[18]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[58]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[49]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[55]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[59]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[42]
            , (de.hdodenhof.circleimageview.CircleImageView) bindings[6]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[8]
            , (android.widget.LinearLayout) bindings[9]
            , (android.widget.LinearLayout) bindings[24]
            , (android.widget.LinearLayout) bindings[38]
            , (android.widget.LinearLayout) bindings[47]
            , (android.widget.LinearLayout) bindings[54]
            , (android.widget.RelativeLayout) bindings[62]
            , (android.widget.RelativeLayout) bindings[1]
            , (android.widget.RelativeLayout) bindings[21]
            , (android.widget.RelativeLayout) bindings[35]
            , (android.widget.RelativeLayout) bindings[44]
            , (android.widget.RelativeLayout) bindings[51]
            , (android.widget.RelativeLayout) bindings[5]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[63]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[10]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[23]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[22]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[37]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[36]
            , (androidx.appcompat.widget.AppCompatImageView) bindings[46]
            , (androidx.appcompat.widget.AppCompatTextView) bindings[45]
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