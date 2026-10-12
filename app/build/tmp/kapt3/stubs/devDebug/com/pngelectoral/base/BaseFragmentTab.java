package com.pngelectoral.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0004\b&\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u000f\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0016\u00a2\u0006\u0002\u0010\rJ\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\u000fJ1\u0010\u0011\u001a\u00020\u000f2\n\u0010\u0012\u001a\u0006\u0012\u0002\b\u00030\u00002\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\f2\b\u0010\u0016\u001a\u0004\u0018\u00010\f\u00a2\u0006\u0002\u0010\u0017R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n\u00a8\u0006\u0018"}, d2 = {"Lcom/pngelectoral/base/BaseFragmentTab;", "T", "Landroidx/databinding/ViewDataBinding;", "Lcom/pngelectoral/base/BaseFragment;", "()V", "mFragmentManager", "Landroidx/fragment/app/FragmentManager;", "getMFragmentManager", "()Landroidx/fragment/app/FragmentManager;", "setMFragmentManager", "(Landroidx/fragment/app/FragmentManager;)V", "onBackPressed", "", "()Ljava/lang/Boolean;", "popTab", "", "popUpToRoot", "pushInsideTab", "fragment", "containerId", "", "addBackStack", "animation", "(Lcom/pngelectoral/base/BaseFragmentTab;IZLjava/lang/Boolean;)V", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public abstract class BaseFragmentTab<T extends androidx.databinding.ViewDataBinding> extends com.pngelectoral.base.BaseFragment<T> {
    @org.jetbrains.annotations.Nullable
    private androidx.fragment.app.FragmentManager mFragmentManager;
    private java.util.HashMap _$_findViewCache;
    
    public BaseFragmentTab() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable
    public final androidx.fragment.app.FragmentManager getMFragmentManager() {
        return null;
    }
    
    public final void setMFragmentManager(@org.jetbrains.annotations.Nullable
    androidx.fragment.app.FragmentManager p0) {
    }
    
    public final void pushInsideTab(@org.jetbrains.annotations.NotNull
    com.pngelectoral.base.BaseFragmentTab<?> fragment, int containerId, boolean addBackStack, @org.jetbrains.annotations.Nullable
    java.lang.Boolean animation) {
    }
    
    public final void popTab() {
    }
    
    @org.jetbrains.annotations.Nullable
    @java.lang.Override
    public java.lang.Boolean onBackPressed() {
        return null;
    }
    
    public final void popUpToRoot() {
    }
}