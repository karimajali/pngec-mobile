package com.pngelectoral.datasync;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010 \u001a\u00020!H\u0002J\b\u0010\"\u001a\u00020!H\u0002J\b\u0010#\u001a\u00020!H\u0002J\b\u0010$\u001a\u00020!H\u0002J\b\u0010%\u001a\u00020!H\u0002J\b\u0010&\u001a\u00020!H\u0002J\b\u0010\'\u001a\u00020!H\u0002J\b\u0010(\u001a\u00020!H\u0002J\u0018\u0010)\u001a\u00020\u001f2\u0006\u0010*\u001a\u00020\u001f2\u0006\u0010+\u001a\u00020\u001fH\u0003J(\u0010,\u001a\u00020!2\u0016\u0010-\u001a\u0012\u0012\u0004\u0012\u00020.0\u001bj\b\u0012\u0004\u0012\u00020.`\u001d2\u0006\u0010/\u001a\u00020\u0010H\u0002J(\u00100\u001a\u00020!2\u0016\u00101\u001a\u0012\u0012\u0004\u0012\u0002020\u001bj\b\u0012\u0004\u0012\u000202`\u001d2\u0006\u00103\u001a\u00020\u0010H\u0002J(\u00104\u001a\u00020!2\u0016\u0010-\u001a\u0012\u0012\u0004\u0012\u0002050\u001bj\b\u0012\u0004\u0012\u000205`\u001d2\u0006\u0010/\u001a\u00020\u0010H\u0002J(\u00106\u001a\u00020!2\u0016\u00107\u001a\u0012\u0012\u0004\u0012\u0002080\u001bj\b\u0012\u0004\u0012\u000208`\u001d2\u0006\u0010/\u001a\u00020\u0010H\u0002J \u00109\u001a\u0012\u0012\u0004\u0012\u00020:0\u001bj\b\u0012\u0004\u0012\u00020:`\u001d2\u0006\u0010;\u001a\u00020\u001cH\u0002J\u0010\u0010<\u001a\u00020=2\u0006\u0010>\u001a\u00020:H\u0002J\u0014\u0010?\u001a\u0004\u0018\u00010@2\b\u0010A\u001a\u0004\u0018\u00010BH\u0016J\b\u0010C\u001a\u00020!H\u0016J\"\u0010D\u001a\u00020\u00102\b\u0010E\u001a\u0004\u0018\u00010B2\u0006\u0010F\u001a\u00020\u00102\u0006\u0010G\u001a\u00020\u0010H\u0016J\b\u0010H\u001a\u00020!H\u0002J \u0010I\u001a\u00020!2\u0016\u0010J\u001a\u0012\u0012\u0004\u0012\u00020K0\u001bj\b\u0012\u0004\u0012\u00020K`\u001dH\u0002J\b\u0010L\u001a\u00020!H\u0002J\b\u0010M\u001a\u00020!H\u0002J\u0016\u0010N\u001a\u00020!2\f\u0010O\u001a\b\u0012\u0004\u0012\u00020:0PH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\u001c0\u001bj\b\u0012\u0004\u0012\u00020\u001c`\u001dX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006Q"}, d2 = {"Lcom/pngelectoral/datasync/DownloadNewVotersSyncService;", "Landroid/app/Service;", "()V", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "geoListData", "Lcom/pngelectoral/ui/model/GeoListData;", "getGeoListData", "()Lcom/pngelectoral/ui/model/GeoListData;", "setGeoListData", "(Lcom/pngelectoral/ui/model/GeoListData;)V", "locationPageNumber", "", "provinceFileCount", "securedSharedPreferenceWrapper", "Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;", "getSecuredSharedPreferenceWrapper", "()Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;", "setSecuredSharedPreferenceWrapper", "(Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;)V", "stopServiceReceiver", "Landroid/content/BroadcastReceiver;", "voterDataFiles", "Ljava/util/ArrayList;", "Ljava/io/File;", "Lkotlin/collections/ArrayList;", "zipFilePath", "", "allVotersDownloaded", "", "callGeoClanListApi", "callGetGeoListAPI", "callGetOccupationListAPI", "callLocationsAPI", "callMKAClanListApi", "callMKAGeoListApi", "copyVotersData", "createNotificationChannel", "channelId", "channelName", "downloadGeoClanList", "clanList", "Lcom/pngelectoral/ui/model/ClanListModel$ClanData;", "totalCount", "downloadLocationsList", "locationsList", "Lcom/pngelectoral/ui/model/LocationList;", "totalLocationsCount", "downloadMKAClanList", "Lcom/pngelectoral/ui/model/MKAClanListModel$ClanData;", "downloadMKAGeoList", "geoList", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData;", "getDownloadedTransferInVotersList", "Lcom/pngelectoral/ui/model/NewVotersList;", "file", "getVoterToInsertFromFile", "Lcom/pngelectoral/ui/model/VotersList;", "newVoter", "onBind", "Landroid/os/IBinder;", "p0", "Landroid/content/Intent;", "onDestroy", "onStartCommand", "intent", "flags", "startId", "saveGeoDataList", "saveOccupationList", "occupationList", "Lcom/pngelectoral/ui/model/OccupationList;", "startNotificationWithNotification", "stopNotification", "storeDataAndFetchFromAnotherFile", "result", "", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DownloadNewVotersSyncService extends android.app.Service {
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int locationPageNumber = 1;
    public com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper securedSharedPreferenceWrapper;
    private int provinceFileCount = 0;
    private java.lang.String zipFilePath = "";
    private java.util.ArrayList<java.io.File> voterDataFiles;
    private final android.content.BroadcastReceiver stopServiceReceiver = null;
    public com.pngelectoral.ui.model.GeoListData geoListData;
    
    public DownloadNewVotersSyncService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper getSecuredSharedPreferenceWrapper() {
        return null;
    }
    
    public final void setSecuredSharedPreferenceWrapper(@org.jetbrains.annotations.NotNull
    com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper p0) {
    }
    
    @org.jetbrains.annotations.Nullable
    @java.lang.Override
    public android.os.IBinder onBind(@org.jetbrains.annotations.Nullable
    android.content.Intent p0) {
        return null;
    }
    
    @java.lang.Override
    public int onStartCommand(@org.jetbrains.annotations.Nullable
    android.content.Intent intent, int flags, int startId) {
        return 0;
    }
    
    /**
     * Method to call api for mkageolist.
     */
    private final void callMKAGeoListApi() {
    }
    
    /**
     * Method to add data into database table mkageolist.
     */
    private final void downloadMKAGeoList(java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData> geoList, int totalCount) {
    }
    
    /**
     * Method to call api for mkaclanlist.
     */
    private final void callMKAClanListApi() {
    }
    
    /**
     * Method to add data into database table mkaclanlist
     */
    private final void downloadMKAClanList(java.util.ArrayList<com.pngelectoral.ui.model.MKAClanListModel.ClanData> clanList, int totalCount) {
    }
    
    /**
     * Method to call api for geoclanlist.
     */
    private final void callGeoClanListApi() {
    }
    
    /**
     * Method to add data into database table clanlist
     */
    private final void downloadGeoClanList(java.util.ArrayList<com.pngelectoral.ui.model.ClanListModel.ClanData> clanList, int totalCount) {
    }
    
    private final void copyVotersData() {
    }
    
    private final void allVotersDownloaded() {
    }
    
    private final void storeDataAndFetchFromAnotherFile(java.util.List<? extends com.pngelectoral.ui.model.NewVotersList> result) {
    }
    
    private final com.pngelectoral.ui.model.VotersList getVoterToInsertFromFile(com.pngelectoral.ui.model.NewVotersList newVoter) {
        return null;
    }
    
    private final java.util.ArrayList<com.pngelectoral.ui.model.NewVotersList> getDownloadedTransferInVotersList(java.io.File file) {
        return null;
    }
    
    private final void callLocationsAPI() {
    }
    
    private final void downloadLocationsList(java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationsList, int totalLocationsCount) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.GeoListData getGeoListData() {
        return null;
    }
    
    public final void setGeoListData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.GeoListData p0) {
    }
    
    private final void callGetGeoListAPI() {
    }
    
    private final void saveGeoDataList() {
    }
    
    private final void callGetOccupationListAPI() {
    }
    
    private final void saveOccupationList(java.util.ArrayList<com.pngelectoral.ui.model.OccupationList> occupationList) {
    }
    
    private final void startNotificationWithNotification() {
    }
    
    @androidx.annotation.RequiresApi(value = android.os.Build.VERSION_CODES.O)
    private final java.lang.String createNotificationChannel(java.lang.String channelId, java.lang.String channelName) {
        return null;
    }
    
    private final void stopNotification() {
    }
    
    @java.lang.Override
    public void onDestroy() {
    }
}