package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u00013B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u001d\u001a\u00020\u001eH\u0002J\b\u0010\u001f\u001a\u00020\u001eH\u0002J\u0010\u0010 \u001a\u00020\u001e2\u0006\u0010!\u001a\u00020\fH\u0002J\u0014\u0010\"\u001a\u0004\u0018\u00010#2\b\u0010$\u001a\u0004\u0018\u00010#H\u0002J\u0012\u0010%\u001a\u00020#2\b\u0010&\u001a\u0004\u0018\u00010\u0017H\u0002J\u0012\u0010\'\u001a\u00020\u001e2\b\u0010&\u001a\u0004\u0018\u00010\u0017H\u0002J\"\u0010(\u001a\u00020\u001e2\u0006\u0010)\u001a\u00020\u00142\u0006\u0010*\u001a\u00020\u00142\b\u0010+\u001a\u0004\u0018\u00010,H\u0014J\u0012\u0010-\u001a\u00020\u001e2\b\u0010.\u001a\u0004\u0018\u00010/H\u0014J\b\u00100\u001a\u00020\u001eH\u0002J\b\u00101\u001a\u00020\u001eH\u0002J\b\u00102\u001a\u00020\u001eH\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u00020\u0006X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u00020\u000eX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00170\u0016X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010\u0018\u001a\u0010\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u001a\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u00064"}, d2 = {"Lcom/pngelectoral/ui/activity/RejectedVoterListActivity;", "Lcom/pngelectoral/base/BaseActivity;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityRejectedVoterListBinding;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "hasNext", "", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "pageNumber", "", "rejectedVotersList", "", "Lcom/pngelectoral/ui/model/VotersList;", "rejectedVotersListAdapter", "Lcom/pngelectoral/ui/adapter/GenericAdapter;", "Lcom/pngelectoral/databinding/ItemWardRollVoterBinding;", "rejectedVotersListViewModel", "Lcom/pngelectoral/ui/viewModel/RejectedVotersListViewModel;", "addObservers", "", "addRvRejectedVotersListOnScrollListener", "callRejectedVotersListApi", "isShowLoading", "getPostedDate", "", "postedDate", "getVoterName", "model", "navigateToVoterDetailsScreen", "onActivityResult", "requestCode", "resultCode", "data", "Landroid/content/Intent;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "setFooterData", "setOnClickListeners", "setRejectedVotersListAdapter", "GetFilterListAsync", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class RejectedVoterListActivity extends com.pngelectoral.base.BaseActivity {
    private com.pngelectoral.databinding.ActivityRejectedVoterListBinding binding;
    private com.pngelectoral.ui.viewModel.RejectedVotersListViewModel rejectedVotersListViewModel;
    private com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VotersList, com.pngelectoral.databinding.ItemWardRollVoterBinding> rejectedVotersListAdapter;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private java.util.List<com.pngelectoral.ui.model.VotersList> rejectedVotersList;
    private int pageNumber = 1;
    private boolean hasNext = false;
    private java.util.HashMap _$_findViewCache;
    
    public RejectedVoterListActivity() {
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
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setFooterData() {
    }
    
    private final void setOnClickListeners() {
    }
    
    private final void addRvRejectedVotersListOnScrollListener() {
    }
    
    private final void addObservers() {
    }
    
    private final void callRejectedVotersListApi(boolean isShowLoading) {
    }
    
    private final void setRejectedVotersListAdapter() {
    }
    
    private final java.lang.String getVoterName(com.pngelectoral.ui.model.VotersList model) {
        return null;
    }
    
    private final java.lang.String getPostedDate(java.lang.String postedDate) {
        return null;
    }
    
    private final void navigateToVoterDetailsScreen(com.pngelectoral.ui.model.VotersList model) {
    }
    
    @java.lang.Override
    protected void onActivityResult(int requestCode, int resultCode, @org.jetbrains.annotations.Nullable
    android.content.Intent data) {
    }
    
    @android.annotation.SuppressLint(value = {"StaticFieldLeak"})
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0081\u0004\u0018\u00002 \u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0001B#\u0012\u001c\b\u0002\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0007\u00a2\u0006\u0002\u0010\bJ-\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0016\u0010\n\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\u000b\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\fJ\u0018\u0010\r\u001a\u00020\u000e2\u000e\u0010\u000f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u0014R\"\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0006j\n\u0012\u0004\u0012\u00020\u0004\u0018\u0001`\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lcom/pngelectoral/ui/activity/RejectedVoterListActivity$GetFilterListAsync;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "", "Lcom/pngelectoral/ui/model/VotersList;", "votersList", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "(Lcom/pngelectoral/ui/activity/RejectedVoterListActivity;Ljava/util/ArrayList;)V", "doInBackground", "p0", "", "([Ljava/lang/Void;)Ljava/util/List;", "onPostExecute", "", "result", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public final class GetFilterListAsync extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.util.List<? extends com.pngelectoral.ui.model.VotersList>> {
        private final java.util.ArrayList<com.pngelectoral.ui.model.VotersList> votersList = null;
        
        public GetFilterListAsync(@org.jetbrains.annotations.Nullable
        java.util.ArrayList<com.pngelectoral.ui.model.VotersList> votersList) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        @java.lang.Override
        protected java.util.List<com.pngelectoral.ui.model.VotersList> doInBackground(@org.jetbrains.annotations.NotNull
        java.lang.Void... p0) {
            return null;
        }
        
        @java.lang.Override
        protected void onPostExecute(@org.jetbrains.annotations.Nullable
        java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
        }
    }
}