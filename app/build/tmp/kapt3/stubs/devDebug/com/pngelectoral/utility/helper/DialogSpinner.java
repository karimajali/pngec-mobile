package com.pngelectoral.utility.helper;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 %*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0001%B%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0016\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0006j\b\u0012\u0004\u0012\u00028\u0000`\u0007\u00a2\u0006\u0002\u0010\bJ\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u0000J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0017\u001a\u00020\u0012J/\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002!\u0010 \u001a\u001d\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u00180\u0014J/\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002!\u0010 \u001a\u001d\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u001c0\u0014J\u000e\u0010\"\u001a\u00020\u001c2\u0006\u0010#\u001a\u00020$R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u001c\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u000e\u00a2\u0006\u0002\n\u0000R)\u0010\u0013\u001a\u001d\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u00180\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000R!\u0010\u0005\u001a\u0012\u0012\u0004\u0012\u00028\u00000\u0006j\b\u0012\u0004\u0012\u00028\u0000`\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR)\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00118\u0000\u00a2\u0006\f\b\u0015\u0012\b\b\u0016\u0012\u0004\b\b(\u0017\u0012\u0004\u0012\u00020\u001c0\u0014X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/pngelectoral/utility/helper/DialogSpinner;", "M", "", "activity", "Landroid/app/Activity;", "list", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "(Landroid/app/Activity;Ljava/util/ArrayList;)V", "getActivity", "()Landroid/app/Activity;", "dialogFragment", "Landroidx/fragment/app/DialogFragment;", "getDialogFragment", "()Landroidx/fragment/app/DialogFragment;", "setDialogFragment", "(Landroidx/fragment/app/DialogFragment;)V", "enableSearch", "", "getValueCallback", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "value", "", "getList", "()Ljava/util/ArrayList;", "onValueSelected", "", "build", "setEnableSearch", "setMap", "callback", "setOnValueSelectedCallback", "show", "fragmentManager", "Landroidx/fragment/app/FragmentManager;", "Companion", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class DialogSpinner<M extends java.lang.Object> {
    @org.jetbrains.annotations.NotNull
    private final android.app.Activity activity = null;
    @org.jetbrains.annotations.NotNull
    private final java.util.ArrayList<M> list = null;
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.utility.helper.DialogSpinner.Companion Companion = null;
    private kotlin.jvm.functions.Function1<? super M, java.lang.String> getValueCallback;
    private boolean enableSearch = true;
    private kotlin.jvm.functions.Function1<? super M, kotlin.Unit> onValueSelected;
    @org.jetbrains.annotations.Nullable
    private androidx.fragment.app.DialogFragment dialogFragment;
    
    public DialogSpinner(@org.jetbrains.annotations.NotNull
    android.app.Activity activity, @org.jetbrains.annotations.NotNull
    java.util.ArrayList<M> list) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final android.app.Activity getActivity() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.ArrayList<M> getList() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.utility.helper.DialogSpinner<M> setMap(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super M, java.lang.String> callback) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.utility.helper.DialogSpinner<M> setEnableSearch(boolean value) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.utility.helper.DialogSpinner<M> setOnValueSelectedCallback(@org.jetbrains.annotations.NotNull
    kotlin.jvm.functions.Function1<? super M, kotlin.Unit> callback) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final androidx.fragment.app.DialogFragment getDialogFragment() {
        return null;
    }
    
    public final void setDialogFragment(@org.jetbrains.annotations.Nullable
    androidx.fragment.app.DialogFragment p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.utility.helper.DialogSpinner<M> build() {
        return null;
    }
    
    public final void show(@org.jetbrains.annotations.NotNull
    androidx.fragment.app.FragmentManager fragmentManager) {
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J2\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00050\u0004\"\u0004\b\u0001\u0010\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0016\u0010\b\u001a\u0012\u0012\u0004\u0012\u0002H\u00050\tj\b\u0012\u0004\u0012\u0002H\u0005`\n\u00a8\u0006\u000b"}, d2 = {"Lcom/pngelectoral/utility/helper/DialogSpinner$Companion;", "", "()V", "with", "Lcom/pngelectoral/utility/helper/DialogSpinner;", "M", "activity", "Landroid/app/Activity;", "list", "Ljava/util/ArrayList;", "Lkotlin/collections/ArrayList;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull
        public final <M extends java.lang.Object>com.pngelectoral.utility.helper.DialogSpinner<M> with(@org.jetbrains.annotations.NotNull
        android.app.Activity activity, @org.jetbrains.annotations.NotNull
        java.util.ArrayList<M> list) {
            return null;
        }
    }
}