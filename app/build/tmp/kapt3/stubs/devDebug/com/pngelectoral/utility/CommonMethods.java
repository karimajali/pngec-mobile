package com.pngelectoral.utility;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00b8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tJ\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\r\u001a\u00020\u000bJ\u0010\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u000f\u001a\u00020\u000bJ\u0012\u0010\u0010\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000bJ\u0012\u0010\u0012\u001a\u0004\u0018\u00010\u000b2\b\u0010\u0011\u001a\u0004\u0018\u00010\u000bJ\u001f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016\u00a2\u0006\u0002\u0010\u0018J\u000e\u0010\u0019\u001a\u00020\u000b2\u0006\u0010\u001a\u001a\u00020\u001bJ\u0016\u0010\u001c\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u0010\u0010!\u001a\u00020\u000b2\b\u0010\"\u001a\u0004\u0018\u00010\u000bJ\u0018\u0010#\u001a\u00020\u000b2\b\u0010\"\u001a\u0004\u0018\u00010\u000b2\u0006\u0010$\u001a\u00020\u000bJ\u0018\u0010%\u001a\u00020\u000b2\b\u0010\"\u001a\u0004\u0018\u00010\u000b2\u0006\u0010$\u001a\u00020\u000bJ\b\u0010&\u001a\u0004\u0018\u00010\u000bJ\u0017\u0010\'\u001a\u0004\u0018\u00010(2\b\u0010)\u001a\u0004\u0018\u00010(\u00a2\u0006\u0002\u0010*J\u0017\u0010+\u001a\u0004\u0018\u00010(2\b\u0010)\u001a\u0004\u0018\u00010\u000b\u00a2\u0006\u0002\u0010,J\u001f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0016\u00a2\u0006\u0002\u0010\u0018J\u000e\u00100\u001a\u0002012\u0006\u00102\u001a\u00020\u000bJ\u001f\u00103\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\u0016\u00a2\u0006\u0002\u0010\u0018J\u0017\u00106\u001a\u0004\u0018\u0001072\b\u0010)\u001a\u0004\u0018\u000107\u00a2\u0006\u0002\u00108J\u001f\u00109\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u0010:\u001a\b\u0012\u0004\u0012\u00020;0\u0016\u00a2\u0006\u0002\u0010\u0018J\u001f\u0010<\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u0010=\u001a\b\u0012\u0004\u0012\u00020>0\u0016\u00a2\u0006\u0002\u0010\u0018J\u001f\u0010?\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u0010@\u001a\b\u0012\u0004\u0012\u00020A0\u0016\u00a2\u0006\u0002\u0010\u0018J\f\u0010B\u001a\b\u0012\u0004\u0012\u00020D0CJ\f\u0010E\u001a\b\u0012\u0004\u0012\u00020D0CJ\u0017\u0010F\u001a\u0004\u0018\u00010\u000b2\b\u0010)\u001a\u0004\u0018\u00010(\u00a2\u0006\u0002\u0010GJ\u0010\u0010F\u001a\u00020\u000b2\b\u0010)\u001a\u0004\u0018\u00010\u000bJ\u0015\u0010H\u001a\u00020\u00072\b\u0010)\u001a\u0004\u0018\u00010\u0007\u00a2\u0006\u0002\u0010IJ\b\u0010J\u001a\u0004\u0018\u00010\u000bJ\b\u0010K\u001a\u0004\u0018\u00010\u000bJ\u0016\u0010L\u001a\u00020\u000b2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 J\u0012\u0010M\u001a\u0004\u0018\u00010\u000b2\b\u0010)\u001a\u0004\u0018\u00010NJ\u0010\u0010M\u001a\u00020\u000b2\b\u0010)\u001a\u0004\u0018\u00010;J\u0012\u0010M\u001a\u0004\u0018\u00010\u000b2\b\u0010)\u001a\u0004\u0018\u00010OJ\u0012\u0010M\u001a\u0004\u0018\u00010\u000b2\b\u0010)\u001a\u0004\u0018\u00010\u001eJ\u0012\u0010P\u001a\u0004\u0018\u00010\u000b2\b\u0010)\u001a\u0004\u0018\u00010\u001eJ\u001f\u0010Q\u001a\b\u0012\u0004\u0012\u00020\u000b0\u00142\f\u0010R\u001a\b\u0012\u0004\u0012\u00020S0\u0016\u00a2\u0006\u0002\u0010\u0018J\u0006\u0010T\u001a\u00020UJ\u0016\u0010V\u001a\u00020U2\u0006\u0010\u001f\u001a\u00020\t2\u0006\u0010W\u001a\u00020XJ\u0006\u0010Y\u001a\u00020UJ\u0010\u0010Z\u001a\u00020\u00072\u0006\u0010[\u001a\u00020\tH\u0002J\u0010\u0010\\\u001a\u00020\u00072\b\u0010\"\u001a\u0004\u0018\u00010\u000bJ\u0016\u0010]\u001a\u00020\u00072\u0006\u0010^\u001a\u00020\u001e2\u0006\u0010_\u001a\u00020\u000bJ\u000e\u0010`\u001a\u00020U2\u0006\u0010\u001f\u001a\u00020\tJ\u0010\u0010a\u001a\u00020U2\u0006\u0010\u001f\u001a\u00020\tH\u0007J\u0010\u0010b\u001a\u00020U2\u0006\u0010\u001f\u001a\u00020\tH\u0007J\u0010\u0010c\u001a\u00020U2\b\u0010\u001f\u001a\u0004\u0018\u00010\tJ\u000e\u0010d\u001a\u00020U2\u0006\u0010e\u001a\u00020(R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006f"}, d2 = {"Lcom/pngelectoral/utility/CommonMethods;", "", "()V", "mDownloadProgressDialog", "Landroid/app/ProgressDialog;", "mProgressDialog", "checkInternetConnection", "", "mActivity", "Landroid/content/Context;", "decryptDcPassWord", "", "text", "key", "getDOBAPIString", "dateOfBirth", "getDisplayDate", "postedDate", "getDisplayDateAndTime", "getDistrictList", "", "districts", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/DistrictList;", "(Ljava/util/ArrayList;)[Ljava/lang/String;", "getExternalSdCardPath", "activity", "Landroid/app/Activity;", "getForm11ImageFileName", "voter", "Lcom/pngelectoral/ui/model/VotersList;", "mContext", "Lcom/pngelectoral/ui/activity/SettingsActivity;", "getForm11Number", "form11Number", "getForm11NumberForStagingReport", "filterType", "getForm11NumberManualAndScanForReport", "getGenderLessVotersReportPostedDateTime", "getIntegerVoterDetails", "", "model", "(Ljava/lang/Integer;)Ljava/lang/Integer;", "getIntegerVoterDetailsFromString", "(Ljava/lang/String;)Ljava/lang/Integer;", "getLLGList", "llgs", "Lcom/pngelectoral/ui/model/LLGList;", "getLatitudeOrLongitude", "", "latitudeOrLongitude", "getLocationList", "locations", "Lcom/pngelectoral/ui/model/LocationList;", "getLongVoterDetails", "", "(Ljava/lang/Long;)Ljava/lang/Long;", "getPDPOList", "dpos", "Lcom/pngelectoral/ui/model/DpoList;", "getProvinceList", "provinces", "Lcom/pngelectoral/ui/model/ProvinceList;", "getRegionList", "regions", "Lcom/pngelectoral/ui/model/RegionList;", "getStatusFilterList", "", "Lcom/pngelectoral/ui/model/VoterListFilterModel;", "getStatusFilterListForSync", "getVoterDetails", "(Ljava/lang/Integer;)Ljava/lang/String;", "getVoterDetailsBoolean", "(Ljava/lang/Boolean;)Z", "getVoterDetailsPostedDateTime", "getVoterDetailsUpdatedDate", "getVoterImageFileName", "getVoterName", "Lcom/pngelectoral/ui/model/DownloadedVotersList;", "Lcom/pngelectoral/ui/model/NewVotersList;", "getVoterNameForTransferIn", "getWardList", "wards", "Lcom/pngelectoral/ui/model/WardList;", "hideDownloadProgress", "", "hideKeypad", "view", "Landroid/view/View;", "hideProgress", "isInternetAvailable", "context", "isManualForm11Number", "isProvisionalVoter", "wardVoter", "role", "openSettings", "showDownloadProgress", "showProgress", "showSettingsDialog", "updateDownloadProgress", "percentage", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class CommonMethods {
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.utility.CommonMethods INSTANCE = null;
    private static android.app.ProgressDialog mProgressDialog;
    private static android.app.ProgressDialog mDownloadProgressDialog;
    
    private CommonMethods() {
        super();
    }
    
    public final void hideKeypad(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, @org.jetbrains.annotations.NotNull
    android.view.View view) {
    }
    
    public final boolean checkInternetConnection(@org.jetbrains.annotations.NotNull
    android.content.Context mActivity) {
        return false;
    }
    
    private final boolean isInternetAvailable(android.content.Context context) {
        return false;
    }
    
    @android.annotation.SuppressLint(value = {"ResourceAsColor"})
    public final void showProgress(@org.jetbrains.annotations.NotNull
    android.content.Context mContext) {
    }
    
    public final void hideProgress() {
    }
    
    @android.annotation.SuppressLint(value = {"ResourceAsColor"})
    public final void showDownloadProgress(@org.jetbrains.annotations.NotNull
    android.content.Context mContext) {
    }
    
    public final void updateDownloadProgress(int percentage) {
    }
    
    public final void hideDownloadProgress() {
    }
    
    public final void showSettingsDialog(@org.jetbrains.annotations.Nullable
    android.content.Context mContext) {
    }
    
    public final void openSettings(@org.jetbrains.annotations.NotNull
    android.content.Context mContext) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterName(@org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.model.VotersList model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterName(@org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.model.NewVotersList model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterNameForTransferIn(@org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.model.VotersList model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterName(@org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.model.DownloadedVotersList model) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVoterDetails(@org.jetbrains.annotations.Nullable
    java.lang.String model) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getForm11NumberManualAndScanForReport(@org.jetbrains.annotations.Nullable
    java.lang.String form11Number, @org.jetbrains.annotations.NotNull
    java.lang.String filterType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getForm11NumberForStagingReport(@org.jetbrains.annotations.Nullable
    java.lang.String form11Number, @org.jetbrains.annotations.NotNull
    java.lang.String filterType) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getForm11Number(@org.jetbrains.annotations.Nullable
    java.lang.String form11Number) {
        return null;
    }
    
    public final boolean isManualForm11Number(@org.jetbrains.annotations.Nullable
    java.lang.String form11Number) {
        return false;
    }
    
    public final boolean getVoterDetailsBoolean(@org.jetbrains.annotations.Nullable
    java.lang.Boolean model) {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterDetails(@org.jetbrains.annotations.Nullable
    java.lang.Integer model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getIntegerVoterDetails(@org.jetbrains.annotations.Nullable
    java.lang.Integer model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Long getLongVoterDetails(@org.jetbrains.annotations.Nullable
    java.lang.Long model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.Integer getIntegerVoterDetailsFromString(@org.jetbrains.annotations.Nullable
    java.lang.String model) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDisplayDate(@org.jetbrains.annotations.Nullable
    java.lang.String postedDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDisplayDateAndTime(@org.jetbrains.annotations.Nullable
    java.lang.String postedDate) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterDetailsPostedDateTime() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getVoterDetailsUpdatedDate() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getDOBAPIString(@org.jetbrains.annotations.NotNull
    java.lang.String dateOfBirth) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getExternalSdCardPath(@org.jetbrains.annotations.NotNull
    android.app.Activity activity) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getRegionList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.RegionList> regions) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getProvinceList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinces) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getPDPOList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.DpoList> dpos) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVoterName(@org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.model.DpoList model) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getDistrictList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> districts) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getLLGList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgs) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getWardList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> wards) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String[] getLocationList(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locations) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.lang.String getGenderLessVotersReportPostedDateTime() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String decryptDcPassWord(@org.jetbrains.annotations.NotNull
    java.lang.String text, @org.jetbrains.annotations.NotNull
    java.lang.String key) {
        return null;
    }
    
    public final double getLatitudeOrLongitude(@org.jetbrains.annotations.NotNull
    java.lang.String latitudeOrLongitude) {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.pngelectoral.ui.model.VoterListFilterModel> getStatusFilterList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.List<com.pngelectoral.ui.model.VoterListFilterModel> getStatusFilterListForSync() {
        return null;
    }
    
    public final boolean isProvisionalVoter(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.VotersList wardVoter, @org.jetbrains.annotations.NotNull
    java.lang.String role) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getVoterImageFileName(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.VotersList voter, @org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.activity.SettingsActivity mContext) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getForm11ImageFileName(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.VotersList voter, @org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.activity.SettingsActivity mContext) {
        return null;
    }
}