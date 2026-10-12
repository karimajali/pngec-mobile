interface ApiConstants {
    companion object {


        var EMAIL = "UserName"
        var PASSWORD = "Password"
        var DEVICENUMBER = "DeviceNumber"
        var REGIONID = "RegionId"
        var PROVINCEID = "ProvinceId"
        var DISTRICTID = "DistrictId"
        var MKADISTRICTID = "MKADistrictId"
        var LLGID = "LLGId"
        var MKALLGID = "MKALLGId"
        var WARDID = "WardId"
        var MKAWARDID = "MKAWardId"
        var LOCATIONID = "LocationId"
        var MKALOCATIONID = "MKALocationId"
        var PAGENO = "PageNo"
        var PAGESIZE = "PageSize"
        var UserId = "UserId"
        var REQUESTTYPE = "RequestType"

        var START_DATE = "StartDate"
        var END_DATE = "EndDate"
        var DPO = "DPO"

        var DPO_LOGIN = 10
        var DPO_FORGOT_PASSWORD = 11
        var DPO_DOWNLOAD_VOTERS_LIST = 12
        var DPO_GET_GEO_LIST = 13
        var DPO_GET_OCCUPATION_LIST = 14
        var DPO_TRANSFER_IN_VOTERS_LIST = 15
        var DPO_SYNC_VOTERS_LIST = 16
        var DPO_VALIDATE_ENROLMENT_FORM_NUMBER = 17
        var FORM11_IMAGE_CAPTURE = 18
        var VOTER_IMAGE_CAPTURE = 19
        var REQ_CAMERA = 20
        var EDIT_VOTER_DETAILS = 21
        var DPO_DOWNLOAD_LOCATIONS_LIST = 22
        var DPO_GET_LOCATION_LIST = 23
        var DPO_REJECTED_VOTERS_LIST = 24
        var DPO_LOG_OUT = 25
        var DPO_GET_ACTIVE_PROVINCE_LIST = 26
        var DPO_GEO_STRUCTURE_UPDATE = 28
        var DPO_UPDATED_VOTERS_LIST = 29
        var DPO_DEVICE_OPERATOR_REPORT = 30
        var VOTER_STATUS_DOB_DISCREPANCY_REPORT = 31
        var DOWNLOAD_PRELIMINARY_VOTERS_LIST = 32
        var GENERAL_SETTINGS = 33
        var DPO_CHECK_SYNC_VOTERS_LIST = 34
        var DPO_GET_MKA_GEO_LIST = 35
        var DPO_GET_MKA_CLAN_LIST = 36
        var DPO_GET_GEO_CLAN_LIST = 37

        var VOTER_DETAILS = "voter_details"
        var VOTER_IMAGE = "voter_image"
        var ENROLMENT_FORM_NUMBER = "EnrolmentFormNumber"
        var ENROLMENT_FORM_TYPE = "EnrolmentFormType"
        var SYNC_DATA_OPTION = "sync_data_option"

        // Shared Pref Constants
        var DPO_LOGGED_IN_USER_DATA = "dpo_logged_in_user_data"
        var DPO_NAME = "dpo_name"
        var DPO_VOTERS_DATA_DOWNLOADED = "dpo_voters_data_downloaded"
        var DPO_SETUP_COMPLETED = "dpo_setup_completed"
        var LATITUDE = "latitude"
        var LONGITUDE = "longitude"
        var ROLE = "role"
        var ROLE_ID = "role_id"
        var GEO_LIST_DOWNLOADED = "geo_list_downloaded"
        var LOCATION_DATA_DOWNLOADED = "location_list_downloaded"
        var GEO_LIST_DATA_DOWNLOADED = "geo_list_data_downloaded"
        var CLAN_LIST_DATA_DOWNLOADED = "clan_list_data_downloaded"
        var LOCATION_PAGE_NO = "LocationPageNo"
        var LOGGED_IN_DPO_EMAIL = "logged_in_dpo_email"
        var LOGGED_IN_DPO_PASSWORD = "logged_in_dpo_password"
        var OCCUPATION_DOWNLOADED = "occupation_downloaded"
        var DOWNLOADED_PERCENTAGE = "downloaded_percentage"
        var DOWNLOAD_IN_PROGRESS = "downloaded_in_progress"

        var DPO_GEO_REGION_ID = "dpo_geo_region_id"
        var DPO_GEO_PROVINCE_ID = "dpo_geo_province_id"
        var DPO_GEO_DISTRICT_ID = "dpo_geo_district_id"
        var DPO_GEO_LLG_ID = "dpo_geo_llg_id"
        var DPO_GEO_WARD_ID = "dpo_geo_ward_id"
        var DPO_GEO_LOCATION_ID = "dpo_geo_location_id"

        var DPO_GEO_REGION = "dpo_geo_region"
        var DPO_GEO_PROVINCE = "dpo_geo_province"
        var DPO_GEO_DISTRICT = "dpo_geo_district"
        var DPO_GEO_LLG = "dpo_geo_llg"
        var DPO_GEO_WARD = "dpo_geo_ward"
        var DPO_GEO_LOCATION = "dpo_geo_location"

        // Voter Sync API PARAMS
        var ELECTOR_ID = "electorId"
        var OLD_ELECTOR_ID = "oldElectorID"
        var LOCATION_ID = "locationId"
        var OLD_LOCATION_ID = "OldLocationId"
        var LOCATION_NAME = "locationName"
        var WARD_NAME = "wardName"
        var LLG_ID = "llgId"
        var LLG_NAME = "llgName"
        var DISTRICT_ID = "districtId"
        var PROVINCE_ID = "provinceId"
        var REGION_ID = "regionId"
        var LNAME = "lName"
        var FNAME = "fName"
        var VOTER_NAME = "voterName"
        var GENDER = "gender"
        var DOB = "dob"
        var IS_YOB = "isYOB"
        var IS_PWD = "isPWD"
        var STATUS = "status"

        var STOP_SERVICE = "stop_service"
        var ALL_DPO_DATA_COMPLETED_BROADCAST = "all_dpo_data_downloaded"
        var FILE_DOWNLOADED = "file_downloaded"
        var FILE_DOWNLOAD_PROGRESS = "file_download_progress"
        var FROM_REJECT_LIST = "from_reject_list"
        var UN_SYNC_PROGRESS = "un_sync_progress"

        var REGION = "Region"
        var PROVINCE = "Province"
        var DISTRICT = "District"
        var LLG = "LLG"

        var VOTER_STATUS_UPDATE = 1000

        //Transfer In Selection Values

        var TSV_REGION_ID = 0
        var TSV_REGION_NAME = ""

        var TSV_PROVINCE_ID = 0
        var TSV_PROVINCE_NAME = ""

        var TSV_DISTRICT_ID = 0
        var TSV_DISTRICT_NAME = ""

        var TSV_LLG_ID = 0
        var TSV_LLG_NAME = ""

        var TSV_WARD_ID = 0
        var TSV_WARD_NAME = ""
        var TSV_WARD_NUMBER = 0
        var TSV_WARD_DISPLAY_NAME = ""


        var TSV_KEY_WORD_FIRST_NAME = ""
        var TSV_KEY_WORD_LAST_NAME = ""
        var TSV_GENDER = ""
        var TSV_VOTER_TRANSFERRED = false

        var HOME_FILTER_CLAN = ""
        var HOME_FILTER_CLAN_ID = 0
        var HOME_FILTER_LOCATION = ""
        var HOME_FILTER_LOCATION_ID = 0
        var HOME_FILTER_MKA_LOCATION = ""
        var HOME_FILTER_MKA_LOCATION_ID = 0

        var FACE_DETECTED_CALL_BACK = "face_detected_call_back"
        var FACE_DETECTED = "face_detected"
        var FACE_DETECTED_OBJECT = "face_detected_object"
        var IMAGE_CAPTURED = "image_captured"
        var IMAGE_PATH = "image_path"

        var SINGLE_PAGE_DATA_COMPLETED = "single_page_data_completed"
        var INTERNET_CONNECTION_LOST = "internet_connection_lost"

        var REPORT_STAGING = "report_staging"

        var MIN_YEAR_FOR_ENROLMENT = 1910
        var MIN_MONTH_FOR_ENROLMENT = 0
        var MIN_DAY_FOR_ENROLMENT = 1

        var ISSUE_OF_WRIT_DATE_VALUE = "28-04-2022"
        var LAST_DATE_OF_ENROLMENT_DEFAULT = "28-04-2004"
        var LAST_DATE_OF_ENROLMENT_DEFAULT_MKA = "09-11-2005"
        var PROVISIONAL_CUT_OFF_DATE_VALUE = "28-04-2003"
        var PROVISIONAL_CUT_OFF_DATE_VALUE_MKA = "09-11-2004"

        var PROVISIONAL_CUT_OFF_DATE = "PROVISIONAL_CUT_OFF_DATE"
        var ISSUE_OF_WRIT_DATE = "ISSUE_OF_WRIT_DATE"

        val ALTERNATE_ROW_COLORS = intArrayOf(0xFFFFFFFF.toInt(), 0x40F1F1F1)

        var DPO_WORKING_ROLL_LLG_ID = "dpo_working_roll_llg_id"
        var DPO_WORKING_ROLL_WARD_ID = "dpo_working_roll_ward_id"
        var DPO_WORKING_ROLL_LOCATION_ID = "dpo_working_roll_location_id"
        var DPO_WORKING_ROLL_MKA_LLG_ID = "DPO_WORKING_ROLL_MKA_LLG_ID"
        var DPO_WORKING_ROLL_MKA_WARD_ID = "DPO_WORKING_ROLL_MKA_WARD_ID"
        var DPO_WORKING_ROLL_MKA_LOCATION_ID = "DPO_WORKING_ROLL_MKA_LOCATION_ID"

        var DPO_WORKING_ROLL_LLG = "dpo_working_roll_llg"
        var DPO_WORKING_ROLL_WARD = "dpo_working_roll_ward"
        var DPO_WORKING_ROLL_LOCATION = "dpo_working_roll_location"
        var DPO_WORKING_ROLL_MKA_LLG = "DPO_WORKING_ROLL_MKA_LLG"
        var DPO_WORKING_ROLL_MKA_WARD = "DPO_WORKING_ROLL_MKA_WARD"
        var DPO_WORKING_ROLL_MKA_LOCATION = "DPO_WORKING_ROLL_MKA_LOCATION"

        var PRELIMINARY_PAGENO = "Preliminary_PageNo"
        var PRELIMINARY_DOWNLOADED_PERCENTAGE = "Preliminary_downloaded_percentage"
        var PRELIMINARY_DOWNLOAD_IN_PROGRESS = "Preliminary_downloaded_in_progress"

        var ALL_PRELIMINARY_DATA_COMPLETED_BROADCAST = "all_preliminary_data_downloaded"
        var PRELIMINARY_SINGLE_PAGE_DATA_COMPLETED = "preliminary_single_page_data_completed"

        var PRELIMINARY_ROLL_LLG_ID = "preliminary_roll_llg_id"
        var PRELIMINARY_ROLL_WARD_ID = "preliminary_roll_ward_id"
        var PRELIMINARY_ROLL_LOCATION_ID = "preliminary_roll_location_id"

        var PRELIMINARY_ROLL_LLG_NAME = "preliminary_roll_llg_name"
        var PRELIMINARY_ROLL_WARD_NAME = "preliminary_roll_ward_name"
        var PRELIMINARY_ROLL_LOCATION_NAME = "preliminary_roll_location_name"

        var FILTER_WARD_ROLL_LAST_NAME = "filter_ward_roll_last_name"
        var FILTER_WARD_ROLL_FIRST_NAME = "filter_ward_roll_first_name"
        var FILTER_WARD_ROLL_ELECTOR_ID = "filter_ward_roll_elector_id"
        var FILTER_WARD_ROLL_GENDER = "filter_ward_roll_gender"

        var GEO_STRUCTURE_DOWNLOAD_IN_PROGRESS = "Geostructure_downloaded_in_progress"
        var GEO_STRUCTURE_PAGENO = "Geostructure_PageNo"
        var GEO_STRUCTURE_DOWNLOADED_PERCENTAGE = "Geostructure_downloaded_percentage"

        var GEO_STRUCTURE_DATA_COMPLETED_BROADCAST = "Geostructure_data_downloaded"
        var GEO_STRUCTURE_SINGLE_PAGE_DATA_COMPLETED = "Geostructure_single_page_data_completed"

        var FAILED_FORM11_NUMBER = 99

        var OCCUPATION_VERSION = "occupationVersion"
        var GEO_VERSION = "geoVersion"
        var DC_PASSWORD = "dcPassword"
        var DECRYPT_KEY = "hbpngec2022dcpasswordencrypt"
        var DC_PASSWORD_DEFAULT = "pngec2022"


        var ZipFilePath = "zip_file_path"


        //Different User Role
        var ROLE_DPO = "role_dpo"
        var ROLE_MKA = "role_mka"

        //Working Roll Filter Type
        var FILTER_TYPE = "filter_type"
        var PNGEC_GEO_STRUCTURE = "pngec_geo_structure"
        var MKA_GEO_STRUCTURE = "mka_geo_structure"
    }
}