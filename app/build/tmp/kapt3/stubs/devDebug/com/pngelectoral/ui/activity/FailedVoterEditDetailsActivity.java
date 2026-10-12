package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00c2\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0002\n\u0002\b\u000b\n\u0002\u0010\u0011\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010e\u001a\u00020fH\u0002J\n\u0010g\u001a\u0004\u0018\u00010NH\u0002J\b\u0010h\u001a\u00020fH\u0002J\u000f\u0010i\u001a\u0004\u0018\u00010\u0015H\u0002\u00a2\u0006\u0002\u0010jJ\u0010\u0010k\u001a\u00020f2\u0006\u0010l\u001a\u00020\u0015H\u0002J\b\u0010m\u001a\u00020fH\u0002J\b\u0010n\u001a\u00020fH\u0002J#\u0010o\u001a\u00020\u00152\u0006\u0010p\u001a\u00020\u001d2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020\u001d0rH\u0002\u00a2\u0006\u0002\u0010sJ\b\u0010t\u001a\u00020fH\u0002J\b\u0010u\u001a\u00020fH\u0002J\b\u0010v\u001a\u00020fH\u0002J\b\u0010w\u001a\u00020fH\u0002J\b\u0010x\u001a\u00020fH\u0002J\b\u0010y\u001a\u00020fH\u0003J\b\u0010z\u001a\u00020fH\u0002J\"\u0010{\u001a\u00020f2\u0006\u0010|\u001a\u00020\u00152\u0006\u0010}\u001a\u00020\u00152\b\u0010~\u001a\u0004\u0018\u00010\u007fH\u0014J\u0015\u0010\u0080\u0001\u001a\u00020f2\n\u0010\u0081\u0001\u001a\u0005\u0018\u00010\u0082\u0001H\u0014J\t\u0010\u0083\u0001\u001a\u00020fH\u0014J\u0012\u0010\u0084\u0001\u001a\u00020f2\u0007\u0010\u0085\u0001\u001a\u00020\u001dH\u0002J\u0012\u0010\u0086\u0001\u001a\u00020f2\u0007\u0010\u0085\u0001\u001a\u00020\u001dH\u0003J\t\u0010\u0087\u0001\u001a\u00020fH\u0002J\t\u0010\u0088\u0001\u001a\u00020fH\u0002J\u0011\u0010\u0089\u0001\u001a\u00020f2\u0006\u0010X\u001a\u00020YH\u0002J\u0012\u0010\u008a\u0001\u001a\u00020f2\u0007\u0010\u008b\u0001\u001a\u00020\u001dH\u0002J\u001e\u0010\u008a\u0001\u001a\u00020f2\u0007\u0010\u008b\u0001\u001a\u00020\u001d2\n\u0010\u008c\u0001\u001a\u0005\u0018\u00010\u008d\u0001H\u0002J\u001e\u0010\u008a\u0001\u001a\u00020f2\u0007\u0010\u008b\u0001\u001a\u00020\u001d2\n\u0010\u008e\u0001\u001a\u0005\u0018\u00010\u008f\u0001H\u0002J\t\u0010\u0090\u0001\u001a\u00020fH\u0002J\u001a\u0010\u0091\u0001\u001a\u00020f2\u000f\u0010\u0092\u0001\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010HH\u0002J\u0017\u0010\u0093\u0001\u001a\u00020f2\f\u0010G\u001a\b\u0012\u0004\u0012\u00020I0HH\u0002J\t\u0010\u0094\u0001\u001a\u00020fH\u0002J\u001a\u0010\u0095\u0001\u001a\u00020f2\u000f\u0010\u0092\u0001\u001a\n\u0012\u0004\u0012\u00020N\u0018\u00010HH\u0002J\'\u0010\u0096\u0001\u001a\u00020f2\f\u0010q\u001a\b\u0012\u0004\u0012\u00020\u001d0r2\b\u0010\u0097\u0001\u001a\u00030\u008f\u0001H\u0002\u00a2\u0006\u0003\u0010\u0098\u0001J\u0012\u0010\u0099\u0001\u001a\u00020f2\u0007\u0010\u009a\u0001\u001a\u00020YH\u0002J\t\u0010\u009b\u0001\u001a\u00020fH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001c\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001e\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020 X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010!\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0017\"\u0004\b$\u0010\u0019R\u001a\u0010%\u001a\u00020\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u0016\u0010*\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010+X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010-\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b.\u0010\u0017\"\u0004\b/\u0010\u0019R\u001a\u00100\u001a\u000201X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u00103\"\u0004\b4\u00105R\u0010\u00106\u001a\u0004\u0018\u000107X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00108\u001a\u000209X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010:\u001a\n <*\u0004\u0018\u00010;0;X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010=\u001a\u0004\u0018\u000107X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010>\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u0017\"\u0004\b@\u0010\u0019R\u001a\u0010A\u001a\u00020\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\'\"\u0004\bC\u0010)R\u001a\u0010D\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u0017\"\u0004\bF\u0010\u0019R\u0016\u0010G\u001a\n\u0012\u0004\u0012\u00020I\u0018\u00010HX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010J\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u0017\"\u0004\bL\u0010\u0019R\"\u0010M\u001a\n\u0012\u0004\u0012\u00020N\u0018\u00010+X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u000e\u0010S\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010T\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bU\u0010\u0017\"\u0004\bV\u0010\u0019R\u0010\u0010W\u001a\u0004\u0018\u00010\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010X\u001a\u00020YX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010[\"\u0004\b\\\u0010]R\u000e\u0010^\u001a\u00020\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010_\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b`\u0010\u0017\"\u0004\ba\u0010\u0019R\u001a\u0010b\u001a\u00020\u001dX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010\'\"\u0004\bd\u0010)\u00a8\u0006\u009c\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/FailedVoterEditDetailsActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityFailedVoterEditDetailsBinding;", "bindingDatePicker", "Lcom/pngelectoral/databinding/DialogDatePickerBinding;", "bindingYearPicker", "Lcom/pngelectoral/databinding/DialogYearPickerBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "editVoterDetailsViewModel", "Lcom/pngelectoral/ui/viewModel/EditVoterDetailsViewModel;", "enrolmentForm11Number", "", "form11ImagePath", "job", "Lkotlinx/coroutines/Job;", "lastDateOfEnrolment", "llgId", "getLlgId", "setLlgId", "llgName", "getLlgName", "()Ljava/lang/String;", "setLlgName", "(Ljava/lang/String;)V", "locationArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mAlertDialog", "Landroid/app/AlertDialog;", "mDisplayDateFormat", "Ljava/text/SimpleDateFormat;", "mSelectedCalender", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "mYearPickerAlertDialog", "mkaWardId", "getMkaWardId", "setMkaWardId", "mkaWardName", "getMkaWardName", "setMkaWardName", "occupationId", "getOccupationId", "setOccupationId", "occupationListArray", "", "Lcom/pngelectoral/ui/model/OccupationList;", "provinceId", "getProvinceId", "setProvinceId", "provinceListArray", "Lcom/pngelectoral/ui/model/ProvinceList;", "getProvinceListArray", "()Ljava/util/ArrayList;", "setProvinceListArray", "(Ljava/util/ArrayList;)V", "provisionalCutOffDate", "regionId", "getRegionId", "setRegionId", "selectedImagePath", "voterDetailsResponse", "Lcom/pngelectoral/ui/model/VotersList;", "getVoterDetailsResponse", "()Lcom/pngelectoral/ui/model/VotersList;", "setVoterDetailsResponse", "(Lcom/pngelectoral/ui/model/VotersList;)V", "voterOriginalDob", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "callLocationListAPI", "", "fetchVoterProvinceOfBirthNameFromDB", "getLocationListFromDB", "getNotSpecifiedOccupationId", "()Ljava/lang/Integer;", "getOccupation", "occupationFK", "getOccupationListFromDB", "getProvinceListFromDB", "getSelectedPosition", "selectedValue", "singleChoiceItems", "", "(Ljava/lang/String;[Ljava/lang/String;)I", "getVoterDistrictName", "getVoterLLGName", "getVoterLocationName", "getVoterProvinceName", "getVoterProvinceOfBirthName", "getVoterWardName", "navigateToUpdateFor11NumberScreen", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "selectDateOfBirth", "dateOfBirth", "selectDateOfBirthYear", "setFooterData", "setOnClickListeners", "setVoterData", "showAlertMessage", "alertMessage", "currentEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "currentTextView", "Landroidx/appcompat/widget/AppCompatTextView;", "showGenderOptions", "showLocationOptions", "result", "showOccupationWithSearch", "showPersonWithDisabilityOptions", "showProvinceOptions", "showSingleSelectionDialog", "tvSelection", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;)V", "updatedVoterInformationDB", "updatedVoterInformation", "validateVoterDetails", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class FailedVoterEditDetailsActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityFailedVoterEditDetailsBinding binding;
    private com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel editVoterDetailsViewModel;
    @org.jetbrains.annotations.NotNull
    private com.pngelectoral.ui.model.VotersList voterDetailsResponse;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private java.lang.String selectedImagePath = "";
    private java.lang.String form11ImagePath = "";
    private java.lang.String enrolmentForm11Number = "";
    private int regionId = 0;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int mkaWardId = 0;
    private int locationId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String llgName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String mkaWardName = "";
    private java.lang.String lastDateOfEnrolment = "";
    private java.lang.String provisionalCutOffDate = "";
    private int occupationId = 0;
    private java.util.List<? extends com.pngelectoral.ui.model.OccupationList> occupationListArray;
    private java.lang.String voterOriginalDob = "";
    private kotlinx.coroutines.Job job;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceListArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private com.pngelectoral.databinding.DialogDatePickerBinding bindingDatePicker;
    private android.app.AlertDialog mAlertDialog;
    private final java.util.Calendar mSelectedCalender = null;
    private final java.text.SimpleDateFormat mDisplayDateFormat = null;
    private com.pngelectoral.databinding.DialogYearPickerBinding bindingYearPicker;
    private android.app.AlertDialog mYearPickerAlertDialog;
    private java.util.HashMap _$_findViewCache;
    
    public FailedVoterEditDetailsActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.VotersList getVoterDetailsResponse() {
        return null;
    }
    
    public final void setVoterDetailsResponse(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.VotersList p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.DPOUserData getLoggedInDPOUserData() {
        return null;
    }
    
    public final void setLoggedInDPOUserData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.DPOUserData p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
    }
    
    public final int getRegionId() {
        return 0;
    }
    
    public final void setRegionId(int p0) {
    }
    
    public final int getProvinceId() {
        return 0;
    }
    
    public final void setProvinceId(int p0) {
    }
    
    public final int getDistrictId() {
        return 0;
    }
    
    public final void setDistrictId(int p0) {
    }
    
    public final int getLlgId() {
        return 0;
    }
    
    public final void setLlgId(int p0) {
    }
    
    public final int getWardId() {
        return 0;
    }
    
    public final void setWardId(int p0) {
    }
    
    public final int getMkaWardId() {
        return 0;
    }
    
    public final void setMkaWardId(int p0) {
    }
    
    public final int getLocationId() {
        return 0;
    }
    
    public final void setLocationId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLlgName() {
        return null;
    }
    
    public final void setLlgName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWardName() {
        return null;
    }
    
    public final void setWardName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getMkaWardName() {
        return null;
    }
    
    public final void setMkaWardName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    public final int getOccupationId() {
        return 0;
    }
    
    public final void setOccupationId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setFooterData() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setVoterData(com.pngelectoral.ui.model.VotersList voterDetailsResponse) {
    }
    
    private final void getOccupation(int occupationFK) {
    }
    
    private final void getVoterProvinceOfBirthName() {
    }
    
    private final com.pngelectoral.ui.model.ProvinceList fetchVoterProvinceOfBirthNameFromDB() {
        return null;
    }
    
    private final void getVoterProvinceName() {
    }
    
    private final void getVoterDistrictName() {
    }
    
    private final void getVoterLLGName() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getVoterWardName() {
    }
    
    private final void getVoterLocationName() {
    }
    
    private final void setOnClickListeners() {
    }
    
    private final void showOccupationWithSearch(java.util.List<? extends com.pngelectoral.ui.model.OccupationList> occupationListArray) {
    }
    
    private final void showGenderOptions() {
    }
    
    private final void showPersonWithDisabilityOptions() {
    }
    
    private final void getOccupationListFromDB() {
    }
    
    private final void getProvinceListFromDB() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> getProvinceListArray() {
        return null;
    }
    
    public final void setProvinceListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> p0) {
    }
    
    private final void showProvinceOptions(java.util.List<? extends com.pngelectoral.ui.model.ProvinceList> result) {
    }
    
    private final void getLocationListFromDB() {
    }
    
    private final void callLocationListAPI() {
    }
    
    private final void showLocationOptions(java.util.List<? extends com.pngelectoral.ui.model.LocationList> result) {
    }
    
    private final void showSingleSelectionDialog(java.lang.String[] singleChoiceItems, androidx.appcompat.widget.AppCompatTextView tvSelection) {
    }
    
    private final int getSelectedPosition(java.lang.String selectedValue, java.lang.String[] singleChoiceItems) {
        return 0;
    }
    
    private final void validateVoterDetails() {
    }
    
    private final java.lang.Integer getNotSpecifiedOccupationId() {
        return null;
    }
    
    private final void updatedVoterInformationDB(com.pngelectoral.ui.model.VotersList updatedVoterInformation) {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage, androidx.appcompat.widget.AppCompatEditText currentEditText) {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage, androidx.appcompat.widget.AppCompatTextView currentTextView) {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage) {
    }
    
    private final void selectDateOfBirth(java.lang.String dateOfBirth) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void selectDateOfBirthYear(java.lang.String dateOfBirth) {
    }
    
    private final void navigateToUpdateFor11NumberScreen() {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
}