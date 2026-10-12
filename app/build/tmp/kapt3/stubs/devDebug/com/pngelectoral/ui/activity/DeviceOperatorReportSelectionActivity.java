package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00d0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010l\u001a\u00020mH\u0002J\b\u0010n\u001a\u00020mH\u0002J\b\u0010o\u001a\u00020mH\u0002J\b\u0010p\u001a\u00020mH\u0002J\b\u0010q\u001a\u00020mH\u0002J\b\u0010r\u001a\u00020mH\u0002J\b\u0010s\u001a\u00020mH\u0002J\u0010\u0010t\u001a\n\u0012\u0004\u0012\u00020L\u0018\u00010uH\u0002J\b\u0010v\u001a\u00020mH\u0003J\u0010\u0010w\u001a\u00020m2\u0006\u0010x\u001a\u00020WH\u0002J\b\u0010y\u001a\u00020mH\u0003J\b\u0010z\u001a\u00020mH\u0002J\b\u0010{\u001a\u00020mH\u0002J\u0012\u0010|\u001a\u00020m2\b\u0010}\u001a\u0004\u0018\u00010~H\u0014J\b\u0010\u007f\u001a\u00020mH\u0014J\t\u0010\u0080\u0001\u001a\u00020mH\u0002J\t\u0010\u0081\u0001\u001a\u00020mH\u0002J\t\u0010\u0082\u0001\u001a\u00020mH\u0002J\t\u0010\u0083\u0001\u001a\u00020mH\u0002J\u0012\u0010\u0084\u0001\u001a\u00020m2\u0007\u0010\u0085\u0001\u001a\u00020\u001cH\u0002J\u001a\u0010\u0086\u0001\u001a\u00020m2\u000f\u0010\u0087\u0001\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010uH\u0002J\u001a\u0010\u0088\u0001\u001a\u00020m2\u000f\u0010\u0087\u0001\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010uH\u0002J\u001a\u0010\u0089\u0001\u001a\u00020m2\u000f\u0010\u0087\u0001\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010uH\u0002J\u001a\u0010\u008a\u0001\u001a\u00020m2\u000f\u0010\u0087\u0001\u001a\n\u0012\u0004\u0012\u000206\u0018\u00010uH\u0002J\t\u0010\u008b\u0001\u001a\u00020mH\u0002J\u001a\u0010\u008c\u0001\u001a\u00020m2\u000f\u0010\u0087\u0001\u001a\n\u0012\u0004\u0012\u00020Q\u0018\u00010uH\u0002J2\u0010\u008d\u0001\u001a\u00020m2\u000e\u0010\u008e\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0\u008f\u00012\b\u0010\u0090\u0001\u001a\u00030\u0091\u00012\u0007\u0010\u0092\u0001\u001a\u00020\u001cH\u0002\u00a2\u0006\u0003\u0010\u0093\u0001J\u001a\u0010\u0094\u0001\u001a\u00020m2\u000f\u0010\u0087\u0001\u001a\n\u0012\u0004\u0012\u00020f\u0018\u00010uH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0016\u0010\u0018\u001a\n\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\"\u0010!\u001a\n\u0012\u0004\u0012\u00020\"\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u000e\u0010\'\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010)\u001a\u00020*X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010+\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u0015\"\u0004\b-\u0010\u0017R\"\u0010.\u001a\n\u0012\u0004\u0012\u00020/\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010$\"\u0004\b1\u0010&R\u001a\u00102\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u0010\u001e\"\u0004\b4\u0010 R\u0016\u00105\u001a\n\u0012\u0004\u0012\u000206\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00107\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b8\u0010\u0015\"\u0004\b9\u0010\u0017R\u001a\u0010:\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010\u001e\"\u0004\b<\u0010 R\u001a\u0010=\u001a\u00020>X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010@\"\u0004\bA\u0010BR\u000e\u0010C\u001a\u00020DX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010E\u001a\u0004\u0018\u00010FX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010G\u001a\u00020DX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010H\u001a\n J*\u0004\u0018\u00010I0IX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010K\u001a\b\u0012\u0004\u0012\u00020L0\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010M\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u0015\"\u0004\bO\u0010\u0017R\"\u0010P\u001a\n\u0012\u0004\u0012\u00020Q\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010$\"\u0004\bS\u0010&R\u0010\u0010T\u001a\u0004\u0018\u00010UX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010V\u001a\u00020WX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010X\u001a\u00020\"X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010Y\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010\u001e\"\u0004\b[\u0010 R\u000e\u0010\\\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010]\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010^\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010\u0015\"\u0004\b`\u0010\u0017R\u001a\u0010a\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010\u001e\"\u0004\bc\u0010 R\u000e\u0010d\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010e\u001a\n\u0012\u0004\u0012\u00020f\u0018\u00010\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bg\u0010$\"\u0004\bh\u0010&R\u001a\u0010i\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bj\u0010\u0015\"\u0004\bk\u0010\u0017\u00a8\u0006\u0095\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/DeviceOperatorReportSelectionActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityDeviceOperatorReportSelectionBinding;", "bindingDatePicker", "Lcom/pngelectoral/databinding/DialogDatePickerBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "districtListArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/DistrictList;", "districtName", "", "getDistrictName", "()Ljava/lang/String;", "setDistrictName", "(Ljava/lang/String;)V", "dpoListArray", "Lcom/pngelectoral/ui/model/DpoList;", "getDpoListArray", "()Ljava/util/ArrayList;", "setDpoListArray", "(Ljava/util/ArrayList;)V", "endDate", "endDateAPI", "job", "Lkotlinx/coroutines/Job;", "llgId", "getLlgId", "setLlgId", "llgListArray", "Lcom/pngelectoral/ui/model/LLGList;", "getLlgListArray", "setLlgListArray", "llgName", "getLlgName", "setLlgName", "locationArray", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "locationName", "getLocationName", "setLocationName", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mAPIDateFormat", "Ljava/text/SimpleDateFormat;", "mAlertDatePickerDialog", "Landroid/app/AlertDialog;", "mDisplayDateFormat", "mSelectedCalender", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "mkaLocationArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward$Location;", "provinceId", "getProvinceId", "setProvinceId", "provinceListArray", "Lcom/pngelectoral/ui/model/ProvinceList;", "getProvinceListArray", "setProvinceListArray", "registrationViewModel", "Lcom/pngelectoral/ui/viewModel/RegistrationViewModel;", "reportForStaging", "", "selectedDPO", "selectedDPOName", "getSelectedDPOName", "setSelectedDPOName", "startDate", "startDateAPI", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "wardNoAndName", "wardNoAndNameArray", "Lcom/pngelectoral/ui/model/WardList;", "getWardNoAndNameArray", "setWardNoAndNameArray", "wardNumber", "getWardNumber", "setWardNumber", "callLocationListAPI", "", "getAllDPOUsingCoRoutines", "getAllProvinceUsingCoRoutines", "getDistrictUsingCoRoutines", "getLLGListUsingCoRoutines", "getLocationListFromDB", "getMKALocationListFromDB", "getMKALocationListQuery", "", "getMKAWardNumberAndNameUsingCoroutines", "getWardNoAndNameListUsingCoRoutines", "llgAssigned", "getWardNumberAndNameUsingCoroutines", "navigateToDeviceOperatorReportModule", "navigateToStagingReportScreen", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "selectEndDate", "selectStartDate", "setFooterData", "setOnclickListeners", "showAlertMessage", "alertMessage", "showDistrictOptions", "result", "showDpoListOptions", "showLLGOptions", "showLocationOptions", "showMKALocationOptions", "showProvinceOptions", "showSingleSelectionDialog", "singleChoiceItems", "", "tvSelection", "Landroidx/appcompat/widget/AppCompatTextView;", "selectedField", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;Ljava/lang/String;)V", "showWardNoAndNameOptions", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DeviceOperatorReportSelectionActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityDeviceOperatorReportSelectionBinding binding;
    private com.pngelectoral.ui.viewModel.RegistrationViewModel registrationViewModel;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String selectedDPOName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String districtName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String llgName = "";
    private java.lang.String wardNoAndName = "";
    private int wardNumber = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String locationName = "";
    private java.lang.String startDate = "";
    private java.lang.String endDate = "";
    private java.lang.String startDateAPI = "";
    private java.lang.String endDateAPI = "";
    private boolean reportForStaging = false;
    private com.pngelectoral.ui.model.DpoList selectedDPO;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> mkaLocationArray;
    private kotlinx.coroutines.Job job;
    private final java.util.Calendar mSelectedCalender = null;
    private final java.text.SimpleDateFormat mDisplayDateFormat = null;
    private final java.text.SimpleDateFormat mAPIDateFormat = null;
    private com.pngelectoral.databinding.DialogDatePickerBinding bindingDatePicker;
    private android.app.AlertDialog mAlertDatePickerDialog;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.DpoList> dpoListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceListArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> districtListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardNoAndNameArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private java.util.HashMap _$_findViewCache;
    
    public DeviceOperatorReportSelectionActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
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
    public final java.lang.String getSelectedDPOName() {
        return null;
    }
    
    public final void setSelectedDPOName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDistrictName() {
        return null;
    }
    
    public final void setDistrictName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLlgName() {
        return null;
    }
    
    public final void setLlgName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    public final int getWardNumber() {
        return 0;
    }
    
    public final void setWardNumber(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWardName() {
        return null;
    }
    
    public final void setWardName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLocationName() {
        return null;
    }
    
    public final void setLocationName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.DPOUserData getLoggedInDPOUserData() {
        return null;
    }
    
    public final void setLoggedInDPOUserData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.DPOUserData p0) {
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
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getWardNumberAndNameUsingCoroutines() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getMKAWardNumberAndNameUsingCoroutines() {
    }
    
    private final void setFooterData() {
    }
    
    private final void setOnclickListeners() {
    }
    
    private final void navigateToDeviceOperatorReportModule() {
    }
    
    private final void navigateToStagingReportScreen() {
    }
    
    private final void selectStartDate() {
    }
    
    private final void selectEndDate() {
    }
    
    private final void getAllDPOUsingCoRoutines() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.DpoList> getDpoListArray() {
        return null;
    }
    
    public final void setDpoListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.DpoList> p0) {
    }
    
    private final void showDpoListOptions(java.util.List<? extends com.pngelectoral.ui.model.DpoList> result) {
    }
    
    private final void getAllProvinceUsingCoRoutines() {
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
    
    private final void getDistrictUsingCoRoutines() {
    }
    
    private final void showDistrictOptions(java.util.List<? extends com.pngelectoral.ui.model.DistrictList> result) {
    }
    
    private final void getLLGListUsingCoRoutines() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LLGList> getLlgListArray() {
        return null;
    }
    
    public final void setLlgListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> p0) {
    }
    
    private final void showLLGOptions(java.util.List<? extends com.pngelectoral.ui.model.LLGList> result) {
    }
    
    private final void getWardNoAndNameListUsingCoRoutines(boolean llgAssigned) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.WardList> getWardNoAndNameArray() {
        return null;
    }
    
    public final void setWardNoAndNameArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> p0) {
    }
    
    private final void showWardNoAndNameOptions(java.util.List<? extends com.pngelectoral.ui.model.WardList> result) {
    }
    
    private final void getLocationListFromDB() {
    }
    
    private final void getMKALocationListFromDB() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> getMKALocationListQuery() {
        return null;
    }
    
    private final void showMKALocationOptions() {
    }
    
    private final void showLocationOptions(java.util.List<? extends com.pngelectoral.ui.model.LocationList> result) {
    }
    
    private final void showSingleSelectionDialog(java.lang.String[] singleChoiceItems, androidx.appcompat.widget.AppCompatTextView tvSelection, java.lang.String selectedField) {
    }
    
    private final void callLocationListAPI() {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage) {
    }
}