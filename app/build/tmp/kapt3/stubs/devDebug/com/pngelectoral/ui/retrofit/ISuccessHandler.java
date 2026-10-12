package com.pngelectoral.ui.retrofit;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\bf\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u001d\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00028\u0000H&\u00a2\u0006\u0002\u0010\b\u00a8\u0006\t"}, d2 = {"Lcom/pngelectoral/ui/retrofit/ISuccessHandler;", "T", "", "successResponse", "", "requestCode", "", "mResponse", "(ILjava/lang/Object;)V", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public abstract interface ISuccessHandler<T extends java.lang.Object> {
    
    public abstract void successResponse(int requestCode, T mResponse);
}