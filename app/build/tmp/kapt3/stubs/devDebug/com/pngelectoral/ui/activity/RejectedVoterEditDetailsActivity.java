package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00cc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\r\n\u0002\u0010\u0011\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010a\u001a\u00020bH\u0002J\b\u0010c\u001a\u00020bH\u0002J\u0010\u0010d\u001a\n\u0012\u0004\u0012\u000201\u0018\u00010GH\u0002J\u000f\u0010e\u001a\u0004\u0018\u00010\u0015H\u0002\u00a2\u0006\u0002\u0010fJ\u0010\u0010g\u001a\u00020b2\u0006\u0010h\u001a\u00020\u0015H\u0002J\b\u0010i\u001a\u00020bH\u0002J\u0010\u0010j\u001a\n\u0012\u0004\u0012\u00020H\u0018\u00010GH\u0002J\b\u0010k\u001a\u00020bH\u0002J\u0010\u0010l\u001a\n\u0012\u0004\u0012\u00020M\u0018\u00010GH\u0002J#\u0010m\u001a\u00020\u00152\u0006\u0010n\u001a\u00020\u001f2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u001f0pH\u0002\u00a2\u0006\u0002\u0010qJ\b\u0010r\u001a\u00020bH\u0002J\b\u0010s\u001a\u00020bH\u0002J\b\u0010t\u001a\u00020bH\u0002J\b\u0010u\u001a\u00020bH\u0002J\b\u0010v\u001a\u00020bH\u0003J\u0012\u0010w\u001a\u00020b2\b\u0010x\u001a\u0004\u0018\u00010yH\u0014J\b\u0010z\u001a\u00020bH\u0014J\u0010\u0010{\u001a\u00020b2\u0006\u0010|\u001a\u00020\u001fH\u0002J\u0010\u0010}\u001a\u00020b2\u0006\u0010|\u001a\u00020\u001fH\u0003J\b\u0010~\u001a\u00020bH\u0002J\b\u0010\u007f\u001a\u00020bH\u0002J\u0011\u0010\u0080\u0001\u001a\u00020b2\u0006\u0010Q\u001a\u00020RH\u0003J\u0012\u0010\u0081\u0001\u001a\u00020b2\u0007\u0010\u0082\u0001\u001a\u00020\u001fH\u0002J\u001e\u0010\u0081\u0001\u001a\u00020b2\u0007\u0010\u0082\u0001\u001a\u00020\u001f2\n\u0010\u0083\u0001\u001a\u0005\u0018\u00010\u0084\u0001H\u0002J\u001e\u0010\u0081\u0001\u001a\u00020b2\u0007\u0010\u0082\u0001\u001a\u00020\u001f2\n\u0010\u0085\u0001\u001a\u0005\u0018\u00010\u0086\u0001H\u0002J\t\u0010\u0087\u0001\u001a\u00020bH\u0002J\u001a\u0010\u0088\u0001\u001a\u00020b2\u000f\u0010\u0089\u0001\u001a\n\u0012\u0004\u0012\u000201\u0018\u00010GH\u0002J\u0017\u0010\u008a\u0001\u001a\u00020b2\f\u0010F\u001a\b\u0012\u0004\u0012\u00020H0GH\u0002J\t\u0010\u008b\u0001\u001a\u00020bH\u0002J\u001a\u0010\u008c\u0001\u001a\u00020b2\u000f\u0010\u0089\u0001\u001a\n\u0012\u0004\u0012\u00020M\u0018\u00010GH\u0002J0\u0010\u008d\u0001\u001a\u00020b2\f\u0010o\u001a\b\u0012\u0004\u0012\u00020\u001f0p2\b\u0010\u008e\u0001\u001a\u00030\u0086\u00012\u0007\u0010\u008f\u0001\u001a\u00020\u001fH\u0002\u00a2\u0006\u0003\u0010\u0090\u0001J\u0012\u0010\u0091\u0001\u001a\u00020b2\u0007\u0010\u0092\u0001\u001a\u00020RH\u0002J\t\u0010\u0093\u0001\u001a\u00020bH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u001a\u0010\u000e\u001a\u00020\u000fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0011\"\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u0010\u0010\u001a\u001a\u0004\u0018\u00010\u001bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010 \u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\u0017\"\u0004\b\"\u0010\u0019R.\u0010#\u001a\u0016\u0012\u0004\u0012\u00020%\u0018\u00010$j\n\u0012\u0004\u0012\u00020%\u0018\u0001`&X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010(\"\u0004\b)\u0010*R\u001a\u0010+\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u0016\u00100\u001a\n\u0012\u0004\u0012\u000201\u0018\u00010$X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00102\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u0017\"\u0004\b4\u0010\u0019R\u001a\u00105\u001a\u000206X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u00108\"\u0004\b9\u0010:R\u0010\u0010;\u001a\u0004\u0018\u00010<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010=\u001a\u00020>X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010?\u001a\n A*\u0004\u0018\u00010@0@X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010B\u001a\u0004\u0018\u00010<X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010C\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\u0017\"\u0004\bE\u0010\u0019R\u0016\u0010F\u001a\n\u0012\u0004\u0012\u00020H\u0018\u00010GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010I\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010\u0017\"\u0004\bK\u0010\u0019R\"\u0010L\u001a\u0016\u0012\u0004\u0012\u00020M\u0018\u00010$j\n\u0012\u0004\u0012\u00020M\u0018\u0001`&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010N\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0017\"\u0004\bP\u0010\u0019R\u001a\u0010Q\u001a\u00020RX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010T\"\u0004\bU\u0010VR\u001a\u0010W\u001a\u00020\u0015X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010\u0017\"\u0004\bY\u0010\u0019R\u001a\u0010Z\u001a\u00020\u001fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b[\u0010-\"\u0004\b\\\u0010/R.\u0010]\u001a\u0016\u0012\u0004\u0012\u00020^\u0018\u00010$j\n\u0012\u0004\u0012\u00020^\u0018\u0001`&X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010(\"\u0004\b`\u0010*\u00a8\u0006\u0094\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/RejectedVoterEditDetailsActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityRejectedVoterEditDetailsBinding;", "bindingDatePicker", "Lcom/pngelectoral/databinding/DialogDatePickerBinding;", "bindingYearPicker", "Lcom/pngelectoral/databinding/DialogYearPickerBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "editVoterDetailsViewModel", "Lcom/pngelectoral/ui/viewModel/EditVoterDetailsViewModel;", "job", "Lkotlinx/coroutines/Job;", "lastDateOfEnrolment", "", "llgId", "getLlgId", "setLlgId", "llgListArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/LLGList;", "Lkotlin/collections/ArrayList;", "getLlgListArray", "()Ljava/util/ArrayList;", "setLlgListArray", "(Ljava/util/ArrayList;)V", "llgName", "getLlgName", "()Ljava/lang/String;", "setLlgName", "(Ljava/lang/String;)V", "locationArray", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mAlertDialog", "Landroid/app/AlertDialog;", "mDisplayDateFormat", "Ljava/text/SimpleDateFormat;", "mSelectedCalender", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "mYearPickerAlertDialog", "occupationId", "getOccupationId", "setOccupationId", "occupationListArray", "", "Lcom/pngelectoral/ui/model/OccupationList;", "provinceId", "getProvinceId", "setProvinceId", "provinceListArray", "Lcom/pngelectoral/ui/model/ProvinceList;", "regionId", "getRegionId", "setRegionId", "voterDetailsResponse", "Lcom/pngelectoral/ui/model/VotersList;", "getVoterDetailsResponse", "()Lcom/pngelectoral/ui/model/VotersList;", "setVoterDetailsResponse", "(Lcom/pngelectoral/ui/model/VotersList;)V", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "wardNoAndNameArray", "Lcom/pngelectoral/ui/model/WardList;", "getWardNoAndNameArray", "setWardNoAndNameArray", "callLocationListAPI", "", "getLocationListFromDB", "getLocationListQuery", "getNotSpecifiedOccupationId", "()Ljava/lang/Integer;", "getOccupation", "occupationFK", "getOccupationListFromDB", "getOccupationListQuery", "getProvinceListFromDB", "getProvinceListQuery", "getSelectedPosition", "selectedValue", "singleChoiceItems", "", "(Ljava/lang/String;[Ljava/lang/String;)I", "getVoterDistrictName", "getVoterLLGName", "getVoterLocationName", "getVoterProvinceName", "getVoterWardName", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "selectDateOfBirth", "dateOfBirth", "selectDateOfBirthYear", "setFooterData", "setOnClickListeners", "setVoterData", "showAlertMessage", "alertMessage", "currentEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "currentTextView", "Landroidx/appcompat/widget/AppCompatTextView;", "showGenderOptions", "showLocationOptions", "result", "showOccupationWithSearch", "showPersonWithDisabilityOptions", "showProvinceOptions", "showSingleSelectionDialog", "tvSelection", "selectedField", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;Ljava/lang/String;)V", "updatedVoterInformationDB", "updatedVoterInformation", "validateVoterDetails", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class RejectedVoterEditDetailsActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityRejectedVoterEditDetailsBinding binding;
    private com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel editVoterDetailsViewModel;
    @org.jetbrains.annotations.NotNull
    private com.pngelectoral.ui.model.VotersList voterDetailsResponse;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int regionId = 0;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String llgName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    private java.lang.String lastDateOfEnrolment = "";
    private java.util.List<? extends com.pngelectoral.ui.model.OccupationList> occupationListArray;
    private kotlinx.coroutines.Job job;
    private int occupationId = 0;
    private java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardNoAndNameArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private final java.util.Calendar mSelectedCalender = null;
    private final java.text.SimpleDateFormat mDisplayDateFormat = null;
    private com.pngelectoral.databinding.DialogDatePickerBinding bindingDatePicker;
    private android.app.AlertDialog mAlertDialog;
    private com.pngelectoral.databinding.DialogYearPickerBinding bindingYearPicker;
    private android.app.AlertDialog mYearPickerAlertDialog;
    private java.util.HashMap _$_findViewCache;
    
    public RejectedVoterEditDetailsActivity() {
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
    @java.lang.Override
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setFooterData() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void setVoterData(com.pngelectoral.ui.model.VotersList voterDetailsResponse) {
    }
    
    private final void getOccupation(int occupationFK) {
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
    
    public final int getOccupationId() {
        return 0;
    }
    
    public final void setOccupationId(int p0) {
    }
    
    private final void showOccupationWithSearch(java.util.List<? extends com.pngelectoral.ui.model.OccupationList> occupationListArray) {
    }
    
    private final void showGenderOptions() {
    }
    
    private final void showPersonWithDisabilityOptions() {
    }
    
    private final void getOccupationListFromDB() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.OccupationList> getOccupationListQuery() {
        return null;
    }
    
    private final void getProvinceListFromDB() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.ProvinceList> getProvinceListQuery() {
        return null;
    }
    
    private final void showProvinceOptions(java.util.List<? extends com.pngelectoral.ui.model.ProvinceList> result) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LLGList> getLlgListArray() {
        return null;
    }
    
    public final void setLlgListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.WardList> getWardNoAndNameArray() {
        return null;
    }
    
    public final void setWardNoAndNameArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> p0) {
    }
    
    private final void getLocationListFromDB() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.LocationList> getLocationListQuery() {
        return null;
    }
    
    private final void callLocationListAPI() {
    }
    
    private final void showLocationOptions(java.util.List<? extends com.pngelectoral.ui.model.LocationList> result) {
    }
    
    private final void showSingleSelectionDialog(java.lang.String[] singleChoiceItems, androidx.appcompat.widget.AppCompatTextView tvSelection, java.lang.String selectedField) {
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
}