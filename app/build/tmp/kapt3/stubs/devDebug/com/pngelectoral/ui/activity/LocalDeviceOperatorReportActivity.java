package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00a0\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010V\u001a\u00020WH\u0002J\b\u0010X\u001a\u00020WH\u0002J\u0016\u0010Y\u001a\u00020W2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0016\u0010[\u001a\u00020W2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0016\u0010\\\u001a\u00020W2\f\u0010Z\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0016\u0010]\u001a\u00020W2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0014\u0010^\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010\u0014H\u0002J\u0014\u0010a\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010\u0014H\u0002J\u0017\u0010b\u001a\u00020\u00142\b\u0010c\u001a\u0004\u0018\u00010dH\u0002\u00a2\u0006\u0002\u0010eJ\b\u0010f\u001a\u00020WH\u0002J\u0010\u0010g\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010hH\u0002J\b\u0010i\u001a\u00020WH\u0002J\u001e\u0010j\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010\u00142\b\u0010k\u001a\u0004\u0018\u00010lH\u0002J\u001e\u0010m\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010\u00142\b\u0010k\u001a\u0004\u0018\u00010lH\u0002J\u001e\u0010n\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010\u00142\b\u0010k\u001a\u0004\u0018\u00010lH\u0002J\u001e\u0010o\u001a\u0004\u0018\u00010_2\b\u0010`\u001a\u0004\u0018\u00010\u00142\b\u0010k\u001a\u0004\u0018\u00010lH\u0002J\u0010\u0010p\u001a\n\u0012\u0004\u0012\u00020\u0012\u0018\u00010hH\u0002J\b\u0010q\u001a\u00020WH\u0002J\u0012\u0010r\u001a\u00020W2\b\u0010s\u001a\u0004\u0018\u00010tH\u0014J\b\u0010u\u001a\u00020WH\u0014J\b\u0010v\u001a\u00020WH\u0002J\b\u0010w\u001a\u00020WH\u0002J\b\u0010x\u001a\u00020WH\u0002J\b\u0010y\u001a\u00020WH\u0003J\u0010\u0010z\u001a\u00020W2\u0006\u0010{\u001a\u00020|H\u0002J\u0010\u0010}\u001a\u00020W2\u0006\u0010{\u001a\u00020|H\u0002J\u0010\u0010~\u001a\u00020W2\u0006\u0010{\u001a\u00020|H\u0002J\u0016\u0010\u007f\u001a\u00020W2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\u0012\u0010\u0080\u0001\u001a\u00020W2\u0007\u0010\u0081\u0001\u001a\u00020\u0014H\u0002J\t\u0010\u0082\u0001\u001a\u00020WH\u0002J\u0017\u0010\u0083\u0001\u001a\u00020W2\f\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u0002J\t\u0010\u0084\u0001\u001a\u00020WH\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0015\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010!\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010&\u001a\u00020\'X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010\u001a\"\u0004\b*\u0010\u001cR\u001a\u0010+\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010\u001a\"\u0004\b-\u0010\u001cR\u000e\u0010.\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010/\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u0010\u001a\"\u0004\b1\u0010\u001cR\u0014\u00102\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00103\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u001a\"\u0004\b5\u0010\u001cR\u0014\u00106\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u00107\u001a\u0004\u0018\u000108X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00109\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010:\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010;\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010=0<X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010>\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b?\u0010\u001a\"\u0004\b@\u0010\u001cR\u001a\u0010A\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010\u001a\"\u0004\bC\u0010\u001cR\u001a\u0010D\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\u001a\"\u0004\bF\u0010\u001cR\u0014\u0010G\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010H\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010I\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010K\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bL\u0010\u001a\"\u0004\bM\u0010\u001cR\u000e\u0010N\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010O\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010P\u001a\u0010\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020R\u0018\u00010QX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010S\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u0010\u001a\"\u0004\bU\u0010\u001c\u00a8\u0006\u0085\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/LocalDeviceOperatorReportActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityDeviceOperatorReportBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "deceasedVotersList", "", "Lcom/pngelectoral/ui/model/VotersList;", "deviceId", "", "deviceRun", "deviceRunInMillis", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "dopReportFilesPath", "dpo", "dpoWardNewEnrollmentVotersList", "dpoWardStatusWiseVotersList", "dpoWardUpdatedRollVotersList", "dpoWardUpdatedRollVotersListWithSortByGenderAndLname", "duplicateVotersList", "endDate", "ghostVotersList", "job", "Lkotlinx/coroutines/Job;", "llgId", "getLlgId", "setLlgId", "locationId", "getLocationId", "setLocationId", "locationName", "newPhoto", "getNewPhoto", "setNewPhoto", "newVotersList", "provinceId", "getProvinceId", "setProvinceId", "provisionalVotersList", "registrationViewModel", "Lcom/pngelectoral/ui/viewModel/RegistrationViewModel;", "selectedDpoName", "startDate", "storagePermissionResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "totalPhotoCount", "getTotalPhotoCount", "setTotalPhotoCount", "totalWithNoPhotoCount", "getTotalWithNoPhotoCount", "setTotalWithNoPhotoCount", "totalWithPhotoCount", "getTotalWithPhotoCount", "setTotalWithPhotoCount", "transferInVotersList", "transferredOutVotersList", "underAgeVotersList", "updatedVotersList", "wardId", "getWardId", "setWardId", "wardName", "wardVotersList", "wardVotersListAdapter", "Lcom/pngelectoral/ui/adapter/GenericAdapter;", "Lcom/pngelectoral/databinding/ItemWardVoterBinding;", "withPreviousPhotoCount", "getWithPreviousPhotoCount", "setWithPreviousPhotoCount", "checkManageAllPermission", "", "exportReportPDF", "filterWardData", "list", "filterWardDataAndDisplayStatusCount", "filterWardPhotData", "filterWardPhotoDataAndDisplayStatusCount", "getBoldTextCell", "Lcom/itextpdf/layout/element/Cell;", "text", "getCell", "getDisability", "pwd", "", "(Ljava/lang/Boolean;)Ljava/lang/String;", "getPhotoVotersListUsingCoRoutines", "getUpdatedVotersList", "", "getUpdatedVotersListUsingCoRoutines", "getVotersListCell", "alignment", "Lcom/itextpdf/layout/property/TextAlignment;", "getVotersListHeaderCell", "getWardSummaryCell", "getWardSummaryHeaderCell", "getWardVotersList", "loadData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "requestStoragePermission", "setFooterData", "setOnclickListeners", "setReportHeaderData", "setWardNewEnrollmentVotersInfoTableData", "document", "Lcom/itextpdf/layout/Document;", "setWardSummaryData", "setWardUpdatedRollVotersInfoTableData", "setWardVotersListAdapter", "showAlertMessage", "alertMessage", "showDownloadSuccessMessage", "sortByGender", "writePDFFile", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class LocalDeviceOperatorReportActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityDeviceOperatorReportBinding binding;
    private com.pngelectoral.ui.viewModel.RegistrationViewModel registrationViewModel;
    private com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VotersList, com.pngelectoral.databinding.ItemWardVoterBinding> wardVotersListAdapter;
    private java.lang.String selectedDpoName = "";
    private java.lang.String startDate = "";
    private java.lang.String endDate = "";
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
    private java.lang.String deviceRun = "";
    private java.lang.String deviceRunInMillis = "";
    private java.lang.String deviceId = "";
    private java.lang.String dpo = "";
    private java.lang.String wardName = "";
    private java.lang.String locationName = "";
    private java.util.List<com.pngelectoral.ui.model.VotersList> updatedVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> newVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> provisionalVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> deceasedVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> ghostVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> duplicateVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> transferredOutVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> transferInVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> underAgeVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> dpoWardStatusWiseVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> dpoWardUpdatedRollVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> dpoWardUpdatedRollVotersListWithSortByGenderAndLname;
    private java.util.List<com.pngelectoral.ui.model.VotersList> dpoWardNewEnrollmentVotersList;
    private java.util.List<com.pngelectoral.ui.model.VotersList> wardVotersList;
    private java.lang.String dopReportFilesPath = "PNG_Device_Operator_Report";
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private kotlinx.coroutines.Job job;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> storagePermissionResultLauncher;
    private int withPreviousPhotoCount = 0;
    private int newPhoto = 0;
    private int totalWithPhotoCount = 0;
    private int totalWithNoPhotoCount = 0;
    private int totalPhotoCount = 0;
    private java.util.HashMap _$_findViewCache;
    
    public LocalDeviceOperatorReportActivity() {
        super();
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
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
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
    
    private final void getPhotoVotersListUsingCoRoutines() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> getWardVotersList() {
        return null;
    }
    
    private final void filterWardPhotData(java.util.List<com.pngelectoral.ui.model.VotersList> list) {
    }
    
    public final int getWithPreviousPhotoCount() {
        return 0;
    }
    
    public final void setWithPreviousPhotoCount(int p0) {
    }
    
    public final int getNewPhoto() {
        return 0;
    }
    
    public final void setNewPhoto(int p0) {
    }
    
    public final int getTotalWithPhotoCount() {
        return 0;
    }
    
    public final void setTotalWithPhotoCount(int p0) {
    }
    
    public final int getTotalWithNoPhotoCount() {
        return 0;
    }
    
    public final void setTotalWithNoPhotoCount(int p0) {
    }
    
    public final int getTotalPhotoCount() {
        return 0;
    }
    
    public final void setTotalPhotoCount(int p0) {
    }
    
    private final void filterWardPhotoDataAndDisplayStatusCount(java.util.List<com.pngelectoral.ui.model.VotersList> wardVotersList) {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "HardwareIds"})
    private final void setReportHeaderData() {
    }
    
    private final void setFooterData() {
    }
    
    private final void setOnclickListeners() {
    }
    
    private final void getUpdatedVotersListUsingCoRoutines() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> getUpdatedVotersList() {
        return null;
    }
    
    private final void filterWardData(java.util.List<com.pngelectoral.ui.model.VotersList> list) {
    }
    
    private final void filterWardDataAndDisplayStatusCount(java.util.List<com.pngelectoral.ui.model.VotersList> wardVotersList) {
    }
    
    private final void sortByGender(java.util.List<com.pngelectoral.ui.model.VotersList> dpoWardUpdatedRollVotersList) {
    }
    
    private final void loadData() {
    }
    
    private final void setWardVotersListAdapter(java.util.List<com.pngelectoral.ui.model.VotersList> dpoWardStatusWiseVotersList) {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage) {
    }
    
    private final void requestStoragePermission() {
    }
    
    private final void checkManageAllPermission() {
    }
    
    private final void exportReportPDF() {
    }
    
    private final void writePDFFile() {
    }
    
    private final com.itextpdf.layout.element.Cell getBoldTextCell(java.lang.String text) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getCell(java.lang.String text) {
        return null;
    }
    
    private final void setWardUpdatedRollVotersInfoTableData(com.itextpdf.layout.Document document) {
    }
    
    private final void setWardNewEnrollmentVotersInfoTableData(com.itextpdf.layout.Document document) {
    }
    
    private final com.itextpdf.layout.element.Cell getVotersListHeaderCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getVotersListCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final java.lang.String getDisability(java.lang.Boolean pwd) {
        return null;
    }
    
    private final void setWardSummaryData(com.itextpdf.layout.Document document) {
    }
    
    private final com.itextpdf.layout.element.Cell getWardSummaryHeaderCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getWardSummaryCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final void showDownloadSuccessMessage() {
    }
}