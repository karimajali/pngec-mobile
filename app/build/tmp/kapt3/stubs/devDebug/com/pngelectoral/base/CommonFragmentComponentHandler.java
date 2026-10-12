package com.pngelectoral.base;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J\b\u0010\u0006\u001a\u00020\u0007H&J\u000f\u0010\b\u001a\u0004\u0018\u00010\tH&\u00a2\u0006\u0002\u0010\nR\u0012\u0010\u0002\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u000b"}, d2 = {"Lcom/pngelectoral/base/CommonFragmentComponentHandler;", "", "layoutId", "", "getLayoutId", "()I", "init", "", "onBackPressed", "", "()Ljava/lang/Boolean;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public abstract interface CommonFragmentComponentHandler {
    
    public abstract int getLayoutId();
    
    @org.jetbrains.annotations.Nullable
    public abstract java.lang.Boolean onBackPressed();
    
    public abstract void init();
}