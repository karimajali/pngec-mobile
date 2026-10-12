package com.pngelectoral.utility;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2 = {"Lcom/pngelectoral/utility/ShareUtils;", "", "()V", "Companion", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class ShareUtils {
    @org.jetbrains.annotations.NotNull
    public static final com.pngelectoral.utility.ShareUtils.Companion Companion = null;
    
    public ShareUtils() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0014\u0010\u0007\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\t\u0012\u0004\u0012\u00020\u00040\bJ\u0018\u0010\n\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\t\u00a8\u0006\f"}, d2 = {"Lcom/pngelectoral/utility/ShareUtils$Companion;", "", "()V", "extractDatabaseAndShare", "", "application", "Landroid/app/Application;", "onFile", "Lkotlin/Function1;", "Ljava/io/File;", "shareFile", "exportDatabaseFile", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        public final void extractDatabaseAndShare(@org.jetbrains.annotations.NotNull
        android.app.Application application, @org.jetbrains.annotations.NotNull
        kotlin.jvm.functions.Function1<? super java.io.File, kotlin.Unit> onFile) {
        }
        
        public final void shareFile(@org.jetbrains.annotations.NotNull
        android.app.Application application, @org.jetbrains.annotations.Nullable
        java.io.File exportDatabaseFile) {
        }
    }
}