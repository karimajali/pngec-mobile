package com.pngelectoral.ui.retrofit;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00b4\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b \n\u0002\u0010\u0006\n\u0002\b+\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\'J\u0018\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0001\u0010\r\u001a\u00020\u0006H\'J\u0018\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0001\u0010\r\u001a\u00020\u0006H\'J\u0018\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u00032\b\b\u0001\u0010\r\u001a\u00020\u0006H\'J.\u0010\u0010\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u00130\u0012j\b\u0012\u0004\u0012\u00020\u0013`\u00140\u00110\u00032\b\b\u0001\u0010\u0015\u001a\u00020\u0016H\'J\u001e\u0010\u0017\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00110\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u000e\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001a0\u0003H\'J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001c0\u0003H\'J$\u0010\u001d\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020\u001e0\u0012j\b\u0012\u0004\u0012\u00020\u001e`\u00140\u00110\u0003H\'J\u0018\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020 0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u000e\u0010!\u001a\b\u0012\u0004\u0012\u00020\"0\u0003H\'J.\u0010#\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020$0\u0012j\b\u0012\u0004\u0012\u00020$`\u00140\u00110\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\'J$\u0010%\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u00020&0\u0012j\b\u0012\u0004\u0012\u00020&`\u00140\u00110\u0003H\'J\u0018\u0010\'\u001a\b\u0012\u0004\u0012\u00020(0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u0018\u0010)\u001a\b\u0012\u0004\u0012\u00020*0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u0018\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u001e\u0010-\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00110\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u000e\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\u0003H\'J\u000e\u00100\u001a\b\u0012\u0004\u0012\u0002010\u0003H\'J\u0018\u00102\u001a\b\u0012\u0004\u0012\u00020(0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J.\u00103\u001a\u001e\u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u00110\u00032\b\b\u0001\u0010\t\u001a\u00020\nH\'J\u0018\u00105\u001a\b\u0012\u0004\u0012\u00020(0\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u001e\u00106\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00110\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u001e\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00010\u00110\u00032\b\b\u0001\u0010\u0018\u001a\u00020\u0006H\'J\u00d8\u0005\u00108\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010x\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0080\u0001J\u00cf\u0005\u0010\u0081\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0082\u0001J\u00cf\u0005\u0010\u0083\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010x\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0082\u0001J\u00c5\u0005\u0010\u0084\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0085\u0001J\u00e4\u0005\u0010\u0086\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\t\b\u0001\u0010\u0087\u0001\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010x\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0088\u0001J\u00da\u0005\u0010\u0089\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\t\b\u0001\u0010\u0087\u0001\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u008a\u0001J\u00d2\u0006\u0010\u008b\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\t\b\u0001\u0010\u0087\u0001\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010x\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\t\b\u0001\u0010\u008c\u0001\u001a\u00020:2\t\b\u0001\u0010\u008d\u0001\u001a\u00020:2\t\b\u0001\u0010\u008e\u0001\u001a\u00020:2\t\b\u0001\u0010\u008f\u0001\u001a\u00020:2\t\b\u0001\u0010\u0090\u0001\u001a\u00020:2\t\b\u0001\u0010\u0091\u0001\u001a\u00020:2\t\b\u0001\u0010\u0092\u0001\u001a\u00020:2\t\b\u0001\u0010\u0093\u0001\u001a\u00020:2\t\b\u0001\u0010\u0094\u0001\u001a\u00020:2\t\b\u0001\u0010\u0095\u0001\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0096\u0001J\u00c8\u0006\u0010\u0097\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\t\b\u0001\u0010\u0087\u0001\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\t\b\u0001\u0010\u008c\u0001\u001a\u00020:2\t\b\u0001\u0010\u008d\u0001\u001a\u00020:2\t\b\u0001\u0010\u008e\u0001\u001a\u00020:2\t\b\u0001\u0010\u008f\u0001\u001a\u00020:2\t\b\u0001\u0010\u0090\u0001\u001a\u00020:2\t\b\u0001\u0010\u0091\u0001\u001a\u00020:2\t\b\u0001\u0010\u0092\u0001\u001a\u00020:2\t\b\u0001\u0010\u0093\u0001\u001a\u00020:2\t\b\u0001\u0010\u0094\u0001\u001a\u00020:2\t\b\u0001\u0010\u0095\u0001\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u0098\u0001J\u00c7\u0006\u0010\u0099\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010x\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\t\b\u0001\u0010\u008c\u0001\u001a\u00020:2\t\b\u0001\u0010\u008d\u0001\u001a\u00020:2\t\b\u0001\u0010\u008e\u0001\u001a\u00020:2\t\b\u0001\u0010\u008f\u0001\u001a\u00020:2\t\b\u0001\u0010\u0090\u0001\u001a\u00020:2\t\b\u0001\u0010\u0091\u0001\u001a\u00020:2\t\b\u0001\u0010\u0092\u0001\u001a\u00020:2\t\b\u0001\u0010\u0093\u0001\u001a\u00020:2\t\b\u0001\u0010\u0094\u0001\u001a\u00020:2\t\b\u0001\u0010\u0095\u0001\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u009a\u0001J\u00bd\u0006\u0010\u009b\u0001\u001a \u0012\u001a\u0012\u0018\u0012\u0014\u0012\u0012\u0012\u0004\u0012\u0002040\u0012j\b\u0012\u0004\u0012\u000204`\u00140\u0011\u0018\u00010\u00032\b\b\u0001\u00109\u001a\u00020:2\b\b\u0001\u0010;\u001a\u00020:2\b\b\u0001\u0010<\u001a\u00020:2\b\b\u0001\u0010=\u001a\u00020:2\b\b\u0001\u0010>\u001a\u00020:2\b\b\u0001\u0010?\u001a\u00020:2\b\b\u0001\u0010@\u001a\u00020:2\b\b\u0001\u0010A\u001a\u00020:2\b\b\u0001\u0010B\u001a\u00020:2\b\b\u0001\u0010C\u001a\u00020:2\b\b\u0001\u0010D\u001a\u00020:2\b\b\u0001\u0010E\u001a\u00020:2\b\b\u0001\u0010F\u001a\u00020:2\b\b\u0001\u0010G\u001a\u00020:2\b\b\u0001\u0010H\u001a\u00020:2\b\b\u0001\u0010I\u001a\u00020:2\b\b\u0001\u0010J\u001a\u00020:2\b\b\u0001\u0010K\u001a\u00020:2\b\b\u0001\u0010L\u001a\u00020:2\b\b\u0001\u0010M\u001a\u00020:2\b\b\u0001\u0010N\u001a\u00020:2\b\b\u0001\u0010O\u001a\u00020:2\b\b\u0001\u0010P\u001a\u00020Q2\b\b\u0001\u0010R\u001a\u00020Q2\b\b\u0001\u0010S\u001a\u00020:2\b\b\u0001\u0010T\u001a\u00020:2\b\b\u0001\u0010U\u001a\u00020:2\b\b\u0001\u0010V\u001a\u00020:2\b\b\u0001\u0010W\u001a\u00020:2\b\b\u0001\u0010X\u001a\u00020:2\b\b\u0001\u0010Y\u001a\u00020:2\b\b\u0001\u0010Z\u001a\u00020:2\b\b\u0001\u0010[\u001a\u00020:2\b\b\u0001\u0010\\\u001a\u00020:2\b\b\u0001\u0010]\u001a\u00020:2\b\b\u0001\u0010^\u001a\u00020:2\b\b\u0001\u0010_\u001a\u00020:2\b\b\u0001\u0010`\u001a\u00020:2\b\b\u0001\u0010a\u001a\u00020:2\b\b\u0001\u0010b\u001a\u00020:2\b\b\u0001\u0010c\u001a\u00020:2\b\b\u0001\u0010d\u001a\u00020:2\b\b\u0001\u0010e\u001a\u00020:2\b\b\u0001\u0010f\u001a\u00020:2\b\b\u0001\u0010g\u001a\u00020:2\b\b\u0001\u0010h\u001a\u00020:2\b\b\u0001\u0010i\u001a\u00020:2\b\b\u0001\u0010j\u001a\u00020:2\b\b\u0001\u0010k\u001a\u00020:2\b\b\u0001\u0010l\u001a\u00020:2\b\b\u0001\u0010m\u001a\u00020:2\b\b\u0001\u0010n\u001a\u00020:2\b\b\u0001\u0010o\u001a\u00020:2\b\b\u0001\u0010p\u001a\u00020:2\n\b\u0001\u0010q\u001a\u0004\u0018\u00010r2\n\b\u0001\u0010s\u001a\u0004\u0018\u00010r2\b\b\u0001\u0010t\u001a\u00020:2\b\b\u0001\u0010u\u001a\u00020:2\b\b\u0001\u0010v\u001a\u00020:2\b\b\u0001\u0010w\u001a\u00020:2\b\b\u0001\u0010y\u001a\u00020:2\b\b\u0001\u0010z\u001a\u00020:2\b\b\u0001\u0010{\u001a\u00020:2\b\b\u0001\u0010|\u001a\u00020Q2\b\b\u0001\u0010}\u001a\u00020:2\b\b\u0001\u0010~\u001a\u00020:2\t\b\u0001\u0010\u008c\u0001\u001a\u00020:2\t\b\u0001\u0010\u008d\u0001\u001a\u00020:2\t\b\u0001\u0010\u008e\u0001\u001a\u00020:2\t\b\u0001\u0010\u008f\u0001\u001a\u00020:2\t\b\u0001\u0010\u0090\u0001\u001a\u00020:2\t\b\u0001\u0010\u0091\u0001\u001a\u00020:2\t\b\u0001\u0010\u0092\u0001\u001a\u00020:2\t\b\u0001\u0010\u0093\u0001\u001a\u00020:2\t\b\u0001\u0010\u0094\u0001\u001a\u00020:2\t\b\u0001\u0010\u0095\u0001\u001a\u00020:2\b\b\u0001\u0010\u007f\u001a\u00020:H\'\u00a2\u0006\u0003\u0010\u009c\u0001\u00a8\u0006\u009d\u0001"}, d2 = {"Lcom/pngelectoral/ui/retrofit/RetrofitInterface;", "", "callAllLocationsAPI", "Lretrofit2/Call;", "Lcom/pngelectoral/ui/model/AllLocationsModel;", "downloadLocationsObject", "Lcom/google/gson/JsonObject;", "callCheckSyncedElectorsAPI", "Lcom/pngelectoral/ui/model/SyncedVoterStatusModel;", "syncDataVotersListArray", "Lcom/google/gson/JsonArray;", "callDownloadPreliminaryVotersAPI", "Lcom/pngelectoral/ui/model/DownloadVotersListModel;", "downloadVotersObject", "callDownloadVotersAPI", "callDownloadVotersFileAPI", "callDownloadVotersListAPI", "Lcom/pngelectoral/ui/retrofit/Response;", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/VotersList;", "Lkotlin/collections/ArrayList;", "userID", "", "callForgotPasswordAPI", "inputParam", "callGeneralSettingsAPI", "Lcom/pngelectoral/ui/model/GeneralSettingsModel;", "callGeoClanListAPI", "Lcom/pngelectoral/ui/model/ClanListModel;", "callGetActiveProvinceListAPI", "Lcom/pngelectoral/ui/model/ProvinceList;", "callGetDeviceOperatorReportAPI", "Lcom/pngelectoral/ui/model/DeviceOperatorReportModel;", "callGetGeoListAPI", "Lcom/pngelectoral/ui/model/GeoListModel;", "callGetLocationListAPI", "Lcom/pngelectoral/ui/model/LocationList;", "callGetOccupationListAPI", "Lcom/pngelectoral/ui/model/OccupationList;", "callGetUpdatedVoterListAPI", "Lcom/pngelectoral/ui/model/TransferInVotersModel;", "callGetVoterStatusAndDOBDiscrepancyReportAPI", "Lcom/pngelectoral/ui/model/StatusAndDobDiscrepancyReportModel;", "callLoginAPI", "Lcom/pngelectoral/ui/model/LoginResponseModel;", "callLogoutAPI", "callMKAClanListAPI", "Lcom/pngelectoral/ui/model/MKAClanListModel;", "callMKAGeoListAPI", "Lcom/pngelectoral/ui/model/MKAGeoListModel;", "callRejectedVotersListAPI", "callSyncElectorsAPI", "Lcom/pngelectoral/ui/model/FailedVotersList;", "callTransferInVotersListAPI", "callUpdateGeoStructureAPI", "callValidateEnrolmentFormNumberAPI", "syncElectorsAndImages", "electorID", "Lokhttp3/MultipartBody$Part;", "oldElectorID", "locationId", "oldLocationId", "locationName", "wardId", "wardName", "llgId", "llgName", "districtId", "provinceId", "regionId", "MKADistrictId", "MKALLGId", "MKAWardId", "MKALocationId", "MKAClanId", "lName", "fName", "voterName", "gender", "dob", "isYOB", "", "isPWD", "religion", "fatherLName", "fatherFName", "motherLName", "motherFName", "provOfBirthFK", "provinceOfBirth", "provinceName", "openElectorate", "placeOfBirth", "occupationFK", "occupation", "clan", "subClan", "address", "natID", "natIDRecordNo", "status", "houseFK", "houseSortOrder", "restHouse", "sectionNo", "lotNo", "streetName", "suburbTownSettlement", "familyFK", "familyNumber", "familyName", "familySortOrder", "dpo", "lat", "", "longitude", "posted", "comment", "electorProfileImageUrl", "electorFormImageUrl", "profileImageBody", "formImageBody", "enrolmentFormNumber", "EnrolmentFormType", "IsManualFormNumber", "createdBy", "tempElectorId", "clanId", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncElectorsAndImagesWithOutVoterImage", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncElectorsAndVoterImage", "syncElectorsDataOnly", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncRejectedElectorsAndImages", "isRejected", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncRejectedElectorsAndImagesWithOutVoterImage", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncRejectedTransferInElectorsAndImages", "transferOutRegionId", "transferOutProvinceId", "transferOutDistrictId", "transferOutLLGId", "transferOutWardId", "transferOutLocationId", "TransferOutMKADistrictId", "TransferOutMKALLGId", "TransferOutMKAWardId", "TransferOutMKALocationId", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncRejectedTransferInElectorsAndImagesWithOutProfile", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncTransferInElectorsAndImages", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "syncTransferInElectorsAndImagesWithOutProfile", "(Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Ljava/lang/Double;Ljava/lang/Double;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;ZLokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;Lokhttp3/MultipartBody$Part;)Lretrofit2/Call;", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public abstract interface RetrofitInterface {
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "UserMobile/UserLogin")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.LoginResponseModel> callLoginAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "general/forgotpassword")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> callForgotPasswordAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "Voter/GetVoterList/{id}")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.VotersList>>> callDownloadVotersListAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Path(value = "id")
    java.lang.String userID);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetGeoList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.GeoListModel> callGetGeoListAPI();
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetOccupationList")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.OccupationList>>> callGetOccupationListAPI();
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/GetTransferInVoterList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.TransferInVotersModel> callTransferInVotersListAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/ValidateEnrolmentNumber")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> callValidateEnrolmentFormNumberAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/SyncElectors")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> callSyncElectorsAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonArray syncDataVotersListArray);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/GetVoterList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.DownloadVotersListModel> callDownloadVotersAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject downloadVotersObject);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/DownloadVoterList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.DownloadVotersListModel> callDownloadVotersFileAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject downloadVotersObject);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/GetPreliminaryVoterList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.DownloadVotersListModel> callDownloadPreliminaryVotersAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject downloadVotersObject);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/GetAllLocations")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.AllLocationsModel> callAllLocationsAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject downloadLocationsObject);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/GetLocationsByIds")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.LocationList>>> callGetLocationListAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject downloadLocationsObject);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/GetRejectedVoterList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.TransferInVotersModel> callRejectedVotersListAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/Logout")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> callLogoutAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncElectorsAndImages(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part profileImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncRejectedElectorsAndImages(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @retrofit2.http.Part(value = "isRejected")
    boolean isRejected, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part profileImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncElectorsAndImagesWithOutVoterImage(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncElectorsDataOnly(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncRejectedElectorsAndImagesWithOutVoterImage(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @retrofit2.http.Part(value = "isRejected")
    boolean isRejected, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncTransferInElectorsAndImages(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part profileImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutRegionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutProvinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutDistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncRejectedTransferInElectorsAndImages(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @retrofit2.http.Part(value = "isRejected")
    boolean isRejected, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part profileImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutRegionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutProvinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutDistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncTransferInElectorsAndImagesWithOutProfile(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutRegionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutProvinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutDistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncRejectedTransferInElectorsAndImagesWithOutProfile(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @retrofit2.http.Part(value = "isRejected")
    boolean isRejected, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part formImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutRegionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutProvinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutDistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part transferOutLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part TransferOutMKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.Nullable
    @retrofit2.http.POST(value = "Voter/SyncElectorsAndImages")
    @retrofit2.http.Multipart
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>>> syncElectorsAndVoterImage(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldElectorID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part oldLocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part locationName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part wardName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part llgName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part districtId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part regionId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKADistrictId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALLGId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAWardId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKALocationId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part MKAClanId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part voterName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part gender, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dob, @retrofit2.http.Part(value = "isYOB")
    boolean isYOB, @retrofit2.http.Part(value = "isPWD")
    boolean isPWD, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part religion, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part fatherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherLName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part motherFName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provOfBirthFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part provinceName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part openElectorate, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part placeOfBirth, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupationFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part occupation, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part subClan, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part address, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natID, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part natIDRecordNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part status, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part houseSortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part restHouse, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part sectionNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part lotNo, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part streetName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part suburbTownSettlement, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyFK, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familyName, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part familySortOrder, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part dpo, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "lat")
    java.lang.Double lat, @org.jetbrains.annotations.Nullable
    @retrofit2.http.Part(value = "long")
    java.lang.Double longitude, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part posted, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part comment, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorProfileImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part electorFormImageUrl, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part profileImageBody, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part enrolmentFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part EnrolmentFormType, @retrofit2.http.Part(value = "IsManualFormNumber")
    boolean IsManualFormNumber, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part createdBy, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part tempElectorId, @org.jetbrains.annotations.NotNull
    @retrofit2.http.Part
    okhttp3.MultipartBody.Part clanId);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetActiveProvinceList")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList>>> callGetActiveProvinceListAPI();
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/UpdateUserGeoStructure")
    public abstract retrofit2.Call<com.pngelectoral.ui.retrofit.Response<java.lang.Object>> callUpdateGeoStructureAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "Voter/GetUpdatedVoterList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.TransferInVotersModel> callGetUpdatedVoterListAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/GetDeviceOperatorReport")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.DeviceOperatorReportModel> callGetDeviceOperatorReportAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/GetStatusAndDOBDiscrepancyReport")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.StatusAndDobDiscrepancyReportModel> callGetVoterStatusAndDOBDiscrepancyReportAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonObject inputParam);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetSetting")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.GeneralSettingsModel> callGeneralSettingsAPI();
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.POST(value = "General/GetSyncStatus")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.SyncedVoterStatusModel> callCheckSyncedElectorsAPI(@org.jetbrains.annotations.NotNull
    @retrofit2.http.Body
    com.google.gson.JsonArray syncDataVotersListArray);
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetMKAGeoList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.MKAGeoListModel> callMKAGeoListAPI();
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetMKAClanList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.MKAClanListModel> callMKAClanListAPI();
    
    @org.jetbrains.annotations.NotNull
    @retrofit2.http.GET(value = "General/GetGeoClanList")
    public abstract retrofit2.Call<com.pngelectoral.ui.model.ClanListModel> callGeoClanListAPI();
}