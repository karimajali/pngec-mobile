package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\u00020\u00012\u00020\u0002:\u0002\u00c9\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010{\u001a\u00020|2\u0006\u0010}\u001a\u00020QH\u0002J\u0017\u0010~\u001a\u00020|2\r\u0010\u007f\u001a\t\u0012\u0004\u0012\u00020Q0\u0080\u0001H\u0002J#\u0010\u0081\u0001\u001a\u00020#2\u0006\u0010}\u001a\u00020Q2\u0010\u0010\u0082\u0001\u001a\u000b\u0012\u0005\u0012\u00030\u0083\u0001\u0018\u00010*H\u0002J\t\u0010\u0084\u0001\u001a\u00020|H\u0002J\t\u0010\u0085\u0001\u001a\u00020|H\u0002J\t\u0010\u0086\u0001\u001a\u00020|H\u0002J\t\u0010\u0087\u0001\u001a\u00020|H\u0002J\u0017\u0010\u0088\u0001\u001a\u0005\u0018\u00010\u0089\u00012\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u001cH\u0002J\u0017\u0010\u008b\u0001\u001a\u0005\u0018\u00010\u0089\u00012\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u001cH\u0002J#\u0010\u008c\u0001\u001a\u00020\u001c2\u0006\u0010}\u001a\u00020Q2\u0010\u0010\u0082\u0001\u001a\u000b\u0012\u0005\u0012\u00030\u0083\u0001\u0018\u00010*H\u0002J\u0015\u0010\u008d\u0001\u001a\u00020\u001c2\n\u0010\u008e\u0001\u001a\u0005\u0018\u00010\u0083\u0001H\u0002J\t\u0010\u008f\u0001\u001a\u00020|H\u0002J\t\u0010\u0090\u0001\u001a\u00020|H\u0002J\t\u0010\u0091\u0001\u001a\u00020|H\u0002J\u0012\u0010\u0092\u0001\u001a\u000b\u0012\u0004\u0012\u00020I\u0018\u00010\u0080\u0001H\u0002J\t\u0010\u0093\u0001\u001a\u00020|H\u0002J\u0016\u0010\u0094\u0001\u001a\u0004\u0018\u00010\u001c2\t\u0010\u0095\u0001\u001a\u0004\u0018\u00010\u001cH\u0002J\u000b\u0010\u0096\u0001\u001a\u0004\u0018\u00010\u001cH\u0002J&\u0010\u0097\u0001\u001a\b\u0012\u0004\u0012\u00020Q0*2\f\u0010k\u001a\b\u0012\u0004\u0012\u00020Q0*2\u0007\u0010\u0098\u0001\u001a\u00020\u001cH\u0002J\u0014\u0010\u0099\u0001\u001a\u00020\u001c2\t\u0010\u008e\u0001\u001a\u0004\u0018\u00010QH\u0002J#\u0010\u009a\u0001\u001a\u0005\u0018\u00010\u0089\u00012\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u001c2\n\u0010\u009b\u0001\u001a\u0005\u0018\u00010\u009c\u0001H\u0002J#\u0010\u009d\u0001\u001a\u0005\u0018\u00010\u0089\u00012\t\u0010\u008a\u0001\u001a\u0004\u0018\u00010\u001c2\n\u0010\u009b\u0001\u001a\u0005\u0018\u00010\u009c\u0001H\u0002J\t\u0010\u009e\u0001\u001a\u00020|H\u0002J\u001e\u0010\u009f\u0001\u001a\b\u0012\u0004\u0012\u00020Q0*2\r\u0010\u007f\u001a\t\u0012\u0004\u0012\u00020Q0\u0080\u0001H\u0002J\t\u0010\u00a0\u0001\u001a\u00020|H\u0002J\t\u0010\u00a1\u0001\u001a\u00020|H\u0002J\t\u0010\u00a2\u0001\u001a\u00020|H\u0003J\t\u0010\u00a3\u0001\u001a\u00020|H\u0002J\u001a\u0010\u00a4\u0001\u001a\u00020|2\u000f\u0010\u007f\u001a\u000b\u0012\u0004\u0012\u00020Q\u0018\u00010\u0080\u0001H\u0002J\u0015\u0010\u00a5\u0001\u001a\u00020|2\n\u0010\u00a6\u0001\u001a\u0005\u0018\u00010\u00a7\u0001H\u0014J\t\u0010\u00a8\u0001\u001a\u00020|H\u0014J\u0015\u0010\u00a9\u0001\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020V0TH\u0002J\u0013\u0010\u00aa\u0001\u001a\u00020|2\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u0001H\u0002J\t\u0010\u00ad\u0001\u001a\u00020|H\u0002J\"\u0010\u00ae\u0001\u001a\u00020|2\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u00012\r\u0010\u00af\u0001\u001a\b\u0012\u0004\u0012\u00020Q0*H\u0002J\"\u0010\u00b0\u0001\u001a\u00020|2\b\u0010\u00ab\u0001\u001a\u00030\u00ac\u00012\r\u0010\u00af\u0001\u001a\b\u0012\u0004\u0012\u00020Q0*H\u0002J\u0018\u0010\u00b1\u0001\u001a\u00020|2\r\u0010\u00af\u0001\u001a\b\u0012\u0004\u0012\u00020Q0\\H\u0002J\u0012\u0010\u00b2\u0001\u001a\u00020|2\u0007\u0010\u00b3\u0001\u001a\u00020\u001cH\u0002J\t\u0010\u00b4\u0001\u001a\u00020|H\u0002J\u001a\u0010\u00b5\u0001\u001a\u00020|2\u000f\u0010\u007f\u001a\u000b\u0012\u0004\u0012\u00020+\u0018\u00010\u0080\u0001H\u0002J\u001a\u0010\u00b6\u0001\u001a\u00020|2\u000f\u0010\u007f\u001a\u000b\u0012\u0004\u0012\u000204\u0018\u00010\u0080\u0001H\u0002J\t\u0010\u00b7\u0001\u001a\u00020|H\u0002J\t\u0010\u00b8\u0001\u001a\u00020|H\u0002J2\u0010\u00b9\u0001\u001a\u00020|2\u000e\u0010\u00ba\u0001\u001a\t\u0012\u0004\u0012\u00020\u001c0\u00bb\u00012\b\u0010\u00bc\u0001\u001a\u00030\u00bd\u00012\u0007\u0010\u00be\u0001\u001a\u00020\u001cH\u0002\u00a2\u0006\u0003\u0010\u00bf\u0001J\t\u0010\u00c0\u0001\u001a\u00020|H\u0002J\u0012\u0010\u00c1\u0001\u001a\u00020|2\u0007\u0010\u00c2\u0001\u001a\u00020\u001cH\u0002J\t\u0010\u00c3\u0001\u001a\u00020|H\u0002J\u001a\u0010\u00c4\u0001\u001a\u00020|2\u000f\u0010\u007f\u001a\u000b\u0012\u0004\u0012\u00020u\u0018\u00010\u0080\u0001H\u0002J\t\u0010\u00c5\u0001\u001a\u00020|H\u0002J\u001a\u0010\u00c6\u0001\u001a\u00020|2\u0006\u0010J\u001a\u00020\u000f2\u0007\u0010\u00c7\u0001\u001a\u00020\u000fH\u0002J\t\u0010\u00c8\u0001\u001a\u00020|H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0010\u001a\u00020\u0011X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001a\u0010\u0016\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u000e\u0010!\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\"\u001a\u00020#X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010$\u001a\u00020%X\u0082.\u00a2\u0006\u0002\n\u0000R\u001a\u0010&\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0018\"\u0004\b(\u0010\u001aR\"\u0010)\u001a\n\u0012\u0004\u0012\u00020+\u0018\u00010*X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b,\u0010-\"\u0004\b.\u0010/R\u001a\u00100\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b1\u0010\u001e\"\u0004\b2\u0010 R\u0016\u00103\u001a\n\u0012\u0004\u0012\u000204\u0018\u00010*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00105\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u0010\u0018\"\u0004\b7\u0010\u001aR\u001a\u00108\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b9\u0010\u001e\"\u0004\b:\u0010 R\u001a\u0010;\u001a\u00020<X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b=\u0010>\"\u0004\b?\u0010@R\u001c\u0010A\u001a\u0004\u0018\u00010BX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bC\u0010D\"\u0004\bE\u0010FR\u0010\u0010G\u001a\u0004\u0018\u00010BX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010H\u001a\b\u0012\u0004\u0012\u00020I0*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010J\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bK\u0010\u0018\"\u0004\bL\u0010\u001aR\u001a\u0010M\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bN\u0010\u001e\"\u0004\bO\u0010 R\u0014\u0010P\u001a\b\u0012\u0004\u0012\u00020Q0*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010R\u001a\b\u0012\u0004\u0012\u00020Q0*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010S\u001a\u0010\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020V\u0018\u00010TX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010W\u001a\b\u0012\u0004\u0012\u00020\u001c0*X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010X\u001a\b\u0012\u0004\u0012\u00020\u001c0*X\u0080\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bY\u0010-\"\u0004\bZ\u0010/R\u0014\u0010[\u001a\b\u0012\u0004\u0012\u00020U0\\X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0016\u0010]\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010_0^X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010`\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010a\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bc\u0010\u001e\"\u0004\bd\u0010 R\u000e\u0010e\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001c\u0010f\u001a\u0010\u0012\u0004\u0012\u00020Q\u0012\u0004\u0012\u00020g\u0018\u00010TX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010h\u001a\u0004\u0018\u00010iX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010j\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010k\u001a\b\u0012\u0004\u0012\u00020Q0*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010l\u001a\b\u0012\u0004\u0012\u00020Q0*X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010m\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bn\u0010\u0018\"\u0004\bo\u0010\u001aR\u001a\u0010p\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bq\u0010\u001e\"\u0004\br\u0010 R\u000e\u0010s\u001a\u00020\u001cX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\"\u0010t\u001a\n\u0012\u0004\u0012\u00020u\u0018\u00010*X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bv\u0010-\"\u0004\bw\u0010/R\u001a\u0010x\u001a\u00020\u000fX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\by\u0010\u0018\"\u0004\bz\u0010\u001a\u00a8\u0006\u00ca\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/VotersSyncWithDataAndImageActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivitySyncVotersListBinding;", "bindingLayoutLoaderDownloadProgressBar", "Lcom/pngelectoral/databinding/LayoutLoaderDownloadProgressBarBinding;", "bindingSelectGeoLocation", "Lcom/pngelectoral/databinding/DialogGeoAndFilterSelectionBinding;", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "currentUpdatedVotersList", "", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "getDistrictId", "()I", "setDistrictId", "(I)V", "districtName", "", "getDistrictName", "()Ljava/lang/String;", "setDistrictName", "(Ljava/lang/String;)V", "filterType", "isSyncDone", "", "job", "Lkotlinx/coroutines/Job;", "llgId", "getLlgId", "setLlgId", "llgListArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/LLGList;", "getLlgListArray", "()Ljava/util/ArrayList;", "setLlgListArray", "(Ljava/util/ArrayList;)V", "llgName", "getLlgName", "setLlgName", "locationArray", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "locationName", "getLocationName", "setLocationName", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mDownloadProgressDialog", "Landroid/app/AlertDialog;", "getMDownloadProgressDialog", "()Landroid/app/AlertDialog;", "setMDownloadProgressDialog", "(Landroid/app/AlertDialog;)V", "mSelectGeoLocationAlertDialog", "mkaLocationArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward$Location;", "percentage", "getPercentage", "setPercentage", "provinceName", "getProvinceName", "setProvinceName", "rejectedVotersList", "Lcom/pngelectoral/ui/model/VotersList;", "selectedVotersList", "statusAdapter", "Lcom/pngelectoral/ui/adapter/GenericAdapter;", "Lcom/pngelectoral/ui/model/VoterListFilterModel;", "Lcom/pngelectoral/databinding/ItemFilterBinding;", "statusFilterList", "statusId", "getStatusId$PNGEC2026_6_02_21__2026_10_12_devDebug", "setStatusId$PNGEC2026_6_02_21__2026_10_12_devDebug", "statusList", "", "storagePermissionResultLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "Landroid/content/Intent;", "successOrErrorMessage", "syncDataOptions", "syncMessage", "getSyncMessage", "setSyncMessage", "syncSummaryReportFilesPath", "syncVotersListAdapter", "Lcom/pngelectoral/databinding/ItemSyncVoterNewBinding;", "syncVotersListViewModel", "Lcom/pngelectoral/ui/viewModel/SyncVotersListViewModel;", "syncWardNoAndName", "syncedVotersList", "votersListToBeSynced", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "wardNoAndName", "wardNoAndNameArray", "Lcom/pngelectoral/ui/model/WardList;", "getWardNoAndNameArray", "setWardNoAndNameArray", "wardNumber", "getWardNumber", "setWardNumber", "callSyncVoterWithDataAndImageAPI", "", "currentVoter", "checkAndLoadData", "result", "", "checkCurrentVoterSyncStatus", "data", "Lcom/pngelectoral/ui/model/FailedVotersList;", "checkManageAllPermission", "exportSyncSummarySheetReport", "filterSyncedList", "generateSyncSummarySheetReport", "getBoldTextCell", "Lcom/itextpdf/layout/element/Cell;", "text", "getCell", "getErrorMessage", "getFailedVoterName", "model", "getLLGListUsingCoRoutines", "getLocationListFromDB", "getMKALocationListFromDB", "getMKALocationListQuery", "getMKAWardNumberAndNameUsingCoroutines", "getPostedDate", "postedDate", "getSyncedDateTime", "getVoterListStatusWise", "status", "getVoterName", "getVotersListCell", "alignment", "Lcom/itextpdf/layout/property/TextAlignment;", "getVotersListHeaderCell", "getVotersSyncList", "getVotersToBeSync", "getVotersToSyncBasedOnGeoSelection", "getWardNoAndNameListUsingCoRoutines", "getWardNumberAndNameUsingCoroutines", "hideSyncProgress", "loadData", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "prepareStatusAdapter", "setGrandTotalCount", "document", "Lcom/itextpdf/layout/Document;", "setOnClickListeners", "setRejectedVotersListInfoTableData", "votersList", "setSyncedVotersListInfoTableData", "setWardRollVotersListAdapter", "showAlertMessage", "message", "showDownloadProgressDialog", "showLLGOptions", "showLocationOptions", "showMKALocationOptions", "showSelectGeoLocationToSync", "showSingleSelectionDialog", "singleChoiceItems", "", "tvSelection", "Landroidx/appcompat/widget/AppCompatTextView;", "selectedField", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;Ljava/lang/String;)V", "showSyncCompletedMessage", "showSyncSuccessMessage", "syncFinishMessage", "showSyncSummarySheetSuccessMessage", "showWardNoAndNameOptions", "startSync", "uploadSyncProgress", "numberOfDataSyncs", "writeSyncSummarySheet", "UpdateVotersDataAfterSyncVoterWithDataAndImage", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class VotersSyncWithDataAndImageActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivitySyncVotersListBinding binding;
    private com.pngelectoral.ui.viewModel.SyncVotersListViewModel syncVotersListViewModel;
    private com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VotersList, com.pngelectoral.databinding.ItemSyncVoterNewBinding> syncVotersListAdapter;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private java.util.ArrayList<com.pngelectoral.ui.model.VotersList> selectedVotersList;
    private java.util.ArrayList<com.pngelectoral.ui.model.VotersList> votersListToBeSynced;
    private java.util.ArrayList<com.pngelectoral.ui.model.VotersList> syncedVotersList;
    private java.util.ArrayList<com.pngelectoral.ui.model.VotersList> rejectedVotersList;
    private int currentUpdatedVotersList = -1;
    private boolean isSyncDone = false;
    private java.lang.String successOrErrorMessage = "";
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String provinceName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String districtName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String llgName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    private java.lang.String wardNoAndName = "";
    private java.lang.String syncWardNoAndName = "";
    private int wardNumber = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String locationName = "";
    private java.util.List<com.pngelectoral.ui.model.VoterListFilterModel> statusList;
    private final java.util.ArrayList<java.lang.String> statusFilterList = null;
    private java.lang.String filterType = "";
    private java.lang.String syncDataOptions = "";
    private kotlinx.coroutines.Job job;
    private androidx.activity.result.ActivityResultLauncher<android.content.Intent> storagePermissionResultLauncher;
    private java.lang.String syncSummaryReportFilesPath = "PNG_Sync_Summary_Report";
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> mkaLocationArray;
    private com.pngelectoral.databinding.DialogGeoAndFilterSelectionBinding bindingSelectGeoLocation;
    private android.app.AlertDialog mSelectGeoLocationAlertDialog;
    private com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VoterListFilterModel, com.pngelectoral.databinding.ItemFilterBinding> statusAdapter;
    @org.jetbrains.annotations.NotNull
    private java.util.ArrayList<java.lang.String> statusId;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardNoAndNameArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private com.pngelectoral.databinding.LayoutLoaderDownloadProgressBarBinding bindingLayoutLoaderDownloadProgressBar;
    @org.jetbrains.annotations.Nullable
    private android.app.AlertDialog mDownloadProgressDialog;
    private int percentage = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String syncMessage = "";
    private java.util.HashMap _$_findViewCache;
    
    public VotersSyncWithDataAndImageActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.DPOUserData getLoggedInDPOUserData() {
        return null;
    }
    
    public final void setLoggedInDPOUserData(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.DPOUserData p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.data.db.AppDatabase getDataBaseInstance() {
        return null;
    }
    
    public final void setDataBaseInstance(@org.jetbrains.annotations.NotNull
    com.pngelectoral.data.db.AppDatabase p0) {
    }
    
    public final int getDistrictId() {
        return 0;
    }
    
    public final void setDistrictId(int p0) {
    }
    
    public final int getLlgId() {
        return 0;
    }
    
    public final void setLlgId(int p0) {
    }
    
    public final int getWardId() {
        return 0;
    }
    
    public final void setWardId(int p0) {
    }
    
    public final int getLocationId() {
        return 0;
    }
    
    public final void setLocationId(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getProvinceName() {
        return null;
    }
    
    public final void setProvinceName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getDistrictName() {
        return null;
    }
    
    public final void setDistrictName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLlgName() {
        return null;
    }
    
    public final void setLlgName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getWardName() {
        return null;
    }
    
    public final void setWardName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    public final int getWardNumber() {
        return 0;
    }
    
    public final void setWardNumber(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getLocationName() {
        return null;
    }
    
    public final void setLocationName(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.NotNull
    @java.lang.Override
    public kotlin.coroutines.CoroutineContext getCoroutineContext() {
        return null;
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getWardNumberAndNameUsingCoroutines() {
    }
    
    private final void getMKAWardNumberAndNameUsingCoroutines() {
    }
    
    private final void getVotersSyncList() {
    }
    
    private final void checkAndLoadData(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
    }
    
    private final java.util.ArrayList<com.pngelectoral.ui.model.VotersList> getVotersToBeSync(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
        return null;
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setOnClickListeners() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.util.ArrayList<java.lang.String> getStatusId$PNGEC2026_6_02_21__2026_10_12_devDebug() {
        return null;
    }
    
    public final void setStatusId$PNGEC2026_6_02_21__2026_10_12_devDebug(@org.jetbrains.annotations.NotNull
    java.util.ArrayList<java.lang.String> p0) {
    }
    
    private final void showSelectGeoLocationToSync() {
    }
    
    private final com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VoterListFilterModel, com.pngelectoral.databinding.ItemFilterBinding> prepareStatusAdapter() {
        return null;
    }
    
    private final void getLLGListUsingCoRoutines() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LLGList> getLlgListArray() {
        return null;
    }
    
    public final void setLlgListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> p0) {
    }
    
    private final void showLLGOptions(java.util.List<? extends com.pngelectoral.ui.model.LLGList> result) {
    }
    
    private final void getWardNoAndNameListUsingCoRoutines() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.WardList> getWardNoAndNameArray() {
        return null;
    }
    
    public final void setWardNoAndNameArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> p0) {
    }
    
    private final void showWardNoAndNameOptions(java.util.List<? extends com.pngelectoral.ui.model.WardList> result) {
    }
    
    private final void getLocationListFromDB() {
    }
    
    private final void showLocationOptions(java.util.List<? extends com.pngelectoral.ui.model.LocationList> result) {
    }
    
    private final void showSingleSelectionDialog(java.lang.String[] singleChoiceItems, androidx.appcompat.widget.AppCompatTextView tvSelection, java.lang.String selectedField) {
    }
    
    private final void getMKALocationListFromDB() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> getMKALocationListQuery() {
        return null;
    }
    
    private final void showMKALocationOptions() {
    }
    
    private final void filterSyncedList() {
    }
    
    private final void getVotersToSyncBasedOnGeoSelection() {
    }
    
    private final void startSync() {
    }
    
    private final void loadData(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
    }
    
    private final void setWardRollVotersListAdapter(java.util.List<com.pngelectoral.ui.model.VotersList> votersList) {
    }
    
    private final java.lang.String getVoterName(com.pngelectoral.ui.model.VotersList model) {
        return null;
    }
    
    private final java.lang.String getFailedVoterName(com.pngelectoral.ui.model.FailedVotersList model) {
        return null;
    }
    
    private final java.lang.String getPostedDate(java.lang.String postedDate) {
        return null;
    }
    
    private final boolean checkCurrentVoterSyncStatus(com.pngelectoral.ui.model.VotersList currentVoter, java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList> data) {
        return false;
    }
    
    private final java.lang.String getErrorMessage(com.pngelectoral.ui.model.VotersList currentVoter, java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList> data) {
        return null;
    }
    
    private final void callSyncVoterWithDataAndImageAPI(com.pngelectoral.ui.model.VotersList currentVoter) {
    }
    
    private final java.lang.String getSyncedDateTime() {
        return null;
    }
    
    private final void showAlertMessage(java.lang.String message) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.app.AlertDialog getMDownloadProgressDialog() {
        return null;
    }
    
    public final void setMDownloadProgressDialog(@org.jetbrains.annotations.Nullable
    android.app.AlertDialog p0) {
    }
    
    public final int getPercentage() {
        return 0;
    }
    
    public final void setPercentage(int p0) {
    }
    
    private final void showDownloadProgressDialog() {
    }
    
    private final void uploadSyncProgress(int percentage, int numberOfDataSyncs) {
    }
    
    private final void hideSyncProgress() {
    }
    
    @org.jetbrains.annotations.NotNull
    public final java.lang.String getSyncMessage() {
        return null;
    }
    
    public final void setSyncMessage(@org.jetbrains.annotations.NotNull
    java.lang.String p0) {
    }
    
    private final void showSyncSuccessMessage(java.lang.String syncFinishMessage) {
    }
    
    private final void generateSyncSummarySheetReport() {
    }
    
    private final void checkManageAllPermission() {
    }
    
    private final void exportSyncSummarySheetReport() {
    }
    
    private final void writeSyncSummarySheet() {
    }
    
    private final java.util.ArrayList<com.pngelectoral.ui.model.VotersList> getVoterListStatusWise(java.util.ArrayList<com.pngelectoral.ui.model.VotersList> syncedVotersList, java.lang.String status) {
        return null;
    }
    
    private final void setSyncedVotersListInfoTableData(com.itextpdf.layout.Document document, java.util.ArrayList<com.pngelectoral.ui.model.VotersList> votersList) {
    }
    
    private final void setRejectedVotersListInfoTableData(com.itextpdf.layout.Document document, java.util.ArrayList<com.pngelectoral.ui.model.VotersList> votersList) {
    }
    
    private final void setGrandTotalCount(com.itextpdf.layout.Document document) {
    }
    
    private final com.itextpdf.layout.element.Cell getBoldTextCell(java.lang.String text) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getCell(java.lang.String text) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getVotersListHeaderCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final com.itextpdf.layout.element.Cell getVotersListCell(java.lang.String text, com.itextpdf.layout.property.TextAlignment alignment) {
        return null;
    }
    
    private final void showSyncSummarySheetSuccessMessage() {
    }
    
    private final void showSyncCompletedMessage() {
    }
    
    @android.annotation.SuppressLint(value = {"StaticFieldLeak"})
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0081\u0004\u0018\u00002\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0012\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006\u00a2\u0006\u0002\u0010\tJ\'\u0010\n\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u000b\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\f\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\rJ\u0012\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0002H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/pngelectoral/ui/activity/VotersSyncWithDataAndImageActivity$UpdateVotersDataAfterSyncVoterWithDataAndImage;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "currentVoter", "Lcom/pngelectoral/ui/model/VotersList;", "mResponse", "Lcom/pngelectoral/ui/retrofit/Response;", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/FailedVotersList;", "(Lcom/pngelectoral/ui/activity/VotersSyncWithDataAndImageActivity;Lcom/pngelectoral/ui/model/VotersList;Lcom/pngelectoral/ui/retrofit/Response;)V", "doInBackground", "p0", "", "([Ljava/lang/Void;)Ljava/lang/Void;", "onPostExecute", "", "result", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public final class UpdateVotersDataAfterSyncVoterWithDataAndImage extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
        private final com.pngelectoral.ui.model.VotersList currentVoter = null;
        private final com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>> mResponse = null;
        
        public UpdateVotersDataAfterSyncVoterWithDataAndImage(@org.jetbrains.annotations.NotNull
        com.pngelectoral.ui.model.VotersList currentVoter, @org.jetbrains.annotations.NotNull
        com.pngelectoral.ui.retrofit.Response<java.util.ArrayList<com.pngelectoral.ui.model.FailedVotersList>> mResponse) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        @java.lang.Override
        protected java.lang.Void doInBackground(@org.jetbrains.annotations.NotNull
        java.lang.Void... p0) {
            return null;
        }
        
        @java.lang.Override
        protected void onPostExecute(@org.jetbrains.annotations.Nullable
        java.lang.Void result) {
        }
    }
}