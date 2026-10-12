package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00bc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\\\u001a\u00020]H\u0002J\b\u0010^\u001a\u00020]H\u0002J\b\u0010_\u001a\u00020]H\u0002J\b\u0010`\u001a\u00020]H\u0002J\u0010\u0010a\u001a\n\u0012\u0004\u0012\u00020F\u0018\u00010bH\u0002J\b\u0010c\u001a\u00020]H\u0003J\u0010\u0010d\u001a\u00020]2\u0006\u0010e\u001a\u00020fH\u0002J\b\u0010g\u001a\u00020]H\u0003J\b\u0010h\u001a\u00020]H\u0002J\u0012\u0010i\u001a\u00020]2\b\u0010j\u001a\u0004\u0018\u00010kH\u0014J\b\u0010l\u001a\u00020]H\u0014J\b\u0010m\u001a\u00020]H\u0002J\b\u0010n\u001a\u00020]H\u0002J\b\u0010o\u001a\u00020]H\u0002J\b\u0010p\u001a\u00020]H\u0002J\u0010\u0010q\u001a\u00020]2\u0006\u0010r\u001a\u00020\u0019H\u0002J\u0018\u0010s\u001a\u00020]2\u000e\u0010t\u001a\n\u0012\u0004\u0012\u00020\'\u0018\u00010bH\u0002J\u0018\u0010u\u001a\u00020]2\u000e\u0010t\u001a\n\u0012\u0004\u0012\u000200\u0018\u00010bH\u0002J\b\u0010v\u001a\u00020]H\u0002J+\u0010w\u001a\u00020]2\f\u0010x\u001a\b\u0012\u0004\u0012\u00020\u00190y2\u0006\u0010z\u001a\u00020{2\u0006\u0010|\u001a\u00020\u0019H\u0002\u00a2\u0006\u0002\u0010}J\u0018\u0010~\u001a\u00020]2\u000e\u0010t\u001a\n\u0012\u0004\u0012\u00020V\u0018\u00010bH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u00020\rX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010\"\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010\u0015\"\u0004\b$\u0010\u0017R\"\u0010%\u001a\n\u0012\u0004\u0012\u00020\'\u0018\u00010&X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b(\u0010)\"\u0004\b*\u0010+R\u001a\u0010,\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b-\u0010\u001b\"\u0004\b.\u0010\u001dR\u0016\u0010/\u001a\n\u0012\u0004\u0012\u000200\u0018\u00010&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00101\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b2\u0010\u0015\"\u0004\b3\u0010\u0017R\u001a\u00104\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b5\u0010\u001b\"\u0004\b6\u0010\u001dR\u001a\u00107\u001a\u000208X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010:\"\u0004\b;\u0010<R\u000e\u0010=\u001a\u00020>X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010?\u001a\u0004\u0018\u00010@X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010A\u001a\u00020>X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010B\u001a\n D*\u0004\u0018\u00010C0CX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010E\u001a\b\u0012\u0004\u0012\u00020F0&X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010G\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bH\u0010\u0015\"\u0004\bI\u0010\u0017R\u0010\u0010J\u001a\u0004\u0018\u00010KX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010L\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010M\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010N\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010\u0015\"\u0004\bP\u0010\u0017R\u001a\u0010Q\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bR\u0010\u001b\"\u0004\bS\u0010\u001dR\u000e\u0010T\u001a\u00020\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010U\u001a\n\u0012\u0004\u0012\u00020V\u0018\u00010&X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bW\u0010)\"\u0004\bX\u0010+R\u001a\u0010Y\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bZ\u0010\u0015\"\u0004\b[\u0010\u0017\u00a8\u0006\u007f"}, d2 = {"Lcom/pngelectoral/ui/activity/FieldTallySummarySheetReportSelectionActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityFieldTallySummarySheetReportSelectionBinding;", "bindingDatePicker", "Lcom/pngelectoral/databinding/DialogDatePickerBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "districtName", "", "getDistrictName", "()Ljava/lang/String;", "setDistrictName", "(Ljava/lang/String;)V", "endDate", "endDateAPI", "job", "Lkotlinx/coroutines/Job;", "llgId", "getLlgId", "setLlgId", "llgListArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/LLGList;", "getLlgListArray", "()Ljava/util/ArrayList;", "setLlgListArray", "(Ljava/util/ArrayList;)V", "llgName", "getLlgName", "setLlgName", "locationArray", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "locationName", "getLocationName", "setLocationName", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mAPIDateFormat", "Ljava/text/SimpleDateFormat;", "mAlertDatePickerDialog", "Landroid/app/AlertDialog;", "mDisplayDateFormat", "mSelectedCalender", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "mkaLocationArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward$Location;", "provinceId", "getProvinceId", "setProvinceId", "registrationViewModel", "Lcom/pngelectoral/ui/viewModel/RegistrationViewModel;", "startDate", "startDateAPI", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "wardNoAndName", "wardNoAndNameArray", "Lcom/pngelectoral/ui/model/WardList;", "getWardNoAndNameArray", "setWardNoAndNameArray", "wardNumber", "getWardNumber", "setWardNumber", "callLocationListAPI", "", "getLLGListUsingCoRoutines", "getLocationListFromDB", "getMKALocationListFromDB", "getMKALocationListQuery", "", "getMKAWardNumberAndNameUsingCoroutines", "getWardNoAndNameListUsingCoRoutines", "llgAssigned", "", "getWardNumberAndNameUsingCoroutines", "navigateToFieldTallySummarySheetReportModule", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "selectEndDate", "selectStartDate", "setFooterData", "setOnclickListeners", "showAlertMessage", "alertMessage", "showLLGOptions", "result", "showLocationOptions", "showMKALocationOptions", "showSingleSelectionDialog", "singleChoiceItems", "", "tvSelection", "Landroidx/appcompat/widget/AppCompatTextView;", "selectedField", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;Ljava/lang/String;)V", "showWardNoAndNameOptions", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class FieldTallySummarySheetReportSelectionActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityFieldTallySummarySheetReportSelectionBinding binding;
    private com.pngelectoral.ui.viewModel.RegistrationViewModel registrationViewModel;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
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
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> mkaLocationArray;
    private kotlinx.coroutines.Job job;
    private final java.util.Calendar mSelectedCalender = null;
    private final java.text.SimpleDateFormat mDisplayDateFormat = null;
    private final java.text.SimpleDateFormat mAPIDateFormat = null;
    private com.pngelectoral.databinding.DialogDatePickerBinding bindingDatePicker;
    private android.app.AlertDialog mAlertDatePickerDialog;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardNoAndNameArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private java.util.HashMap _$_findViewCache;
    
    public FieldTallySummarySheetReportSelectionActivity() {
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
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getWardNumberAndNameUsingCoroutines() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getMKAWardNumberAndNameUsingCoroutines() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setFooterData() {
    }
    
    private final void setOnclickListeners() {
    }
    
    private final void navigateToFieldTallySummarySheetReportModule() {
    }
    
    private final void selectStartDate() {
    }
    
    private final void selectEndDate() {
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