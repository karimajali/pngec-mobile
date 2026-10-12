package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0094\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u00108\u001a\u000209H\u0002J\b\u0010:\u001a\u000209H\u0002J\u0014\u0010;\u001a\u0004\u0018\u00010<2\b\u0010=\u001a\u0004\u0018\u00010\u0011H\u0002J\u0014\u0010>\u001a\u0004\u0018\u00010<2\b\u0010=\u001a\u0004\u0018\u00010\u0011H\u0002J\u001e\u0010?\u001a\u0004\u0018\u00010<2\b\u0010=\u001a\u0004\u0018\u00010\u00112\b\u0010@\u001a\u0004\u0018\u00010AH\u0002J\u000e\u0010B\u001a\b\u0012\u0004\u0012\u00020\u00160CH\u0002J\b\u0010D\u001a\u000209H\u0002J\u001e\u0010E\u001a\u0004\u0018\u00010<2\b\u0010=\u001a\u0004\u0018\u00010\u00112\b\u0010@\u001a\u0004\u0018\u00010AH\u0002J\u001e\u0010F\u001a\u0004\u0018\u00010<2\b\u0010=\u001a\u0004\u0018\u00010\u00112\b\u0010@\u001a\u0004\u0018\u00010AH\u0002J\b\u0010G\u001a\u000209H\u0002J\u0012\u0010H\u001a\u0002092\b\u0010I\u001a\u0004\u0018\u00010JH\u0014J\b\u0010K\u001a\u000209H\u0014J\b\u0010L\u001a\u000209H\u0002J\u0016\u0010M\u001a\u0002092\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0002J\u0010\u0010N\u001a\u0002092\u0006\u0010O\u001a\u00020PH\u0002J\u0010\u0010Q\u001a\u0002092\u0006\u0010O\u001a\u00020PH\u0002J\b\u0010R\u001a\u000209H\u0002J\b\u0010S\u001a\u000209H\u0003J\u0010\u0010T\u001a\u0002092\u0006\u0010U\u001a\u00020\u0011H\u0002J\b\u0010V\u001a\u000209H\u0002J\b\u0010W\u001a\u000209H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0013\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0017\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u000e\u0010\u001d\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u001f\u001a\u0010\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020!\u0018\u00010 X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010%\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\u001a\"\u0004\b\'\u0010\u001cR\u000e\u0010(\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010)\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010\u001a\"\u0004\b+\u0010\u001cR\u000e\u0010,\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010-\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u00100\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010201X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u00103\u001a\u00020\u0018X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b4\u0010\u001a\"\u0004\b5\u0010\u001cR\u000e\u00106\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00107\u001a\u00020\u0018X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006X"}, d2 = {"Lcom/pngelectoral/ui/activity/GenderLessVoterListReportActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityGenderLessVotersListReportBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "deviceId", "", "deviceRun", "deviceRunInMillis", "districtGenderLessVotersList", "", "Lcom/pngelectoral/ui/model/VotersList;", "districtId", "", "getDistrictId", "()I", "setDistrictId", "(I)V", "districtName", "dpoReportFilesPath", "genderLessVotersListAdapter", "Lcom/pngelectoral/ui/adapter/GenericAdapter;", "Lcom/pngelectoral/databinding/ItemGenderLessVoterBinding;", "geoLocationName", "job", "Lkotlinx/coroutines/Job;", "llgId", "getLlgId", "setLlgId", "llgName", "locationId", "getLocationId", "setLocationId", "locationName", "provinceName", "registrationViewModel", "Lcom/pngelectoral/ui/viewModel/RegistrationViewModel;", "storagePermissionResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "wardId", "getWardId", "setWardId", "wardName", "wardNumber", "checkManageAllPermission", "", "exportReportPDF", "getBoldTextCell", "Lcom/itextpdf/layout/element/Cell;", "text", "getCell", "getCountHeaderCell", "alignment", "Lcom/itextpdf/layout/property/TextAlignment;", "getGenderLessVotersList", "", "getGenderLessVotersListUsingCoRoutines", "getVotersListCell", "getVotersListHeaderCell", "loadData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "requestStoragePermission", "setDistrictGenderLessListAdapter", "setGenderLessVoters", "document", "Lcom/itextpdf/layout/Document;", "setGrandTotalCount", "setOnclickListeners", "setReportHeaderData", "showAlertMessage", "alertMessage", "showDownloadSuccessMessage", "writePDFFile", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class GenderLessVoterListReportActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityGenderLessVotersListReportBinding binding;
    private com.pngelectoral.ui.viewModel.RegistrationViewModel registrationViewModel;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
    private java.lang.String deviceRun = "";
    private java.lang.String deviceRunInMillis = "";
    private java.lang.String deviceId = "";
    private java.lang.String provinceName = "";
    private java.lang.String districtName = "";
    private java.lang.String llgName = "";
    private java.lang.String wardName = "";
    private java.lang.String locationName = "";
    private int wardNumber = 0;
    private java.lang.String geoLocationName = "";
    private java.lang.String dpoReportFilesPath = "PNG_District_Genderless_Voterslist_Report";
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private java.util.List<com.pngelectoral.ui.model.VotersList> districtGenderLessVotersList;
    private kotlinx.coroutines.Job job;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> storagePermissionResultLauncher;
    private com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VotersList, com.pngelectoral.databinding.ItemGenderLessVoterBinding> genderLessVotersListAdapter;
    private java.util.HashMap _$_findViewCache;
    
    public GenderLessVoterListReportActivity() {
        super();
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
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n", "HardwareIds"})
    private final void setReportHeaderData() {
    }
    
    private final void setOnclickListeners() {
    }
    
    private final void getGenderLessVotersListUsingCoRoutines() {
    }
    
    private final void loadData() {
    }
    
    private final void setDistrictGenderLessListAdapter(java.util.List<com.pngelectoral.ui.model.VotersList> districtGenderLessVotersList) {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> getGenderLessVotersList() {
        return null;
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
    
    private final void setGenderLessVoters(com.itextpdf.layout.Document document) {
    }
    
    private final com.itextpdf.layout.element.Cell getVotersListHeaderCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getVotersListCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final void setGrandTotalCount(com.itextpdf.layout.Document document) {
    }
    
    private final com.itextpdf.layout.element.Cell getCountHeaderCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final void showDownloadSuccessMessage() {
    }
}