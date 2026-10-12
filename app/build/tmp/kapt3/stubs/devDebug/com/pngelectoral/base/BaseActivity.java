package com.pngelectoral.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0005\b&\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\u0012\u0010\u0012\u001a\u00020\u00112\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0014J\b\u0010\u0015\u001a\u00020\u0011H\u0014J\b\u0010\u0016\u001a\u00020\u0011H\u0014J\b\u0010\u0017\u001a\u00020\u0011H\u0014J\b\u0010\u0018\u001a\u00020\u0011H\u0014J\b\u0010\u0019\u001a\u00020\u0011H\u0014J\u0006\u0010\u001a\u001a\u00020\u0011J\u0006\u0010\u001b\u001a\u00020\u0011J\u000e\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u001eJ1\u0010\u001f\u001a\u00020\u00112\n\u0010 \u001a\u0006\u0012\u0002\b\u00030!2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010%\u00a2\u0006\u0002\u0010\'J\u0014\u0010(\u001a\u00020\u00112\n\u0010)\u001a\u0006\u0012\u0002\b\u00030\u0005H\u0016R \u0010\u0004\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006*"}, d2 = {"Lcom/pngelectoral/base/BaseActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "Lcom/pngelectoral/base/CommonActivityComponentHandler;", "()V", "currentRunningFragment", "Lcom/pngelectoral/base/BaseFragment;", "getCurrentRunningFragment", "()Lcom/pngelectoral/base/BaseFragment;", "setCurrentRunningFragment", "(Lcom/pngelectoral/base/BaseFragment;)V", "securedSharedPreferenceWrapper", "Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;", "getSecuredSharedPreferenceWrapper", "()Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;", "setSecuredSharedPreferenceWrapper", "(Lcom/pngelectoral/utility/sharedPreference/SecuredSharedPreferenceWrapper;)V", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onPause", "onRestart", "onResume", "onStart", "onStop", "pop", "popAll", "popUpTo", "upToName", "", "push", "fragment", "Lcom/pngelectoral/base/BaseFragmentTab;", "containerId", "", "needToAddBackStack", "", "needAnimation", "(Lcom/pngelectoral/base/BaseFragmentTab;IZLjava/lang/Boolean;)V", "setCurrentFragment", "mCurrentFragment", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public abstract class BaseActivity extends androidx.appcompat.app.AppCompatActivity implements com.pngelectoral.base.CommonActivityComponentHandler {
    public com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper securedSharedPreferenceWrapper;
    @org.jetbrains.annotations.Nullable
    private com.pngelectoral.base.BaseFragment<?> currentRunningFragment;
    private java.util.HashMap _$_findViewCache;
    
    public BaseActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper getSecuredSharedPreferenceWrapper() {
        return null;
    }
    
    public final void setSecuredSharedPreferenceWrapper(@org.jetbrains.annotations.NotNull
    com.pngelectoral.utility.sharedPreference.SecuredSharedPreferenceWrapper p0) {
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override
    protected void onStart() {
    }
    
    @java.lang.Override
    protected void onResume() {
    }
    
    @java.lang.Override
    protected void onPause() {
    }
    
    @java.lang.Override
    protected void onStop() {
    }
    
    @java.lang.Override
    protected void onRestart() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final com.pngelectoral.base.BaseFragment<?> getCurrentRunningFragment() {
        return null;
    }
    
    public final void setCurrentRunningFragment(@org.jetbrains.annotations.Nullable
    com.pngelectoral.base.BaseFragment<?> p0) {
    }
    
    @java.lang.Override
    public void setCurrentFragment(@org.jetbrains.annotations.NotNull
    com.pngelectoral.base.BaseFragment<?> mCurrentFragment) {
    }
    
    @java.lang.Override
    public void onBackPressed() {
    }
    
    public final void push(@org.jetbrains.annotations.NotNull
    com.pngelectoral.base.BaseFragmentTab<?> fragment, int containerId, boolean needToAddBackStack, @org.jetbrains.annotations.Nullable
    java.lang.Boolean needAnimation) {
    }
    
    public final void pop() {
    }
    
    public final void popUpTo(@org.jetbrains.annotations.NotNull
    java.lang.String upToName) {
    }
    
    public final void popAll() {
    }
}