package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00fc\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010t\u001a\u00020u2\u0006\u0010v\u001a\u00020wH\u0002J\b\u0010x\u001a\u00020uH\u0002J\u0006\u0010y\u001a\u00020%J!\u0010z\u001a\b\u0012\u0004\u0012\u00020!0{2\f\u0010|\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002\u00a2\u0006\u0002\u0010}J\b\u0010~\u001a\u00020uH\u0002J\b\u0010\u007f\u001a\u00020uH\u0002J\t\u0010\u0080\u0001\u001a\u00020uH\u0002J\t\u0010\u0081\u0001\u001a\u00020uH\u0003J\t\u0010\u0082\u0001\u001a\u00020uH\u0002J\t\u0010\u0083\u0001\u001a\u00020uH\u0002J\'\u0010\u0084\u0001\u001a\u00020\u001a2\u0007\u0010\u0085\u0001\u001a\u00020!2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020!0{H\u0002\u00a2\u0006\u0003\u0010\u0087\u0001J\u0015\u0010\u0088\u0001\u001a\u00020u2\n\u0010\u0089\u0001\u001a\u0005\u0018\u00010\u008a\u0001H\u0015J\t\u0010\u008b\u0001\u001a\u00020uH\u0014J\u0012\u0010\u008c\u0001\u001a\u00020u2\u0007\u0010\u008d\u0001\u001a\u00020!H\u0002J\u0012\u0010\u008e\u0001\u001a\u00020u2\u0007\u0010\u008d\u0001\u001a\u00020!H\u0003J\t\u0010\u008f\u0001\u001a\u00020uH\u0002J\t\u0010\u0090\u0001\u001a\u00020uH\u0002J\u0012\u0010\u0091\u0001\u001a\u00020u2\u0007\u0010\u0092\u0001\u001a\u00020!H\u0002J\u001e\u0010\u0091\u0001\u001a\u00020u2\u0007\u0010\u0092\u0001\u001a\u00020!2\n\u0010\u0093\u0001\u001a\u0005\u0018\u00010\u0094\u0001H\u0002J\t\u0010\u0095\u0001\u001a\u00020uH\u0002J\t\u0010\u0096\u0001\u001a\u00020uH\u0002J\u001a\u0010\u0097\u0001\u001a\u00020u2\u000f\u0010\u0098\u0001\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010`H\u0002J\u0018\u0010\u0099\u0001\u001a\u00020u2\r\u0010\u009a\u0001\u001a\b\u0012\u0004\u0012\u00020B0\rH\u0002J\u0018\u0010\u009b\u0001\u001a\u00020u2\r\u0010\u009c\u0001\u001a\b\u0012\u0004\u0012\u00020Q0\rH\u0002J\u0017\u0010\u009d\u0001\u001a\u00020u2\f\u0010_\u001a\b\u0012\u0004\u0012\u00020a0`H\u0002J\t\u0010\u009e\u0001\u001a\u00020uH\u0002J\u001a\u0010\u009f\u0001\u001a\u00020u2\u000f\u0010\u0098\u0001\u001a\n\u0012\u0004\u0012\u00020f\u0018\u00010`H\u0002J(\u0010\u00a0\u0001\u001a\u00020u2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020!0{2\b\u0010\u00a1\u0001\u001a\u00030\u00a2\u0001H\u0002\u00a2\u0006\u0003\u0010\u00a3\u0001J(\u0010\u00a4\u0001\u001a\u00020u2\r\u0010\u0086\u0001\u001a\b\u0012\u0004\u0012\u00020!0{2\b\u0010\u00a1\u0001\u001a\u00030\u00a2\u0001H\u0002\u00a2\u0006\u0003\u0010\u00a3\u0001J\t\u0010\u00a5\u0001\u001a\u00020uH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u000e\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u0014X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0015\u0010\u0016\"\u0004\b\u0017\u0010\u0018R\u001a\u0010\u0019\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u001c\"\u0004\b\u001d\u0010\u001eR\u000e\u0010\u001f\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u001aX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010(\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001c\"\u0004\b+\u0010\u001eR\u0016\u0010,\u001a\n\u0012\u0004\u0012\u00020-\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010.\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b/\u0010\u001c\"\u0004\b0\u0010\u001eR\u001a\u00101\u001a\u000202X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b3\u00104\"\u0004\b5\u00106R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00109\u001a\u0004\u0018\u00010:X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010;\u001a\u00020<X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010=\u001a\n ?*\u0004\u0018\u00010>0>X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010@\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010A\u001a\b\u0012\u0004\u0012\u00020B0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010C\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bD\u0010\u001c\"\u0004\bE\u0010\u001eR\u0014\u0010F\u001a\b\u0012\u0004\u0012\u00020G0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010H\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bI\u0010\u001c\"\u0004\bJ\u0010\u001eR\u0014\u0010K\u001a\b\u0012\u0004\u0012\u00020L0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010M\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u001c\"\u0004\bO\u0010\u001eR\u0014\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010R\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bS\u0010\u001c\"\u0004\bT\u0010\u001eR\u0014\u0010U\u001a\b\u0012\u0004\u0012\u00020V0\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010W\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bX\u0010\u001c\"\u0004\bY\u0010\u001eR\u0010\u0010Z\u001a\u0004\u0018\u00010[X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\\\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b]\u0010\u001c\"\u0004\b^\u0010\u001eR\u0016\u0010_\u001a\n\u0012\u0004\u0012\u00020a\u0018\u00010`X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010b\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010\u001c\"\u0004\bd\u0010\u001eR\u0016\u0010e\u001a\n\u0012\u0004\u0012\u00020f\u0018\u00010\rX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010g\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010h\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bi\u0010\u001c\"\u0004\bj\u0010\u001eR\u000e\u0010k\u001a\u00020!X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010l\u001a\u00020\u001aX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bm\u0010\u001c\"\u0004\bn\u0010\u001eR\u001a\u0010o\u001a\u00020!X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bp\u0010q\"\u0004\br\u0010s\u00a8\u0006\u00a6\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/NewEnrolmentFormActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityNewEnrolmentFormBinding;", "bindingDatePicker", "Lcom/pngelectoral/databinding/DialogDatePickerBinding;", "bindingForm11Image", "Lcom/pngelectoral/databinding/DialogForm11ImageBinding;", "bindingYearPicker", "Lcom/pngelectoral/databinding/DialogYearPickerBinding;", "clanArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/ClanListData;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "filterClanId", "filterClanName", "", "filterClanSelectedPos", "form11ImagePath", "isProvisional", "", "job", "Lkotlinx/coroutines/Job;", "lastDateOfEnrolment", "llgId", "getLlgId", "setLlgId", "locationArray", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mAlertDatePickerDialog", "Landroid/app/AlertDialog;", "mAlertDialog", "Landroidx/appcompat/app/AlertDialog;", "mDisplayDateFormat", "Ljava/text/SimpleDateFormat;", "mSelectedCalender", "Ljava/util/Calendar;", "kotlin.jvm.PlatformType", "mYearPickerAlertDialog", "mkaClanArray", "Lcom/pngelectoral/ui/model/MKAClanListData;", "mkaClanId", "getMkaClanId", "setMkaClanId", "mkaDistrictArray", "Lcom/pngelectoral/ui/model/MKAGeoListData;", "mkaDistrictId", "getMkaDistrictId", "setMkaDistrictId", "mkaLlgArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG;", "mkaLlgId", "getMkaLlgId", "setMkaLlgId", "mkaLocationArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward$Location;", "mkaLocationId", "getMkaLocationId", "setMkaLocationId", "mkaWardArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward;", "mkaWardId", "getMkaWardId", "setMkaWardId", "newEnrolmentFormViewModel", "Lcom/pngelectoral/ui/viewModel/NewEnrolmentFormViewModel;", "occupationId", "getOccupationId", "setOccupationId", "occupationListArray", "", "Lcom/pngelectoral/ui/model/OccupationList;", "provinceId", "getProvinceId", "setProvinceId", "provinceListArray", "Lcom/pngelectoral/ui/model/ProvinceList;", "provisionalCutOffDate", "regionId", "getRegionId", "setRegionId", "voterImagePath", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "()Ljava/lang/String;", "setWardName", "(Ljava/lang/String;)V", "addNewVoterToDB", "", "updatedVoterInformation", "Lcom/pngelectoral/ui/model/VotersList;", "callLocationListAPI", "checkMkaDataValid", "getClanList", "", "clans", "(Ljava/util/ArrayList;)[Ljava/lang/String;", "getLocationListFromDB", "getMKAClanListFromDB", "getMKALocationList", "getMKAWardNumberAndNameUsingCoroutines", "getOccupationListFromDB", "getProvinceListFromDB", "getSelectedPosition", "selectedValue", "singleChoiceItems", "(Ljava/lang/String;[Ljava/lang/String;)I", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "selectDateOfBirth", "dateOfBirth", "selectDateOfBirthYear", "setFooterData", "setOnClickListeners", "showAlertMessage", "alertMessage", "currentEditText", "Landroidx/appcompat/widget/AppCompatEditText;", "showForm11Image", "showGenderOptions", "showLocationOptions", "result", "showMKAClan", "clanListArray", "showMKALocation", "locationListArray", "showOccupationWithSearch", "showPersonWithDisabilityOptions", "showProvinceOptions", "showSingleSelectionDialog", "tvSelection", "Landroidx/appcompat/widget/AppCompatTextView;", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;)V", "showSingleSelectionDialogClan", "validateVoterDetails", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class NewEnrolmentFormActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityNewEnrolmentFormBinding binding;
    private com.pngelectoral.ui.viewModel.NewEnrolmentFormViewModel newEnrolmentFormViewModel;
    private java.lang.String form11ImagePath = "";
    private java.lang.String voterImagePath = "";
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int regionId = 0;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    private int locationId = 0;
    private java.lang.String lastDateOfEnrolment = "";
    private java.lang.String provisionalCutOffDate = "";
    private java.util.List<? extends com.pngelectoral.ui.model.OccupationList> occupationListArray;
    private boolean isProvisional = false;
    
    /**
     * Define values to store selected mka data.
     */
    private int mkaDistrictId = 0;
    private int mkaLlgId = 0;
    private int mkaWardId = 0;
    private int mkaLocationId = 0;
    private int mkaClanId = 0;
    
    /**
     * Define list to store mka data.
     */
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListData> mkaDistrictArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG> mkaLlgArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward> mkaWardArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> mkaLocationArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAClanListData> mkaClanArray;
    private int filterClanId = 0;
    private int filterClanSelectedPos = -1;
    private java.lang.String filterClanName = "";
    private java.util.ArrayList<com.pngelectoral.ui.model.ClanListData> clanArray;
    private kotlinx.coroutines.Job job;
    private int occupationId = 0;
    private java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceListArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private com.pngelectoral.databinding.DialogForm11ImageBinding bindingForm11Image;
    private androidx.appcompat.app.AlertDialog mAlertDialog;
    private final java.util.Calendar mSelectedCalender = null;
    private final java.text.SimpleDateFormat mDisplayDateFormat = null;
    private com.pngelectoral.databinding.DialogDatePickerBinding bindingDatePicker;
    private android.app.AlertDialog mAlertDatePickerDialog;
    private com.pngelectoral.databinding.DialogYearPickerBinding bindingYearPicker;
    private android.app.AlertDialog mYearPickerAlertDialog;
    private java.util.HashMap _$_findViewCache;
    
    public NewEnrolmentFormActivity() {
        super();
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
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWardName() {
        return null;
    }
    
    public final void setWardName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    public final int getLocationId() {
        return 0;
    }
    
    public final void setLocationId(int p0) {
    }
    
    public final int getMkaDistrictId() {
        return 0;
    }
    
    public final void setMkaDistrictId(int p0) {
    }
    
    public final int getMkaLlgId() {
        return 0;
    }
    
    public final void setMkaLlgId(int p0) {
    }
    
    public final int getMkaWardId() {
        return 0;
    }
    
    public final void setMkaWardId(int p0) {
    }
    
    public final int getMkaLocationId() {
        return 0;
    }
    
    public final void setMkaLocationId(int p0) {
    }
    
    public final int getMkaClanId() {
        return 0;
    }
    
    public final void setMkaClanId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getMKAWardNumberAndNameUsingCoroutines() {
    }
    
    private final void setFooterData() {
    }
    
    private final void setOnClickListeners() {
    }
    
    private final void showSingleSelectionDialogClan(java.lang.String[] singleChoiceItems, androidx.appcompat.widget.AppCompatTextView tvSelection) {
    }
    
    private final java.lang.String[] getClanList(java.util.ArrayList<com.pngelectoral.ui.model.ClanListData> clans) {
        return null;
    }
    
    /**
     * Method to get mkalocation list form mka ward array.
     */
    private final void getMKALocationList() {
    }
    
    /**
     * Method to show scrollable spinner dialog for mka location.
     */
    private final void showMKALocation(java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> locationListArray) {
    }
    
    /**
     * Method to get mkaclan list form database.
     */
    private final void getMKAClanListFromDB() {
    }
    
    /**
     * Method to show scrollable spinner dialog for mka clan.
     */
    private final void showMKAClan(java.util.ArrayList<com.pngelectoral.ui.model.MKAClanListData> clanListArray) {
    }
    
    private final void showGenderOptions() {
    }
    
    private final void showPersonWithDisabilityOptions() {
    }
    
    private final void getOccupationListFromDB() {
    }
    
    public final int getOccupationId() {
        return 0;
    }
    
    public final void setOccupationId(int p0) {
    }
    
    private final void showOccupationWithSearch(java.util.List<? extends com.pngelectoral.ui.model.OccupationList> occupationListArray) {
    }
    
    private final void getProvinceListFromDB() {
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
    
    public final boolean checkMkaDataValid() {
        return false;
    }
    
    private final void addNewVoterToDB(com.pngelectoral.ui.model.VotersList updatedVoterInformation) {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage, androidx.appcompat.widget.AppCompatEditText currentEditText) {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage) {
    }
    
    private final void showForm11Image() {
    }
    
    private final void selectDateOfBirth(java.lang.String dateOfBirth) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void selectDateOfBirthYear(java.lang.String dateOfBirth) {
    }
}