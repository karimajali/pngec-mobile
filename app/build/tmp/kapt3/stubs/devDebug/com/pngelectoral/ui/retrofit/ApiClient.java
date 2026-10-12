package com.pngelectoral.ui.retrofit;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u0004\u0018\u00010\u0006J\b\u0010\n\u001a\u0004\u0018\u00010\u0006J\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/pngelectoral/ui/retrofit/ApiClient;", "", "()V", "BASE_URL", "", "mLocationsRetrofitInterface", "Lcom/pngelectoral/ui/retrofit/RetrofitInterface;", "mRetrofitInterface", "mVotersRetrofitInterface", "getLocationsRetrofitInterface", "getRetrofitInterface", "getVotersRetrofitInterface", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.ui.retrofit.ApiClient INSTANCE = null;
    private static com.pngelectoral.ui.retrofit.RetrofitInterface mRetrofitInterface;
    private static com.pngelectoral.ui.retrofit.RetrofitInterface mLocationsRetrofitInterface;
    private static com.pngelectoral.ui.retrofit.RetrofitInterface mVotersRetrofitInterface;
    private static final java.lang.String BASE_URL = "http://192.168.1.55:57712/api/mobile/";
    
    private ApiClient() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.pngelectoral.ui.retrofit.RetrofitInterface getRetrofitInterface() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.pngelectoral.ui.retrofit.RetrofitInterface getLocationsRetrofitInterface() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.pngelectoral.ui.retrofit.RetrofitInterface getVotersRetrofitInterface() {
        return null;
    }
}