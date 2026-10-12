package com.pngelectoral;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import com.pngelectoral.databinding.ActivityDeviceOperatorReportBindingImpl;
import com.pngelectoral.databinding.ActivityDeviceOperatorReportSelectionBindingImpl;
import com.pngelectoral.databinding.ActivityDownloadBindingImpl;
import com.pngelectoral.databinding.ActivityDpoGeoStructureUpdateBindingImpl;
import com.pngelectoral.databinding.ActivityEditVoterDetailsBindingImpl;
import com.pngelectoral.databinding.ActivityFailedVoterEditDetailsBindingImpl;
import com.pngelectoral.databinding.ActivityFieldTallySummarySheetReportBindingImpl;
import com.pngelectoral.databinding.ActivityFieldTallySummarySheetReportSelectionBindingImpl;
import com.pngelectoral.databinding.ActivityForm11ImageCaptureBindingImpl;
import com.pngelectoral.databinding.ActivityGenderLessVotersListReportBindingImpl;
import com.pngelectoral.databinding.ActivityGenderLessVotersListSelectionBindingImpl;
import com.pngelectoral.databinding.ActivityHomeBindingImpl;
import com.pngelectoral.databinding.ActivityImageDisplayBindingImpl;
import com.pngelectoral.databinding.ActivityLoginBindingImpl;
import com.pngelectoral.databinding.ActivityNewEnrolmentBindingImpl;
import com.pngelectoral.databinding.ActivityNewEnrolmentFormBindingImpl;
import com.pngelectoral.databinding.ActivityNewRegWardSelectionBindingImpl;
import com.pngelectoral.databinding.ActivityOfflineRollLookUpVotersListBindingImpl;
import com.pngelectoral.databinding.ActivityOfflineTransferInVotersListBindingImpl;
import com.pngelectoral.databinding.ActivityProvisionalEnrolmentFormBindingImpl;
import com.pngelectoral.databinding.ActivityProvisionalRegWardSelectionBindingImpl;
import com.pngelectoral.databinding.ActivityRejectedVoterEditDetailsBindingImpl;
import com.pngelectoral.databinding.ActivityRejectedVoterListBindingImpl;
import com.pngelectoral.databinding.ActivityReverseSyncBindingImpl;
import com.pngelectoral.databinding.ActivityRollLookUpBindingImpl;
import com.pngelectoral.databinding.ActivityRollLookUpVoterDetailsBindingImpl;
import com.pngelectoral.databinding.ActivityRollLookUpVotersListBindingImpl;
import com.pngelectoral.databinding.ActivitySettingsBindingImpl;
import com.pngelectoral.databinding.ActivitySplashBindingImpl;
import com.pngelectoral.databinding.ActivityStatusDiscrepancyReportBindingImpl;
import com.pngelectoral.databinding.ActivityStatusDiscrepancyReportSelectionBindingImpl;
import com.pngelectoral.databinding.ActivitySyncVotersListBindingImpl;
import com.pngelectoral.databinding.ActivitySyncedVotersListBindingImpl;
import com.pngelectoral.databinding.ActivityTransferInBindingImpl;
import com.pngelectoral.databinding.ActivityTransferInScanBindingImpl;
import com.pngelectoral.databinding.ActivityTransferInVoterDetailsBindingImpl;
import com.pngelectoral.databinding.ActivityTransferInVotersListBindingImpl;
import com.pngelectoral.databinding.ActivityTransferInWardSelectionBindingImpl;
import com.pngelectoral.databinding.ActivityUpdatedVotersListBindingImpl;
import com.pngelectoral.databinding.ActivityVoterDetailsBindingImpl;
import com.pngelectoral.databinding.ActivityVoterImageCaptureBindingImpl;
import com.pngelectoral.databinding.ActivityWardRollVoterListBindingImpl;
import com.pngelectoral.databinding.ActivityWorkingRollUpdateStatusBindingImpl;
import com.pngelectoral.databinding.BarcodeCaptureBindingImpl;
import com.pngelectoral.databinding.DialogAuditSettingScreenBindingImpl;
import com.pngelectoral.databinding.DialogDatePickerBindingImpl;
import com.pngelectoral.databinding.DialogDownloadPreliminaryBindingImpl;
import com.pngelectoral.databinding.DialogDuplicateVotersListBindingImpl;
import com.pngelectoral.databinding.DialogFilterScreenBindingImpl;
import com.pngelectoral.databinding.DialogForm11ImageBindingImpl;
import com.pngelectoral.databinding.DialogGenderVoterCountBindingImpl;
import com.pngelectoral.databinding.DialogGeoAndFilterSelectionBindingImpl;
import com.pngelectoral.databinding.DialogGeoSelectionBindingImpl;
import com.pngelectoral.databinding.DialogManualForm11NumberBindingImpl;
import com.pngelectoral.databinding.DialogPhotoVoterCountBindingImpl;
import com.pngelectoral.databinding.DialogQrcodeHelpBindingImpl;
import com.pngelectoral.databinding.DialogSpinnerBindingImpl;
import com.pngelectoral.databinding.DialogSyncSettingsPasswordBindingImpl;
import com.pngelectoral.databinding.DialogUpdateStatusBindingImpl;
import com.pngelectoral.databinding.DialogWardSelectionBindingImpl;
import com.pngelectoral.databinding.DialogYearPickerBindingImpl;
import com.pngelectoral.databinding.DownloadFileProgressBindingImpl;
import com.pngelectoral.databinding.ForgotPasswordBindingImpl;
import com.pngelectoral.databinding.GetDeviceIdBindingImpl;
import com.pngelectoral.databinding.ItemDialogSpinnerBindingImpl;
import com.pngelectoral.databinding.ItemFilterBindingImpl;
import com.pngelectoral.databinding.ItemGenderLessVoterBindingImpl;
import com.pngelectoral.databinding.ItemHomeBindingImpl;
import com.pngelectoral.databinding.ItemStatusDiscrepancyVoterBindingImpl;
import com.pngelectoral.databinding.ItemSyncFailedVoterBindingImpl;
import com.pngelectoral.databinding.ItemSyncVoterBindingImpl;
import com.pngelectoral.databinding.ItemSyncVoterNewBindingImpl;
import com.pngelectoral.databinding.ItemTransferInVoterBindingImpl;
import com.pngelectoral.databinding.ItemWardRollVoterBindingImpl;
import com.pngelectoral.databinding.ItemWardVoterBindingImpl;
import com.pngelectoral.databinding.LayoutLoaderCopyImageProgressBarBindingImpl;
import com.pngelectoral.databinding.LayoutLoaderDownloadProgressBarBindingImpl;
import com.pngelectoral.databinding.LayoutLoaderProgressBarBindingImpl;
import com.pngelectoral.databinding.UnSyncProgressBindingImpl;
import com.pngelectoral.databinding.UpdateCropImageViewBindingImpl;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final int LAYOUT_ACTIVITYDEVICEOPERATORREPORT = 1;

  private static final int LAYOUT_ACTIVITYDEVICEOPERATORREPORTSELECTION = 2;

  private static final int LAYOUT_ACTIVITYDOWNLOAD = 3;

  private static final int LAYOUT_ACTIVITYDPOGEOSTRUCTUREUPDATE = 4;

  private static final int LAYOUT_ACTIVITYEDITVOTERDETAILS = 5;

  private static final int LAYOUT_ACTIVITYFAILEDVOTEREDITDETAILS = 6;

  private static final int LAYOUT_ACTIVITYFIELDTALLYSUMMARYSHEETREPORT = 7;

  private static final int LAYOUT_ACTIVITYFIELDTALLYSUMMARYSHEETREPORTSELECTION = 8;

  private static final int LAYOUT_ACTIVITYFORM11IMAGECAPTURE = 9;

  private static final int LAYOUT_ACTIVITYGENDERLESSVOTERSLISTREPORT = 10;

  private static final int LAYOUT_ACTIVITYGENDERLESSVOTERSLISTSELECTION = 11;

  private static final int LAYOUT_ACTIVITYHOME = 12;

  private static final int LAYOUT_ACTIVITYIMAGEDISPLAY = 13;

  private static final int LAYOUT_ACTIVITYLOGIN = 14;

  private static final int LAYOUT_ACTIVITYNEWENROLMENT = 15;

  private static final int LAYOUT_ACTIVITYNEWENROLMENTFORM = 16;

  private static final int LAYOUT_ACTIVITYNEWREGWARDSELECTION = 17;

  private static final int LAYOUT_ACTIVITYOFFLINEROLLLOOKUPVOTERSLIST = 18;

  private static final int LAYOUT_ACTIVITYOFFLINETRANSFERINVOTERSLIST = 19;

  private static final int LAYOUT_ACTIVITYPROVISIONALENROLMENTFORM = 20;

  private static final int LAYOUT_ACTIVITYPROVISIONALREGWARDSELECTION = 21;

  private static final int LAYOUT_ACTIVITYREJECTEDVOTEREDITDETAILS = 22;

  private static final int LAYOUT_ACTIVITYREJECTEDVOTERLIST = 23;

  private static final int LAYOUT_ACTIVITYREVERSESYNC = 24;

  private static final int LAYOUT_ACTIVITYROLLLOOKUP = 25;

  private static final int LAYOUT_ACTIVITYROLLLOOKUPVOTERDETAILS = 26;

  private static final int LAYOUT_ACTIVITYROLLLOOKUPVOTERSLIST = 27;

  private static final int LAYOUT_ACTIVITYSETTINGS = 28;

  private static final int LAYOUT_ACTIVITYSPLASH = 29;

  private static final int LAYOUT_ACTIVITYSTATUSDISCREPANCYREPORT = 30;

  private static final int LAYOUT_ACTIVITYSTATUSDISCREPANCYREPORTSELECTION = 31;

  private static final int LAYOUT_ACTIVITYSYNCVOTERSLIST = 32;

  private static final int LAYOUT_ACTIVITYSYNCEDVOTERSLIST = 33;

  private static final int LAYOUT_ACTIVITYTRANSFERIN = 34;

  private static final int LAYOUT_ACTIVITYTRANSFERINSCAN = 35;

  private static final int LAYOUT_ACTIVITYTRANSFERINVOTERDETAILS = 36;

  private static final int LAYOUT_ACTIVITYTRANSFERINVOTERSLIST = 37;

  private static final int LAYOUT_ACTIVITYTRANSFERINWARDSELECTION = 38;

  private static final int LAYOUT_ACTIVITYUPDATEDVOTERSLIST = 39;

  private static final int LAYOUT_ACTIVITYVOTERDETAILS = 40;

  private static final int LAYOUT_ACTIVITYVOTERIMAGECAPTURE = 41;

  private static final int LAYOUT_ACTIVITYWARDROLLVOTERLIST = 42;

  private static final int LAYOUT_ACTIVITYWORKINGROLLUPDATESTATUS = 43;

  private static final int LAYOUT_BARCODECAPTURE = 44;

  private static final int LAYOUT_DIALOGAUDITSETTINGSCREEN = 45;

  private static final int LAYOUT_DIALOGDATEPICKER = 46;

  private static final int LAYOUT_DIALOGDOWNLOADPRELIMINARY = 47;

  private static final int LAYOUT_DIALOGDUPLICATEVOTERSLIST = 48;

  private static final int LAYOUT_DIALOGFILTERSCREEN = 49;

  private static final int LAYOUT_DIALOGFORM11IMAGE = 50;

  private static final int LAYOUT_DIALOGGENDERVOTERCOUNT = 51;

  private static final int LAYOUT_DIALOGGEOANDFILTERSELECTION = 52;

  private static final int LAYOUT_DIALOGGEOSELECTION = 53;

  private static final int LAYOUT_DIALOGMANUALFORM11NUMBER = 54;

  private static final int LAYOUT_DIALOGPHOTOVOTERCOUNT = 55;

  private static final int LAYOUT_DIALOGQRCODEHELP = 56;

  private static final int LAYOUT_DIALOGSPINNER = 57;

  private static final int LAYOUT_DIALOGSYNCSETTINGSPASSWORD = 58;

  private static final int LAYOUT_DIALOGUPDATESTATUS = 59;

  private static final int LAYOUT_DIALOGWARDSELECTION = 60;

  private static final int LAYOUT_DIALOGYEARPICKER = 61;

  private static final int LAYOUT_DOWNLOADFILEPROGRESS = 62;

  private static final int LAYOUT_FORGOTPASSWORD = 63;

  private static final int LAYOUT_GETDEVICEID = 64;

  private static final int LAYOUT_ITEMDIALOGSPINNER = 65;

  private static final int LAYOUT_ITEMFILTER = 66;

  private static final int LAYOUT_ITEMGENDERLESSVOTER = 67;

  private static final int LAYOUT_ITEMHOME = 68;

  private static final int LAYOUT_ITEMSTATUSDISCREPANCYVOTER = 69;

  private static final int LAYOUT_ITEMSYNCFAILEDVOTER = 70;

  private static final int LAYOUT_ITEMSYNCVOTER = 71;

  private static final int LAYOUT_ITEMSYNCVOTERNEW = 72;

  private static final int LAYOUT_ITEMTRANSFERINVOTER = 73;

  private static final int LAYOUT_ITEMWARDROLLVOTER = 74;

  private static final int LAYOUT_ITEMWARDVOTER = 75;

  private static final int LAYOUT_LAYOUTLOADERCOPYIMAGEPROGRESSBAR = 76;

  private static final int LAYOUT_LAYOUTLOADERDOWNLOADPROGRESSBAR = 77;

  private static final int LAYOUT_LAYOUTLOADERPROGRESSBAR = 78;

  private static final int LAYOUT_UNSYNCPROGRESS = 79;

  private static final int LAYOUT_UPDATECROPIMAGEVIEW = 80;

  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(80);

  static {
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_device_operator_report, LAYOUT_ACTIVITYDEVICEOPERATORREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_device_operator_report_selection, LAYOUT_ACTIVITYDEVICEOPERATORREPORTSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_download, LAYOUT_ACTIVITYDOWNLOAD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_dpo_geo_structure_update, LAYOUT_ACTIVITYDPOGEOSTRUCTUREUPDATE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_edit_voter_details, LAYOUT_ACTIVITYEDITVOTERDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_failed_voter_edit_details, LAYOUT_ACTIVITYFAILEDVOTEREDITDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_field_tally_summary_sheet_report, LAYOUT_ACTIVITYFIELDTALLYSUMMARYSHEETREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_field_tally_summary_sheet_report_selection, LAYOUT_ACTIVITYFIELDTALLYSUMMARYSHEETREPORTSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_form11_image_capture, LAYOUT_ACTIVITYFORM11IMAGECAPTURE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_gender_less_voters_list_report, LAYOUT_ACTIVITYGENDERLESSVOTERSLISTREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_gender_less_voters_list_selection, LAYOUT_ACTIVITYGENDERLESSVOTERSLISTSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_home, LAYOUT_ACTIVITYHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_image_display, LAYOUT_ACTIVITYIMAGEDISPLAY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_login, LAYOUT_ACTIVITYLOGIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_new_enrolment, LAYOUT_ACTIVITYNEWENROLMENT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_new_enrolment_form, LAYOUT_ACTIVITYNEWENROLMENTFORM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_new_reg_ward_selection, LAYOUT_ACTIVITYNEWREGWARDSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_offline_roll_look_up_voters_list, LAYOUT_ACTIVITYOFFLINEROLLLOOKUPVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_offline_transfer_in_voters_list, LAYOUT_ACTIVITYOFFLINETRANSFERINVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_provisional_enrolment_form, LAYOUT_ACTIVITYPROVISIONALENROLMENTFORM);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_provisional_reg_ward_selection, LAYOUT_ACTIVITYPROVISIONALREGWARDSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_rejected_voter_edit_details, LAYOUT_ACTIVITYREJECTEDVOTEREDITDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_rejected_voter_list, LAYOUT_ACTIVITYREJECTEDVOTERLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_reverse_sync, LAYOUT_ACTIVITYREVERSESYNC);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_roll_look_up, LAYOUT_ACTIVITYROLLLOOKUP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_roll_look_up_voter_details, LAYOUT_ACTIVITYROLLLOOKUPVOTERDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_roll_look_up_voters_list, LAYOUT_ACTIVITYROLLLOOKUPVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_settings, LAYOUT_ACTIVITYSETTINGS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_splash, LAYOUT_ACTIVITYSPLASH);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_status_discrepancy_report, LAYOUT_ACTIVITYSTATUSDISCREPANCYREPORT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_status_discrepancy_report_selection, LAYOUT_ACTIVITYSTATUSDISCREPANCYREPORTSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_sync_voters_list, LAYOUT_ACTIVITYSYNCVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_synced_voters_list, LAYOUT_ACTIVITYSYNCEDVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_transfer_in, LAYOUT_ACTIVITYTRANSFERIN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_transfer_in_scan, LAYOUT_ACTIVITYTRANSFERINSCAN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_transfer_in_voter_details, LAYOUT_ACTIVITYTRANSFERINVOTERDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_transfer_in_voters_list, LAYOUT_ACTIVITYTRANSFERINVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_transfer_in_ward_selection, LAYOUT_ACTIVITYTRANSFERINWARDSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_updated_voters_list, LAYOUT_ACTIVITYUPDATEDVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_voter_details, LAYOUT_ACTIVITYVOTERDETAILS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_voter_image_capture, LAYOUT_ACTIVITYVOTERIMAGECAPTURE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_ward_roll_voter_list, LAYOUT_ACTIVITYWARDROLLVOTERLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.activity_working_roll_update_status, LAYOUT_ACTIVITYWORKINGROLLUPDATESTATUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.barcode_capture, LAYOUT_BARCODECAPTURE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_audit_setting_screen, LAYOUT_DIALOGAUDITSETTINGSCREEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_date_picker, LAYOUT_DIALOGDATEPICKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_download_preliminary, LAYOUT_DIALOGDOWNLOADPRELIMINARY);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_duplicate_voters_list, LAYOUT_DIALOGDUPLICATEVOTERSLIST);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_filter_screen, LAYOUT_DIALOGFILTERSCREEN);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_form11_image, LAYOUT_DIALOGFORM11IMAGE);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_gender_voter_count, LAYOUT_DIALOGGENDERVOTERCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_geo_and_filter_selection, LAYOUT_DIALOGGEOANDFILTERSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_geo_selection, LAYOUT_DIALOGGEOSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_manual_form11_number, LAYOUT_DIALOGMANUALFORM11NUMBER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_photo_voter_count, LAYOUT_DIALOGPHOTOVOTERCOUNT);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_qrcode_help, LAYOUT_DIALOGQRCODEHELP);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_spinner, LAYOUT_DIALOGSPINNER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_sync_settings_password, LAYOUT_DIALOGSYNCSETTINGSPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_update_status, LAYOUT_DIALOGUPDATESTATUS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_ward_selection, LAYOUT_DIALOGWARDSELECTION);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.dialog_year_picker, LAYOUT_DIALOGYEARPICKER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.download_file_progress, LAYOUT_DOWNLOADFILEPROGRESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.forgot_password, LAYOUT_FORGOTPASSWORD);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.get_device_id, LAYOUT_GETDEVICEID);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_dialog_spinner, LAYOUT_ITEMDIALOGSPINNER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_filter, LAYOUT_ITEMFILTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_gender_less_voter, LAYOUT_ITEMGENDERLESSVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_home, LAYOUT_ITEMHOME);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_status_discrepancy_voter, LAYOUT_ITEMSTATUSDISCREPANCYVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_sync_failed_voter, LAYOUT_ITEMSYNCFAILEDVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_sync_voter, LAYOUT_ITEMSYNCVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_sync_voter_new, LAYOUT_ITEMSYNCVOTERNEW);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_transfer_in_voter, LAYOUT_ITEMTRANSFERINVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_ward_roll_voter, LAYOUT_ITEMWARDROLLVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.item_ward_voter, LAYOUT_ITEMWARDVOTER);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.layout_loader_copy_image_progress_bar, LAYOUT_LAYOUTLOADERCOPYIMAGEPROGRESSBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.layout_loader_download_progress_bar, LAYOUT_LAYOUTLOADERDOWNLOADPROGRESSBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.layout_loader_progress_bar, LAYOUT_LAYOUTLOADERPROGRESSBAR);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.un_sync_progress, LAYOUT_UNSYNCPROGRESS);
    INTERNAL_LAYOUT_ID_LOOKUP.put(com.pngelectoral.R.layout.update_crop_image_view, LAYOUT_UPDATECROPIMAGEVIEW);
  }

  private final ViewDataBinding internalGetViewDataBinding0(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_ACTIVITYDEVICEOPERATORREPORT: {
        if ("layout/activity_device_operator_report_0".equals(tag)) {
          return new ActivityDeviceOperatorReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_device_operator_report is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYDEVICEOPERATORREPORTSELECTION: {
        if ("layout/activity_device_operator_report_selection_0".equals(tag)) {
          return new ActivityDeviceOperatorReportSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_device_operator_report_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYDOWNLOAD: {
        if ("layout/activity_download_0".equals(tag)) {
          return new ActivityDownloadBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_download is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYDPOGEOSTRUCTUREUPDATE: {
        if ("layout/activity_dpo_geo_structure_update_0".equals(tag)) {
          return new ActivityDpoGeoStructureUpdateBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_dpo_geo_structure_update is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYEDITVOTERDETAILS: {
        if ("layout/activity_edit_voter_details_0".equals(tag)) {
          return new ActivityEditVoterDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_edit_voter_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFAILEDVOTEREDITDETAILS: {
        if ("layout/activity_failed_voter_edit_details_0".equals(tag)) {
          return new ActivityFailedVoterEditDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_failed_voter_edit_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFIELDTALLYSUMMARYSHEETREPORT: {
        if ("layout/activity_field_tally_summary_sheet_report_0".equals(tag)) {
          return new ActivityFieldTallySummarySheetReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_field_tally_summary_sheet_report is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFIELDTALLYSUMMARYSHEETREPORTSELECTION: {
        if ("layout/activity_field_tally_summary_sheet_report_selection_0".equals(tag)) {
          return new ActivityFieldTallySummarySheetReportSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_field_tally_summary_sheet_report_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYFORM11IMAGECAPTURE: {
        if ("layout/activity_form11_image_capture_0".equals(tag)) {
          return new ActivityForm11ImageCaptureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_form11_image_capture is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYGENDERLESSVOTERSLISTREPORT: {
        if ("layout/activity_gender_less_voters_list_report_0".equals(tag)) {
          return new ActivityGenderLessVotersListReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_gender_less_voters_list_report is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYGENDERLESSVOTERSLISTSELECTION: {
        if ("layout/activity_gender_less_voters_list_selection_0".equals(tag)) {
          return new ActivityGenderLessVotersListSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_gender_less_voters_list_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYHOME: {
        if ("layout/activity_home_0".equals(tag)) {
          return new ActivityHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYIMAGEDISPLAY: {
        if ("layout/activity_image_display_0".equals(tag)) {
          return new ActivityImageDisplayBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_image_display is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYLOGIN: {
        if ("layout/activity_login_0".equals(tag)) {
          return new ActivityLoginBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_login is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNEWENROLMENT: {
        if ("layout/activity_new_enrolment_0".equals(tag)) {
          return new ActivityNewEnrolmentBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_new_enrolment is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNEWENROLMENTFORM: {
        if ("layout/activity_new_enrolment_form_0".equals(tag)) {
          return new ActivityNewEnrolmentFormBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_new_enrolment_form is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYNEWREGWARDSELECTION: {
        if ("layout/activity_new_reg_ward_selection_0".equals(tag)) {
          return new ActivityNewRegWardSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_new_reg_ward_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYOFFLINEROLLLOOKUPVOTERSLIST: {
        if ("layout/activity_offline_roll_look_up_voters_list_0".equals(tag)) {
          return new ActivityOfflineRollLookUpVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_offline_roll_look_up_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYOFFLINETRANSFERINVOTERSLIST: {
        if ("layout/activity_offline_transfer_in_voters_list_0".equals(tag)) {
          return new ActivityOfflineTransferInVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_offline_transfer_in_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPROVISIONALENROLMENTFORM: {
        if ("layout/activity_provisional_enrolment_form_0".equals(tag)) {
          return new ActivityProvisionalEnrolmentFormBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_provisional_enrolment_form is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYPROVISIONALREGWARDSELECTION: {
        if ("layout/activity_provisional_reg_ward_selection_0".equals(tag)) {
          return new ActivityProvisionalRegWardSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_provisional_reg_ward_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYREJECTEDVOTEREDITDETAILS: {
        if ("layout/activity_rejected_voter_edit_details_0".equals(tag)) {
          return new ActivityRejectedVoterEditDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_rejected_voter_edit_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYREJECTEDVOTERLIST: {
        if ("layout/activity_rejected_voter_list_0".equals(tag)) {
          return new ActivityRejectedVoterListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_rejected_voter_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYREVERSESYNC: {
        if ("layout/activity_reverse_sync_0".equals(tag)) {
          return new ActivityReverseSyncBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_reverse_sync is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYROLLLOOKUP: {
        if ("layout/activity_roll_look_up_0".equals(tag)) {
          return new ActivityRollLookUpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_roll_look_up is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYROLLLOOKUPVOTERDETAILS: {
        if ("layout/activity_roll_look_up_voter_details_0".equals(tag)) {
          return new ActivityRollLookUpVoterDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_roll_look_up_voter_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYROLLLOOKUPVOTERSLIST: {
        if ("layout/activity_roll_look_up_voters_list_0".equals(tag)) {
          return new ActivityRollLookUpVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_roll_look_up_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSETTINGS: {
        if ("layout/activity_settings_0".equals(tag)) {
          return new ActivitySettingsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_settings is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSPLASH: {
        if ("layout/activity_splash_0".equals(tag)) {
          return new ActivitySplashBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_splash is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSTATUSDISCREPANCYREPORT: {
        if ("layout/activity_status_discrepancy_report_0".equals(tag)) {
          return new ActivityStatusDiscrepancyReportBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_status_discrepancy_report is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSTATUSDISCREPANCYREPORTSELECTION: {
        if ("layout/activity_status_discrepancy_report_selection_0".equals(tag)) {
          return new ActivityStatusDiscrepancyReportSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_status_discrepancy_report_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSYNCVOTERSLIST: {
        if ("layout/activity_sync_voters_list_0".equals(tag)) {
          return new ActivitySyncVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_sync_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYSYNCEDVOTERSLIST: {
        if ("layout/activity_synced_voters_list_0".equals(tag)) {
          return new ActivitySyncedVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_synced_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTRANSFERIN: {
        if ("layout/activity_transfer_in_0".equals(tag)) {
          return new ActivityTransferInBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_transfer_in is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTRANSFERINSCAN: {
        if ("layout/activity_transfer_in_scan_0".equals(tag)) {
          return new ActivityTransferInScanBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_transfer_in_scan is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTRANSFERINVOTERDETAILS: {
        if ("layout/activity_transfer_in_voter_details_0".equals(tag)) {
          return new ActivityTransferInVoterDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_transfer_in_voter_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTRANSFERINVOTERSLIST: {
        if ("layout/activity_transfer_in_voters_list_0".equals(tag)) {
          return new ActivityTransferInVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_transfer_in_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYTRANSFERINWARDSELECTION: {
        if ("layout/activity_transfer_in_ward_selection_0".equals(tag)) {
          return new ActivityTransferInWardSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_transfer_in_ward_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYUPDATEDVOTERSLIST: {
        if ("layout/activity_updated_voters_list_0".equals(tag)) {
          return new ActivityUpdatedVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_updated_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYVOTERDETAILS: {
        if ("layout/activity_voter_details_0".equals(tag)) {
          return new ActivityVoterDetailsBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_voter_details is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYVOTERIMAGECAPTURE: {
        if ("layout/activity_voter_image_capture_0".equals(tag)) {
          return new ActivityVoterImageCaptureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_voter_image_capture is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWARDROLLVOTERLIST: {
        if ("layout/activity_ward_roll_voter_list_0".equals(tag)) {
          return new ActivityWardRollVoterListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_ward_roll_voter_list is invalid. Received: " + tag);
      }
      case  LAYOUT_ACTIVITYWORKINGROLLUPDATESTATUS: {
        if ("layout/activity_working_roll_update_status_0".equals(tag)) {
          return new ActivityWorkingRollUpdateStatusBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for activity_working_roll_update_status is invalid. Received: " + tag);
      }
      case  LAYOUT_BARCODECAPTURE: {
        if ("layout/barcode_capture_0".equals(tag)) {
          return new BarcodeCaptureBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for barcode_capture is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGAUDITSETTINGSCREEN: {
        if ("layout/dialog_audit_setting_screen_0".equals(tag)) {
          return new DialogAuditSettingScreenBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_audit_setting_screen is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDATEPICKER: {
        if ("layout/dialog_date_picker_0".equals(tag)) {
          return new DialogDatePickerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_date_picker is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDOWNLOADPRELIMINARY: {
        if ("layout/dialog_download_preliminary_0".equals(tag)) {
          return new DialogDownloadPreliminaryBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_download_preliminary is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGDUPLICATEVOTERSLIST: {
        if ("layout/dialog_duplicate_voters_list_0".equals(tag)) {
          return new DialogDuplicateVotersListBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_duplicate_voters_list is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFILTERSCREEN: {
        if ("layout/dialog_filter_screen_0".equals(tag)) {
          return new DialogFilterScreenBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_filter_screen is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGFORM11IMAGE: {
        if ("layout/dialog_form11_image_0".equals(tag)) {
          return new DialogForm11ImageBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_form11_image is invalid. Received: " + tag);
      }
    }
    return null;
  }

  private final ViewDataBinding internalGetViewDataBinding1(DataBindingComponent component,
      View view, int internalId, Object tag) {
    switch(internalId) {
      case  LAYOUT_DIALOGGENDERVOTERCOUNT: {
        if ("layout/dialog_gender_voter_count_0".equals(tag)) {
          return new DialogGenderVoterCountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_gender_voter_count is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGGEOANDFILTERSELECTION: {
        if ("layout/dialog_geo_and_filter_selection_0".equals(tag)) {
          return new DialogGeoAndFilterSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_geo_and_filter_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGGEOSELECTION: {
        if ("layout/dialog_geo_selection_0".equals(tag)) {
          return new DialogGeoSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_geo_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGMANUALFORM11NUMBER: {
        if ("layout/dialog_manual_form11_number_0".equals(tag)) {
          return new DialogManualForm11NumberBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_manual_form11_number is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGPHOTOVOTERCOUNT: {
        if ("layout/dialog_photo_voter_count_0".equals(tag)) {
          return new DialogPhotoVoterCountBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_photo_voter_count is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGQRCODEHELP: {
        if ("layout/dialog_qrcode_help_0".equals(tag)) {
          return new DialogQrcodeHelpBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_qrcode_help is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSPINNER: {
        if ("layout/dialog_spinner_0".equals(tag)) {
          return new DialogSpinnerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_spinner is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGSYNCSETTINGSPASSWORD: {
        if ("layout/dialog_sync_settings_password_0".equals(tag)) {
          return new DialogSyncSettingsPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_sync_settings_password is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGUPDATESTATUS: {
        if ("layout/dialog_update_status_0".equals(tag)) {
          return new DialogUpdateStatusBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_update_status is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGWARDSELECTION: {
        if ("layout/dialog_ward_selection_0".equals(tag)) {
          return new DialogWardSelectionBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_ward_selection is invalid. Received: " + tag);
      }
      case  LAYOUT_DIALOGYEARPICKER: {
        if ("layout/dialog_year_picker_0".equals(tag)) {
          return new DialogYearPickerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for dialog_year_picker is invalid. Received: " + tag);
      }
      case  LAYOUT_DOWNLOADFILEPROGRESS: {
        if ("layout/download_file_progress_0".equals(tag)) {
          return new DownloadFileProgressBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for download_file_progress is invalid. Received: " + tag);
      }
      case  LAYOUT_FORGOTPASSWORD: {
        if ("layout/forgot_password_0".equals(tag)) {
          return new ForgotPasswordBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for forgot_password is invalid. Received: " + tag);
      }
      case  LAYOUT_GETDEVICEID: {
        if ("layout/get_device_id_0".equals(tag)) {
          return new GetDeviceIdBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for get_device_id is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMDIALOGSPINNER: {
        if ("layout/item_dialog_spinner_0".equals(tag)) {
          return new ItemDialogSpinnerBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_dialog_spinner is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMFILTER: {
        if ("layout/item_filter_0".equals(tag)) {
          return new ItemFilterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_filter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMGENDERLESSVOTER: {
        if ("layout/item_gender_less_voter_0".equals(tag)) {
          return new ItemGenderLessVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_gender_less_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMHOME: {
        if ("layout/item_home_0".equals(tag)) {
          return new ItemHomeBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_home is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSTATUSDISCREPANCYVOTER: {
        if ("layout/item_status_discrepancy_voter_0".equals(tag)) {
          return new ItemStatusDiscrepancyVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_status_discrepancy_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSYNCFAILEDVOTER: {
        if ("layout/item_sync_failed_voter_0".equals(tag)) {
          return new ItemSyncFailedVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_sync_failed_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSYNCVOTER: {
        if ("layout/item_sync_voter_0".equals(tag)) {
          return new ItemSyncVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_sync_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMSYNCVOTERNEW: {
        if ("layout/item_sync_voter_new_0".equals(tag)) {
          return new ItemSyncVoterNewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_sync_voter_new is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMTRANSFERINVOTER: {
        if ("layout/item_transfer_in_voter_0".equals(tag)) {
          return new ItemTransferInVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_transfer_in_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMWARDROLLVOTER: {
        if ("layout/item_ward_roll_voter_0".equals(tag)) {
          return new ItemWardRollVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_ward_roll_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_ITEMWARDVOTER: {
        if ("layout/item_ward_voter_0".equals(tag)) {
          return new ItemWardVoterBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for item_ward_voter is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTLOADERCOPYIMAGEPROGRESSBAR: {
        if ("layout/layout_loader_copy_image_progress_bar_0".equals(tag)) {
          return new LayoutLoaderCopyImageProgressBarBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_loader_copy_image_progress_bar is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTLOADERDOWNLOADPROGRESSBAR: {
        if ("layout/layout_loader_download_progress_bar_0".equals(tag)) {
          return new LayoutLoaderDownloadProgressBarBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_loader_download_progress_bar is invalid. Received: " + tag);
      }
      case  LAYOUT_LAYOUTLOADERPROGRESSBAR: {
        if ("layout/layout_loader_progress_bar_0".equals(tag)) {
          return new LayoutLoaderProgressBarBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for layout_loader_progress_bar is invalid. Received: " + tag);
      }
      case  LAYOUT_UNSYNCPROGRESS: {
        if ("layout/un_sync_progress_0".equals(tag)) {
          return new UnSyncProgressBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for un_sync_progress is invalid. Received: " + tag);
      }
      case  LAYOUT_UPDATECROPIMAGEVIEW: {
        if ("layout/update_crop_image_view_0".equals(tag)) {
          return new UpdateCropImageViewBindingImpl(component, view);
        }
        throw new IllegalArgumentException("The tag for update_crop_image_view is invalid. Received: " + tag);
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      // find which method will have it. -1 is necessary becausefirst id starts with 1;
      int methodIndex = (localizedLayoutId - 1) / 50;
      switch(methodIndex) {
        case 0: {
          return internalGetViewDataBinding0(component, view, localizedLayoutId, tag);
        }
        case 1: {
          return internalGetViewDataBinding1(component, view, localizedLayoutId, tag);
        }
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(2);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new com.master.basediproject.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(25);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "downloadViewModel");
      sKeys.put(2, "dpoGeoStructureUpdateViewModel");
      sKeys.put(3, "editVoterDetailsViewModel");
      sKeys.put(4, "form11ImageCaptureViewModel");
      sKeys.put(5, "homeViewModel");
      sKeys.put(6, "imageDisplayViewModel");
      sKeys.put(7, "loginViewModel");
      sKeys.put(8, "model");
      sKeys.put(9, "newEnrolmentFormViewModel");
      sKeys.put(10, "newEnrolmentViewModel");
      sKeys.put(11, "provisionalEnrolmentFormViewModel");
      sKeys.put(12, "provisionalEnrolmentViewModel");
      sKeys.put(13, "registrationViewModel");
      sKeys.put(14, "rejectedVotersListViewModel");
      sKeys.put(15, "settingsViewModel");
      sKeys.put(16, "splashViewModel");
      sKeys.put(17, "syncVotersListViewModel");
      sKeys.put(18, "transferInScanViewModel");
      sKeys.put(19, "transferInViewModel");
      sKeys.put(20, "transferInVoterDetailsViewModel");
      sKeys.put(21, "transferInVotersListModel");
      sKeys.put(22, "voterDetailsViewModel");
      sKeys.put(23, "voterImageCaptureViewModel");
      sKeys.put(24, "wardRollViewModel");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(80);

    static {
      sKeys.put("layout/activity_device_operator_report_0", com.pngelectoral.R.layout.activity_device_operator_report);
      sKeys.put("layout/activity_device_operator_report_selection_0", com.pngelectoral.R.layout.activity_device_operator_report_selection);
      sKeys.put("layout/activity_download_0", com.pngelectoral.R.layout.activity_download);
      sKeys.put("layout/activity_dpo_geo_structure_update_0", com.pngelectoral.R.layout.activity_dpo_geo_structure_update);
      sKeys.put("layout/activity_edit_voter_details_0", com.pngelectoral.R.layout.activity_edit_voter_details);
      sKeys.put("layout/activity_failed_voter_edit_details_0", com.pngelectoral.R.layout.activity_failed_voter_edit_details);
      sKeys.put("layout/activity_field_tally_summary_sheet_report_0", com.pngelectoral.R.layout.activity_field_tally_summary_sheet_report);
      sKeys.put("layout/activity_field_tally_summary_sheet_report_selection_0", com.pngelectoral.R.layout.activity_field_tally_summary_sheet_report_selection);
      sKeys.put("layout/activity_form11_image_capture_0", com.pngelectoral.R.layout.activity_form11_image_capture);
      sKeys.put("layout/activity_gender_less_voters_list_report_0", com.pngelectoral.R.layout.activity_gender_less_voters_list_report);
      sKeys.put("layout/activity_gender_less_voters_list_selection_0", com.pngelectoral.R.layout.activity_gender_less_voters_list_selection);
      sKeys.put("layout/activity_home_0", com.pngelectoral.R.layout.activity_home);
      sKeys.put("layout/activity_image_display_0", com.pngelectoral.R.layout.activity_image_display);
      sKeys.put("layout/activity_login_0", com.pngelectoral.R.layout.activity_login);
      sKeys.put("layout/activity_new_enrolment_0", com.pngelectoral.R.layout.activity_new_enrolment);
      sKeys.put("layout/activity_new_enrolment_form_0", com.pngelectoral.R.layout.activity_new_enrolment_form);
      sKeys.put("layout/activity_new_reg_ward_selection_0", com.pngelectoral.R.layout.activity_new_reg_ward_selection);
      sKeys.put("layout/activity_offline_roll_look_up_voters_list_0", com.pngelectoral.R.layout.activity_offline_roll_look_up_voters_list);
      sKeys.put("layout/activity_offline_transfer_in_voters_list_0", com.pngelectoral.R.layout.activity_offline_transfer_in_voters_list);
      sKeys.put("layout/activity_provisional_enrolment_form_0", com.pngelectoral.R.layout.activity_provisional_enrolment_form);
      sKeys.put("layout/activity_provisional_reg_ward_selection_0", com.pngelectoral.R.layout.activity_provisional_reg_ward_selection);
      sKeys.put("layout/activity_rejected_voter_edit_details_0", com.pngelectoral.R.layout.activity_rejected_voter_edit_details);
      sKeys.put("layout/activity_rejected_voter_list_0", com.pngelectoral.R.layout.activity_rejected_voter_list);
      sKeys.put("layout/activity_reverse_sync_0", com.pngelectoral.R.layout.activity_reverse_sync);
      sKeys.put("layout/activity_roll_look_up_0", com.pngelectoral.R.layout.activity_roll_look_up);
      sKeys.put("layout/activity_roll_look_up_voter_details_0", com.pngelectoral.R.layout.activity_roll_look_up_voter_details);
      sKeys.put("layout/activity_roll_look_up_voters_list_0", com.pngelectoral.R.layout.activity_roll_look_up_voters_list);
      sKeys.put("layout/activity_settings_0", com.pngelectoral.R.layout.activity_settings);
      sKeys.put("layout/activity_splash_0", com.pngelectoral.R.layout.activity_splash);
      sKeys.put("layout/activity_status_discrepancy_report_0", com.pngelectoral.R.layout.activity_status_discrepancy_report);
      sKeys.put("layout/activity_status_discrepancy_report_selection_0", com.pngelectoral.R.layout.activity_status_discrepancy_report_selection);
      sKeys.put("layout/activity_sync_voters_list_0", com.pngelectoral.R.layout.activity_sync_voters_list);
      sKeys.put("layout/activity_synced_voters_list_0", com.pngelectoral.R.layout.activity_synced_voters_list);
      sKeys.put("layout/activity_transfer_in_0", com.pngelectoral.R.layout.activity_transfer_in);
      sKeys.put("layout/activity_transfer_in_scan_0", com.pngelectoral.R.layout.activity_transfer_in_scan);
      sKeys.put("layout/activity_transfer_in_voter_details_0", com.pngelectoral.R.layout.activity_transfer_in_voter_details);
      sKeys.put("layout/activity_transfer_in_voters_list_0", com.pngelectoral.R.layout.activity_transfer_in_voters_list);
      sKeys.put("layout/activity_transfer_in_ward_selection_0", com.pngelectoral.R.layout.activity_transfer_in_ward_selection);
      sKeys.put("layout/activity_updated_voters_list_0", com.pngelectoral.R.layout.activity_updated_voters_list);
      sKeys.put("layout/activity_voter_details_0", com.pngelectoral.R.layout.activity_voter_details);
      sKeys.put("layout/activity_voter_image_capture_0", com.pngelectoral.R.layout.activity_voter_image_capture);
      sKeys.put("layout/activity_ward_roll_voter_list_0", com.pngelectoral.R.layout.activity_ward_roll_voter_list);
      sKeys.put("layout/activity_working_roll_update_status_0", com.pngelectoral.R.layout.activity_working_roll_update_status);
      sKeys.put("layout/barcode_capture_0", com.pngelectoral.R.layout.barcode_capture);
      sKeys.put("layout/dialog_audit_setting_screen_0", com.pngelectoral.R.layout.dialog_audit_setting_screen);
      sKeys.put("layout/dialog_date_picker_0", com.pngelectoral.R.layout.dialog_date_picker);
      sKeys.put("layout/dialog_download_preliminary_0", com.pngelectoral.R.layout.dialog_download_preliminary);
      sKeys.put("layout/dialog_duplicate_voters_list_0", com.pngelectoral.R.layout.dialog_duplicate_voters_list);
      sKeys.put("layout/dialog_filter_screen_0", com.pngelectoral.R.layout.dialog_filter_screen);
      sKeys.put("layout/dialog_form11_image_0", com.pngelectoral.R.layout.dialog_form11_image);
      sKeys.put("layout/dialog_gender_voter_count_0", com.pngelectoral.R.layout.dialog_gender_voter_count);
      sKeys.put("layout/dialog_geo_and_filter_selection_0", com.pngelectoral.R.layout.dialog_geo_and_filter_selection);
      sKeys.put("layout/dialog_geo_selection_0", com.pngelectoral.R.layout.dialog_geo_selection);
      sKeys.put("layout/dialog_manual_form11_number_0", com.pngelectoral.R.layout.dialog_manual_form11_number);
      sKeys.put("layout/dialog_photo_voter_count_0", com.pngelectoral.R.layout.dialog_photo_voter_count);
      sKeys.put("layout/dialog_qrcode_help_0", com.pngelectoral.R.layout.dialog_qrcode_help);
      sKeys.put("layout/dialog_spinner_0", com.pngelectoral.R.layout.dialog_spinner);
      sKeys.put("layout/dialog_sync_settings_password_0", com.pngelectoral.R.layout.dialog_sync_settings_password);
      sKeys.put("layout/dialog_update_status_0", com.pngelectoral.R.layout.dialog_update_status);
      sKeys.put("layout/dialog_ward_selection_0", com.pngelectoral.R.layout.dialog_ward_selection);
      sKeys.put("layout/dialog_year_picker_0", com.pngelectoral.R.layout.dialog_year_picker);
      sKeys.put("layout/download_file_progress_0", com.pngelectoral.R.layout.download_file_progress);
      sKeys.put("layout/forgot_password_0", com.pngelectoral.R.layout.forgot_password);
      sKeys.put("layout/get_device_id_0", com.pngelectoral.R.layout.get_device_id);
      sKeys.put("layout/item_dialog_spinner_0", com.pngelectoral.R.layout.item_dialog_spinner);
      sKeys.put("layout/item_filter_0", com.pngelectoral.R.layout.item_filter);
      sKeys.put("layout/item_gender_less_voter_0", com.pngelectoral.R.layout.item_gender_less_voter);
      sKeys.put("layout/item_home_0", com.pngelectoral.R.layout.item_home);
      sKeys.put("layout/item_status_discrepancy_voter_0", com.pngelectoral.R.layout.item_status_discrepancy_voter);
      sKeys.put("layout/item_sync_failed_voter_0", com.pngelectoral.R.layout.item_sync_failed_voter);
      sKeys.put("layout/item_sync_voter_0", com.pngelectoral.R.layout.item_sync_voter);
      sKeys.put("layout/item_sync_voter_new_0", com.pngelectoral.R.layout.item_sync_voter_new);
      sKeys.put("layout/item_transfer_in_voter_0", com.pngelectoral.R.layout.item_transfer_in_voter);
      sKeys.put("layout/item_ward_roll_voter_0", com.pngelectoral.R.layout.item_ward_roll_voter);
      sKeys.put("layout/item_ward_voter_0", com.pngelectoral.R.layout.item_ward_voter);
      sKeys.put("layout/layout_loader_copy_image_progress_bar_0", com.pngelectoral.R.layout.layout_loader_copy_image_progress_bar);
      sKeys.put("layout/layout_loader_download_progress_bar_0", com.pngelectoral.R.layout.layout_loader_download_progress_bar);
      sKeys.put("layout/layout_loader_progress_bar_0", com.pngelectoral.R.layout.layout_loader_progress_bar);
      sKeys.put("layout/un_sync_progress_0", com.pngelectoral.R.layout.un_sync_progress);
      sKeys.put("layout/update_crop_image_view_0", com.pngelectoral.R.layout.update_crop_image_view);
    }
  }
}
