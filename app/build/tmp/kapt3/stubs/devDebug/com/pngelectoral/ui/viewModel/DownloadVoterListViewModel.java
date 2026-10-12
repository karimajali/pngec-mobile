package com.pngelectoral.ui.viewModel;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016R\u001a\u0010\u0002\u001a\u00020\u0003X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R,\u0010\n\u001a\u0014\u0012\u0010\u0012\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\r0\f0\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0017"}, d2 = {"Lcom/pngelectoral/ui/viewModel/DownloadVoterListViewModel;", "Landroidx/lifecycle/AndroidViewModel;", "app", "Landroid/app/Application;", "(Landroid/app/Application;)V", "getApp", "()Landroid/app/Application;", "setApp", "downloadVotersListRepository", "Lcom/pngelectoral/ui/repository/DownloadVotersListRepository;", "provinceListResponse", "Landroidx/lifecycle/MutableLiveData;", "Lcom/pngelectoral/ui/retrofit/Response;", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/ProvinceList;", "getProvinceListResponse", "()Landroidx/lifecycle/MutableLiveData;", "setProvinceListResponse", "(Landroidx/lifecycle/MutableLiveData;)V", "callGetActiveProvinceListAPI", "", "mContext", "Landroid/content/Context;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DownloadVoterListViewModel extends androidx.lifecycle.AndroidViewModel {
    @org.jetbrains.annotations.NotNull
    private android.app.Application app;
    private final com.pngelectoral.ui.repository.DownloadVotersListRepository downloadVotersListRepository = null;
    @org.jetbrains.annotations.NotNull
    private androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList>>> provinceListResponse;
    
    public DownloadVoterListViewModel(@org.jetbrains.annotations.NotNull
    android.app.Application app) {
        super(null);
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.app.Application getApp() {
        return null;
    }
    
    public final void setApp(@org.jetbrains.annotations.NotNull
    android.app.Application p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList>>> getProvinceListResponse() {
        return null;
    }
    
    public final void setProvinceListResponse(@org.jetbrains.annotations.NotNull
    androidx.lifecycle.MutableLiveData<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList>>> p0) {
    }
    
    public final void callGetActiveProvinceListAPI(@org.jetbrains.annotations.NotNull
    android.content.Context mContext) {
    }
}