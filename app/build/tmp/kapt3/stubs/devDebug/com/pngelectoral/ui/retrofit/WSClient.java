package com.pngelectoral.ui.retrofit;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u0010*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001\u0010B\u0005\u00a2\u0006\u0002\u0010\u0003J@\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u000e\u0010\n\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u000b2\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f\u00a8\u0006\u0011"}, d2 = {"Lcom/pngelectoral/ui/retrofit/WSClient;", "T", "", "()V", "request", "", "mContext", "Landroid/content/Context;", "requestId", "", "call", "Lretrofit2/Call;", "mSuccessHandler", "Lcom/pngelectoral/ui/retrofit/ISuccessHandler;", "mFailureHandler", "Lcom/pngelectoral/ui/retrofit/IFailureHandler;", "Companion", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class WSClient<T extends java.lang.Object> {
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.ui.retrofit.WSClient.Companion Companion = null;
    @org.jetbrains.annotations.Nullable
    private static java.util.HashMap<?, ?> requestQue;
    
    public WSClient() {
        super();
    }
    
    public final void request(@org.jetbrains.annotations.NotNull
    android.content.Context mContext, int requestId, @org.jetbrains.annotations.Nullable
    retrofit2.Call<T> call, @org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.retrofit.ISuccessHandler<T> mSuccessHandler, @org.jetbrains.annotations.Nullable
    com.pngelectoral.ui.retrofit.IFailureHandler mFailureHandler) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R$\u0010\u0003\u001a\f\u0012\u0002\b\u0003\u0012\u0002\b\u0003\u0018\u00010\u0004X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/pngelectoral/ui/retrofit/WSClient$Companion;", "", "()V", "requestQue", "Ljava/util/HashMap;", "getRequestQue", "()Ljava/util/HashMap;", "setRequestQue", "(Ljava/util/HashMap;)V", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        public final java.util.HashMap<?, ?> getRequestQue() {
            return null;
        }
        
        public final void setRequestQue(@org.jetbrains.annotations.Nullable
        java.util.HashMap<?, ?> p0) {
        }
    }
}