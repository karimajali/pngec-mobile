package com.pngelectoral.ui.activity;

import java.lang.System;

@kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000\u00f6\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0016\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0017\n\u0002\u0010\u0011\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\u0018\u00002\u00020\u00012\u00020\u0002:\u0004\u00ce\u0001\u00cf\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\n\u0010\u007f\u001a\u0004\u0018\u00010eH\u0002J\u001e\u0010\u0080\u0001\u001a\b\u0012\u0004\u0012\u00020,0l2\r\u0010\u0081\u0001\u001a\b\u0012\u0004\u0012\u00020,0lH\u0002J\n\u0010\u0082\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0084\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0085\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0086\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0087\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0088\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0089\u0001\u001a\u00030\u0083\u0001H\u0002J\u0013\u0010\u008a\u0001\u001a\u00030\u0083\u00012\u0007\u0010\u008b\u0001\u001a\u00020\u000bH\u0002J\u0016\u0010\u008c\u0001\u001a\u0004\u0018\u0001012\t\u0010\u008d\u0001\u001a\u0004\u0018\u000101H\u0002J\n\u0010\u008e\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u008f\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0090\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0091\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0092\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0093\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0094\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0095\u0001\u001a\u00030\u0083\u0001H\u0003J\n\u0010\u0096\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u0097\u0001\u001a\u00030\u0083\u0001H\u0002J\u0016\u0010\u0098\u0001\u001a\u00030\u0083\u00012\n\u0010\u0099\u0001\u001a\u0005\u0018\u00010\u009a\u0001H\u0014J\n\u0010\u009b\u0001\u001a\u00030\u0083\u0001H\u0014J\n\u0010\u009c\u0001\u001a\u00030\u0083\u0001H\u0002J\u0015\u0010\u009d\u0001\u001a\u00030\u0083\u00012\t\u0010\u009e\u0001\u001a\u0004\u0018\u00010,H\u0003J\u0013\u0010\u009f\u0001\u001a\u00030\u0083\u00012\u0007\u0010\u00a0\u0001\u001a\u000201H\u0002J)\u0010\u00a1\u0001\u001a\u00030\u00a2\u00012\u0007\u0010\u00a0\u0001\u001a\u0002012\t\u0010\u00a3\u0001\u001a\u0004\u0018\u0001012\t\u0010\u00a4\u0001\u001a\u0004\u0018\u000101H\u0002J\u001b\u0010\u00a5\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010lH\u0002J\n\u0010\u00a6\u0001\u001a\u00030\u0083\u0001H\u0002J\u0012\u0010\u00a7\u0001\u001a\u00030\u0083\u00012\u0006\u0010n\u001a\u00020,H\u0002J\u0013\u0010\u00a8\u0001\u001a\u00030\u0083\u00012\u0007\u0010\u00a9\u0001\u001a\u000201H\u0002J\u001b\u0010\u00aa\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010lH\u0002J\n\u0010\u00ab\u0001\u001a\u00030\u0083\u0001H\u0002J\u001b\u0010\u00ac\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020,\u0018\u00010lH\u0002J\u001b\u0010\u00ad\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010lH\u0002J\u001b\u0010\u00ae\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010lH\u0002J\u0019\u0010\u00af\u0001\u001a\u00030\u0083\u00012\r\u0010\u00b0\u0001\u001a\b\u0012\u0004\u0012\u00020W0\u001eH\u0002J\u0018\u0010\u00b1\u0001\u001a\u00030\u0083\u00012\f\u00109\u001a\b\u0012\u0004\u0012\u00020Y0\u001eH\u0002J\u0019\u0010\u00b2\u0001\u001a\u00030\u0083\u00012\r\u0010\u00b3\u0001\u001a\b\u0012\u0004\u0012\u00020[0\u001eH\u0002J\u0019\u0010\u00b4\u0001\u001a\u00030\u0083\u00012\r\u0010\u00b5\u0001\u001a\b\u0012\u0004\u0012\u00020]0\u001eH\u0002J\u001b\u0010\u00b6\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020e\u0018\u00010lH\u0002J\u001b\u0010\u00b7\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020m\u0018\u00010lH\u0002J3\u0010\u00b8\u0001\u001a\u00030\u0083\u00012\u000e\u0010\u00b9\u0001\u001a\t\u0012\u0004\u0012\u0002010\u00ba\u00012\b\u0010\u00bb\u0001\u001a\u00030\u00bc\u00012\u0007\u0010\u00bd\u0001\u001a\u000201H\u0002\u00a2\u0006\u0003\u0010\u00be\u0001J\n\u0010\u00bf\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u00c0\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u00c1\u0001\u001a\u00030\u0083\u0001H\u0002J\u001b\u0010\u00c2\u0001\u001a\u00030\u0083\u00012\u000f\u0010\u0081\u0001\u001a\n\u0012\u0004\u0012\u00020z\u0018\u00010lH\u0002J\u008b\u0001\u0010\u00c3\u0001\u001a\u00030\u0083\u00012\b\u0010h\u001a\u0004\u0018\u00010\u000b2\u0007\u0010\u00c4\u0001\u001a\u0002012\b\u0010a\u001a\u0004\u0018\u00010\u000b2\b\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0007\u0010\u00c5\u0001\u001a\u0002012\u0007\u0010\u00c6\u0001\u001a\u0002012\b\u00106\u001a\u0004\u0018\u00010\u000b2\u0007\u0010\u00c7\u0001\u001a\u0002012\b\u0010s\u001a\u0004\u0018\u00010\u000b2\u0007\u0010\u00c8\u0001\u001a\u0002012\b\u0010D\u001a\u0004\u0018\u00010\u000b2\u0007\u0010\u00c9\u0001\u001a\u0002012\u0007\u0010\u00ca\u0001\u001a\u000201H\u0002\u00a2\u0006\u0003\u0010\u00cb\u0001J\n\u0010\u00cc\u0001\u001a\u00030\u0083\u0001H\u0002J\n\u0010\u00cd\u0001\u001a\u00030\u0083\u0001H\u0002R\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\b\u001a\u0004\u0018\u00010\tX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0010\u001a\u00020\u00118VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0012\u0010\u0013R\u001a\u0010\u0014\u001a\u00020\u0015X\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u001a\u0010\u001a\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\r\"\u0004\b\u001c\u0010\u000fR\"\u0010\u001d\u001a\n\u0012\u0004\u0012\u00020\u001f\u0018\u00010\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b \u0010!\"\u0004\b\"\u0010#R\u001a\u0010$\u001a\u00020%X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b&\u0010\'\"\u0004\b(\u0010)R\u001c\u0010*\u001a\u0010\u0012\u0004\u0012\u00020,\u0012\u0004\u0012\u00020-\u0018\u00010+X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010.\u001a\u0004\u0018\u00010/X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00102\u001a\u000201X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u00103\u001a\u000204X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u00105\u001a\u000201X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u00106\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b7\u0010\r\"\u0004\b8\u0010\u000fR\"\u00109\u001a\n\u0012\u0004\u0012\u00020:\u0018\u00010\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b;\u0010!\"\u0004\b<\u0010#R\u001a\u0010=\u001a\u000201X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b>\u0010?\"\u0004\b@\u0010AR\u0016\u0010B\u001a\n\u0012\u0004\u0012\u00020C\u0018\u00010\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010D\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bE\u0010\r\"\u0004\bF\u0010\u000fR\u001a\u0010G\u001a\u00020HX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bI\u0010J\"\u0004\bK\u0010LR\u001c\u0010M\u001a\u0004\u0018\u00010NX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bO\u0010P\"\u0004\bQ\u0010RR\u001c\u0010S\u001a\u0004\u0018\u00010NX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bT\u0010P\"\u0004\bU\u0010RR\u0014\u0010V\u001a\b\u0012\u0004\u0012\u00020W0\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010X\u001a\b\u0012\u0004\u0012\u00020Y0\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010Z\u001a\b\u0012\u0004\u0012\u00020[0\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\\\u001a\b\u0012\u0004\u0012\u00020]0\u001eX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010^\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b_\u0010\r\"\u0004\b`\u0010\u000fR\u001a\u0010a\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010\r\"\u0004\bc\u0010\u000fR\"\u0010d\u001a\n\u0012\u0004\u0012\u00020e\u0018\u00010\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bf\u0010!\"\u0004\bg\u0010#R\u001a\u0010h\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bi\u0010\r\"\u0004\bj\u0010\u000fR\u0016\u0010k\u001a\n\u0012\u0004\u0012\u00020m\u0018\u00010lX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010n\u001a\u00020,X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bo\u0010p\"\u0004\bq\u0010rR\u001a\u0010s\u001a\u00020\u000bX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bt\u0010\r\"\u0004\bu\u0010\u000fR\u001a\u0010v\u001a\u000201X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\bw\u0010?\"\u0004\bx\u0010AR\"\u0010y\u001a\n\u0012\u0004\u0012\u00020z\u0018\u00010\u001eX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b{\u0010!\"\u0004\b|\u0010#R\u0014\u0010}\u001a\b\u0012\u0004\u0012\u00020,0~X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u00d0\u0001"}, d2 = {"Lcom/pngelectoral/ui/activity/WorkingRollUpdateStatusActivity;", "Lcom/pngelectoral/base/BaseActivity;", "Lkotlinx/coroutines/CoroutineScope;", "()V", "binding", "Lcom/pngelectoral/databinding/ActivityWorkingRollUpdateStatusBinding;", "bindingDuplicateVotersList", "Lcom/pngelectoral/databinding/DialogDuplicateVotersListBinding;", "bindingUpdateStatus", "Lcom/pngelectoral/databinding/DialogUpdateStatusBinding;", "clanId", "", "getClanId", "()I", "setClanId", "(I)V", "coroutineContext", "Lkotlin/coroutines/CoroutineContext;", "getCoroutineContext", "()Lkotlin/coroutines/CoroutineContext;", "dataBaseInstance", "Lcom/pngelectoral/data/db/AppDatabase;", "getDataBaseInstance", "()Lcom/pngelectoral/data/db/AppDatabase;", "setDataBaseInstance", "(Lcom/pngelectoral/data/db/AppDatabase;)V", "districtId", "getDistrictId", "setDistrictId", "districtListArray", "Ljava/util/ArrayList;", "Lcom/pngelectoral/ui/model/DistrictList;", "getDistrictListArray", "()Ljava/util/ArrayList;", "setDistrictListArray", "(Ljava/util/ArrayList;)V", "duplicateId", "", "getDuplicateId", "()J", "setDuplicateId", "(J)V", "duplicateVotersAdapter", "Lcom/pngelectoral/ui/adapter/GenericAdapter;", "Lcom/pngelectoral/ui/model/VotersList;", "Lcom/pngelectoral/databinding/ItemSyncVoterBinding;", "editVoterDetailsViewModel", "Lcom/pngelectoral/ui/viewModel/EditVoterDetailsViewModel;", "filterType", "", "initialWardName", "job", "Lkotlinx/coroutines/Job;", "lastDateOfEnrolment", "llgId", "getLlgId", "setLlgId", "llgListArray", "Lcom/pngelectoral/ui/model/LLGList;", "getLlgListArray", "setLlgListArray", "llgName", "getLlgName", "()Ljava/lang/String;", "setLlgName", "(Ljava/lang/String;)V", "locationArray", "Lcom/pngelectoral/ui/model/LocationList;", "locationId", "getLocationId", "setLocationId", "loggedInDPOUserData", "Lcom/pngelectoral/ui/model/DPOUserData;", "getLoggedInDPOUserData", "()Lcom/pngelectoral/ui/model/DPOUserData;", "setLoggedInDPOUserData", "(Lcom/pngelectoral/ui/model/DPOUserData;)V", "mDuplicateVotersListDialog", "Landroid/app/AlertDialog;", "getMDuplicateVotersListDialog", "()Landroid/app/AlertDialog;", "setMDuplicateVotersListDialog", "(Landroid/app/AlertDialog;)V", "mUpdateStatusDialog", "getMUpdateStatusDialog", "setMUpdateStatusDialog", "mkaDistrictArray", "Lcom/pngelectoral/ui/model/MKAGeoListData;", "mkaLlgArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG;", "mkaLocationArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward$Location;", "mkaWardArray", "Lcom/pngelectoral/ui/model/MKAGeoListModel$GeoListData$LlG$Ward;", "occupationId", "getOccupationId", "setOccupationId", "provinceId", "getProvinceId", "setProvinceId", "provinceListArray", "Lcom/pngelectoral/ui/model/ProvinceList;", "getProvinceListArray", "setProvinceListArray", "regionId", "getRegionId", "setRegionId", "regionListArray", "", "Lcom/pngelectoral/ui/model/RegionList;", "voterDetailsResponse", "getVoterDetailsResponse", "()Lcom/pngelectoral/ui/model/VotersList;", "setVoterDetailsResponse", "(Lcom/pngelectoral/ui/model/VotersList;)V", "wardId", "getWardId", "setWardId", "wardName", "getWardName", "setWardName", "wardNoAndNameArray", "Lcom/pngelectoral/ui/model/WardList;", "getWardNoAndNameArray", "setWardNoAndNameArray", "wardVotersList", "", "fetchVoterProvinceOfBirthNameFromDB", "getDisplayVotersListToMakeDuplicate", "result", "getDistrictListForUpdateStatusFromDB", "", "getLLGListForUpdateStatusFromDB", "getLocationListListForUpdateStatusFromDB", "getMKADistrictListFromDB", "getMKALlgListFromDB", "getMKALocationList", "getMKAWardList", "getOccupation", "occupationFK", "getPostedDate", "postedDate", "getProvinceListForUpdateStatusFromDB", "getRegionListFromDB", "getVoterDistrictName", "getVoterLLGName", "getVoterLocationName", "getVoterProvinceName", "getVoterProvinceOfBirthName", "getVoterWardName", "getWardNoAndNameListListForUpdateStatusFromDB", "hideTransferredOptions", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "resetVoterStatus", "saveDuplicateVoter", "model", "searchVotersList", "voterNameOrID", "searchWithFirstAndLastName", "", "fName", "lName", "setDuplicateVotersAdapter", "setFooterData", "setVoterData", "showAlertMessage", "alertMessage", "showDistrictOptionsForUpdateStatus", "showDuplicateAlertMessage", "showDuplicateRecords", "showLLGOptionsForUpdateStatus", "showLocationOptionsForUpdateStatus", "showMKADistrict", "distListArray", "showMKALlg", "showMKALocation", "locationListArray", "showMKAWard", "wardListArray", "showProvinceOptionsForUpdateStatus", "showRegionOptionsForUpdateStatus", "showSingleSelectionDialogForUpdateStatus", "singleChoiceItems", "", "tvSelection", "Landroidx/appcompat/widget/AppCompatTextView;", "selectedField", "([Ljava/lang/String;Landroidx/appcompat/widget/AppCompatTextView;Ljava/lang/String;)V", "showStatusOptions", "showTransferredOptions", "showUpdateStatusDialog", "showWardNoAndNameOptionsForUpdateStatus", "updateTransferredOutStatus", "province", "district", "llg", "ward", "location", "status", "comment", "(Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/Integer;Ljava/lang/String;Ljava/lang/String;)V", "updateVoterStatus", "validateUpdateStatus", "GetDuplicateVotersListAsync", "UpdatedVoterInformationToDB", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
public final class WorkingRollUpdateStatusActivity extends com.pngelectoral.base.BaseActivity implements kotlinx.coroutines.CoroutineScope {
    private com.pngelectoral.databinding.ActivityWorkingRollUpdateStatusBinding binding;
    private com.pngelectoral.ui.viewModel.EditVoterDetailsViewModel editVoterDetailsViewModel;
    @org.jetbrains.annotations.NotNull
    private com.pngelectoral.ui.model.VotersList voterDetailsResponse;
    public com.pngelectoral.ui.model.DPOUserData loggedInDPOUserData;
    public com.pngelectoral.data.db.AppDatabase dataBaseInstance;
    private int regionId = 0;
    private int provinceId = 0;
    private int districtId = 0;
    private int llgId = 0;
    private int wardId = 0;
    private int locationId = 0;
    @org.jetbrains.annotations.NotNull
    private java.lang.String llgName = "";
    @org.jetbrains.annotations.NotNull
    private java.lang.String wardName = "";
    private java.lang.String initialWardName = "";
    private java.lang.String lastDateOfEnrolment = "";
    private int occupationId = 0;
    private long duplicateId = 0L;
    private int clanId = 0;
    private java.lang.String filterType = "";
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListData> mkaDistrictArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG> mkaLlgArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward> mkaWardArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> mkaLocationArray;
    private kotlinx.coroutines.Job job;
    private com.pngelectoral.databinding.DialogUpdateStatusBinding bindingUpdateStatus;
    @org.jetbrains.annotations.Nullable
    private android.app.AlertDialog mUpdateStatusDialog;
    private java.util.List<com.pngelectoral.ui.model.VotersList> wardVotersList;
    private com.pngelectoral.databinding.DialogDuplicateVotersListBinding bindingDuplicateVotersList;
    @org.jetbrains.annotations.Nullable
    private android.app.AlertDialog mDuplicateVotersListDialog;
    private com.pngelectoral.ui.adapter.GenericAdapter<com.pngelectoral.ui.model.VotersList, com.pngelectoral.databinding.ItemSyncVoterBinding> duplicateVotersAdapter;
    private java.util.List<? extends com.pngelectoral.ui.model.RegionList> regionListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> provinceListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> districtListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.LLGList> llgListArray;
    @org.jetbrains.annotations.Nullable
    private java.util.ArrayList<com.pngelectoral.ui.model.WardList> wardNoAndNameArray;
    private java.util.ArrayList<com.pngelectoral.ui.model.LocationList> locationArray;
    private java.util.HashMap _$_findViewCache;
    
    public WorkingRollUpdateStatusActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull
    public final com.pngelectoral.ui.model.VotersList getVoterDetailsResponse() {
        return null;
    }
    
    public final void setVoterDetailsResponse(@org.jetbrains.annotations.NotNull
    com.pngelectoral.ui.model.VotersList p0) {
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
    
    public final int getRegionId() {
        return 0;
    }
    
    public final void setRegionId(int p0) {
    }
    
    public final int getProvinceId() {
        return 0;
    }
    
    public final void setProvinceId(int p0) {
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
    
    public final int getOccupationId() {
        return 0;
    }
    
    public final void setOccupationId(int p0) {
    }
    
    public final long getDuplicateId() {
        return 0L;
    }
    
    public final void setDuplicateId(long p0) {
    }
    
    public final int getClanId() {
        return 0;
    }
    
    public final void setClanId(int p0) {
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
    
    private final void setFooterData() {
    }
    
    @java.lang.Override
    protected void onDestroy() {
    }
    
    private final void setVoterData(com.pngelectoral.ui.model.VotersList voterDetailsResponse) {
    }
    
    private final void getOccupation(int occupationFK) {
    }
    
    private final void getVoterProvinceOfBirthName() {
    }
    
    private final com.pngelectoral.ui.model.ProvinceList fetchVoterProvinceOfBirthNameFromDB() {
        return null;
    }
    
    private final void getVoterProvinceName() {
    }
    
    private final void getVoterDistrictName() {
    }
    
    private final void getVoterLLGName() {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void getVoterWardName() {
    }
    
    private final void getVoterLocationName() {
    }
    
    private final void showDuplicateAlertMessage() {
    }
    
    private final void showAlertMessage(java.lang.String alertMessage) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.app.AlertDialog getMUpdateStatusDialog() {
        return null;
    }
    
    public final void setMUpdateStatusDialog(@org.jetbrains.annotations.Nullable
    android.app.AlertDialog p0) {
    }
    
    private final void showUpdateStatusDialog() {
    }
    
    private final void validateUpdateStatus() {
    }
    
    private final void updateVoterStatus() {
    }
    
    private final void updateTransferredOutStatus(java.lang.Integer regionId, java.lang.String province, java.lang.Integer provinceId, java.lang.Integer districtId, java.lang.String district, java.lang.String llg, java.lang.Integer llgId, java.lang.String ward, java.lang.Integer wardId, java.lang.String location, java.lang.Integer locationId, java.lang.String status, java.lang.String comment) {
    }
    
    private final void showStatusOptions() {
    }
    
    private final void resetVoterStatus() {
    }
    
    private final java.util.List<com.pngelectoral.ui.model.VotersList> getDisplayVotersListToMakeDuplicate(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable
    public final android.app.AlertDialog getMDuplicateVotersListDialog() {
        return null;
    }
    
    public final void setMDuplicateVotersListDialog(@org.jetbrains.annotations.Nullable
    android.app.AlertDialog p0) {
    }
    
    private final void showDuplicateRecords(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
    }
    
    private final void searchVotersList(java.lang.String voterNameOrID) {
    }
    
    private final boolean searchWithFirstAndLastName(java.lang.String voterNameOrID, java.lang.String fName, java.lang.String lName) {
        return false;
    }
    
    private final void setDuplicateVotersAdapter(java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    private final void saveDuplicateVoter(com.pngelectoral.ui.model.VotersList model) {
    }
    
    private final java.lang.String getPostedDate(java.lang.String postedDate) {
        return null;
    }
    
    private final void hideTransferredOptions() {
    }
    
    private final void showTransferredOptions() {
    }
    
    private final void getRegionListFromDB() {
    }
    
    private final void showRegionOptionsForUpdateStatus(java.util.List<? extends com.pngelectoral.ui.model.RegionList> result) {
    }
    
    private final void getProvinceListForUpdateStatusFromDB() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> getProvinceListArray() {
        return null;
    }
    
    public final void setProvinceListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.ProvinceList> p0) {
    }
    
    private final void showProvinceOptionsForUpdateStatus(java.util.List<? extends com.pngelectoral.ui.model.ProvinceList> result) {
    }
    
    private final void getDistrictListForUpdateStatusFromDB() {
    }
    
    private final void getMKADistrictListFromDB() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> getDistrictListArray() {
        return null;
    }
    
    public final void setDistrictListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.DistrictList> p0) {
    }
    
    private final void showDistrictOptionsForUpdateStatus(java.util.List<? extends com.pngelectoral.ui.model.DistrictList> result) {
    }
    
    private final void showMKADistrict(java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListData> distListArray) {
    }
    
    private final void getLLGListForUpdateStatusFromDB() {
    }
    
    private final void getMKALlgListFromDB() {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.LLGList> getLlgListArray() {
        return null;
    }
    
    public final void setLlgListArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.LLGList> p0) {
    }
    
    private final void showLLGOptionsForUpdateStatus(java.util.List<? extends com.pngelectoral.ui.model.LLGList> result) {
    }
    
    private final void showMKALlg(java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG> llgListArray) {
    }
    
    @org.jetbrains.annotations.Nullable
    public final java.util.ArrayList<com.pngelectoral.ui.model.WardList> getWardNoAndNameArray() {
        return null;
    }
    
    public final void setWardNoAndNameArray(@org.jetbrains.annotations.Nullable
    java.util.ArrayList<com.pngelectoral.ui.model.WardList> p0) {
    }
    
    private final void getWardNoAndNameListListForUpdateStatusFromDB() {
    }
    
    private final void getMKAWardList() {
    }
    
    private final void showWardNoAndNameOptionsForUpdateStatus(java.util.List<? extends com.pngelectoral.ui.model.WardList> result) {
    }
    
    private final void showMKAWard(java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward> wardListArray) {
    }
    
    private final void getLocationListListForUpdateStatusFromDB() {
    }
    
    private final void getMKALocationList() {
    }
    
    private final void showLocationOptionsForUpdateStatus(java.util.List<? extends com.pngelectoral.ui.model.LocationList> result) {
    }
    
    private final void showMKALocation(java.util.ArrayList<com.pngelectoral.ui.model.MKAGeoListModel.GeoListData.LlG.Ward.Location> locationListArray) {
    }
    
    private final void showSingleSelectionDialogForUpdateStatus(java.lang.String[] singleChoiceItems, androidx.appcompat.widget.AppCompatTextView tvSelection, java.lang.String selectedField) {
    }
    
    @android.annotation.SuppressLint(value = {"StaticFieldLeak"})
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0081\u0004\u0018\u00002\u001a\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\'\u0010\u0006\u001a\u0004\u0018\u00010\u00022\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\b\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\tJ\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0014R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lcom/pngelectoral/ui/activity/WorkingRollUpdateStatusActivity$UpdatedVoterInformationToDB;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "updatedVoterInformation", "Lcom/pngelectoral/ui/model/VotersList;", "(Lcom/pngelectoral/ui/activity/WorkingRollUpdateStatusActivity;Lcom/pngelectoral/ui/model/VotersList;)V", "doInBackground", "p0", "", "([Ljava/lang/Void;)Ljava/lang/Void;", "onPostExecute", "", "result", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public final class UpdatedVoterInformationToDB extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.lang.Void> {
        private final com.pngelectoral.ui.model.VotersList updatedVoterInformation = null;
        
        public UpdatedVoterInformationToDB(@org.jetbrains.annotations.NotNull
        com.pngelectoral.ui.model.VotersList updatedVoterInformation) {
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
    
    @android.annotation.SuppressLint(value = {"StaticFieldLeak"})
    @kotlin.Metadata(mv = {1, 5, 1}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0081\u0004\u0018\u00002 \u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u0002\u0012\f\u0012\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00030\u0001B\u0005\u00a2\u0006\u0002\u0010\u0005J-\u0010\u0006\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u00032\u0016\u0010\u0007\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00020\b\"\u0004\u0018\u00010\u0002H\u0014\u00a2\u0006\u0002\u0010\tJ\u0018\u0010\n\u001a\u00020\u000b2\u000e\u0010\f\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\u0003H\u0014\u00a8\u0006\r"}, d2 = {"Lcom/pngelectoral/ui/activity/WorkingRollUpdateStatusActivity$GetDuplicateVotersListAsync;", "Landroid/os/AsyncTask;", "Ljava/lang/Void;", "", "Lcom/pngelectoral/ui/model/VotersList;", "(Lcom/pngelectoral/ui/activity/WorkingRollUpdateStatusActivity;)V", "doInBackground", "p0", "", "([Ljava/lang/Void;)Ljava/util/List;", "onPostExecute", "", "result", "PNGEC2026.6.02(21)_2026_10_12_devDebug"})
    public final class GetDuplicateVotersListAsync extends android.os.AsyncTask<java.lang.Void, java.lang.Void, java.util.List<? extends com.pngelectoral.ui.model.VotersList>> {
        
        public GetDuplicateVotersListAsync() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable
        @java.lang.Override
        protected java.util.List<com.pngelectoral.ui.model.VotersList> doInBackground(@org.jetbrains.annotations.NotNull
        java.lang.Void... p0) {
            return null;
        }
        
        @java.lang.Override
        protected void onPostExecute(@org.jetbrains.annotations.Nullable
        java.util.List<? extends com.pngelectoral.ui.model.VotersList> result) {
        }
    }
}