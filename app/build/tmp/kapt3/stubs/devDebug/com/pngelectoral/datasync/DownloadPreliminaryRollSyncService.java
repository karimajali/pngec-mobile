package com.pngelectoral.datasync;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001:\u0001,B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001b\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010 \u001a\u00020\u001eH\u0003J\u0014\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\u001cH\u0016J\"\u0010&\u001a\u00020\n2\b\u0010\'\u001a\u0004\u0018\u00010$2\u0006\u0010(\u001a\u00020\n2\u0006\u0010)\u001a\u00020\nH\u0016J\b\u0010*\u001a\u00020\u001cH\u0002J\b\u0010+\u001a\u00020\u001cH\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u000b\u001a\u00020\fX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006-"}, d2 = {"Lcom/pngelectoral/datasync/DownloadPreliminaryRollSyncService;", "Landroid/app/Service;", "()V", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "downloadedPreliminaryRollCount", "", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "pageNumber", "securedSharedPreferenceWrapper", "Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;", "getSecuredSharedPreferenceWrapper", "()Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;", "setSecuredSharedPreferenceWrapper", "(Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;)V", "stopServiceReceiver", "Landroid/content/BroadcastReceiver;", "voterPageSize", "callDownloadVotersAPI", "", "createNotificationChannel", "", "channelId", "channelName", "onBind", "Landroid/os/IBinder;", "p0", "Landroid/content/Intent;", "onDestroy", "onStartCommand", "intent", "flags", "startId", "startNotificationWithNotification", "stopNotification", "DownloadVotersListAsync", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DownloadPreliminaryRollSyncService extends android.app.Service {
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int pageNumber = 1;
    private int voterPageSize = 500;
    private int downloadedPreliminaryRollCount = 0;
    public com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper securedSharedPreferenceWrapper;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    private final android.content.BroadcastReceiver stopServiceReceiver = null;
    
    public DownloadPreliminaryRollSyncService() {
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
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.DPOUserData getLoggedInDPOUserData() {
        return null;
    }
    
    public final void setLoggedInDPOUserData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.DPOUserData p0) {
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
    
    private final void callDownloadVotersAPI() {
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
    
    @android.annotation.SuppressLint(value = {"StaticFieldLeak"})
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0081\u0004\u0018\u00002\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00030\u0001B%\u0012\u0016\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007\u0012\u0006\u0010\b\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\tJ%\u0010\u0012\u001a\u00020\u00032\u0016\u0010\u0013\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u0014\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\u0015J\u0017\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u0014\u00a2\u0006\u0002\u0010\u0019R*\u0010\u0004\u001a\u0012\u0012\u0004\u0012\u00020\u00060\u0005j\b\u0012\u0004\u0012\u00020\u0006`\u0007X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\rR\u001a\u0010\b\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001a"}, d2 = {"Lcom/pngelectoral/datasync/DownloadPreliminaryRollSyncService$DownloadVotersListAsync;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "", "downloadedVotersList", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/VotersList;", "Lkotlin/collections/ArrayList;", "totalDownloadVotersCount", "(Lcom/pngelectoral/datasync/DownloadPreliminaryRollSyncService;Ljava/util/ArrayList;I)V", "getDownloadedVotersList", "()Ljava/util/ArrayList;", "setDownloadedVotersList", "(Ljava/util/ArrayList;)V", "getTotalDownloadVotersCount", "()I", "setTotalDownloadVotersCount", "(I)V", "doInBackground", "p0", "", "([Ljava/lang/Void;)Ljava/lang/Integer;", "onPostExecute", "", "result", "(Ljava/lang/Integer;)V", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public final class DownloadVotersListAsync extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Integer> {
        @org.jetbrains.annotations.NotNull
        private java.util.ArrayList<com.pngelectoral.ui.model.VotersList> downloadedVotersList;
        private int totalDownloadVotersCount;
        
        public DownloadVotersListAsync(@org.jetbrains.annotations.NotNull
        java.util.ArrayList<com.pngelectoral.ui.model.VotersList> downloadedVotersList, int totalDownloadVotersCount) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final java.util.ArrayList<com.pngelectoral.ui.model.VotersList> getDownloadedVotersList() {
            return null;
        }
        
        public final void setDownloadedVotersList(@org.jetbrains.annotations.NotNull
        java.util.ArrayList<com.pngelectoral.ui.model.VotersList> p0) {
        }
        
        public final int getTotalDownloadVotersCount() {
            return 0;
        }
        
        public final void setTotalDownloadVotersCount(int p0) {
        }
        
        @org.jetbrains.annotations.NotNull
        @java.lang.Override
        protected java.lang.Integer doInBackground(@org.jetbrains.annotations.NotNull
        java.lang.Void... p0) {
            return null;
        }
        
        @java.lang.Override
        protected void onPostExecute(@org.jetbrains.annotations.Nullable
        java.lang.Integer result) {
        }
    }
}