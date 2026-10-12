package com.pngelectoral.ui.activity

import ApiConstants
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.InputType
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityRollLookUpVoterDetailsBinding
import com.pngelectoral.ui.model.*
import com.pngelectoral.ui.viewModel.VoterDetailsViewModel
import com.pngelectoral.utility.CommonMethods
import com.pngelectoral.utility.CommonMethods.getDisplayDate
import com.pngelectoral.utility.CommonMethods.getDisplayDateAndTime
import com.pngelectoral.utility.CommonMethods.getVoterDetails
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class RollLookUpVoterDetailsActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityRollLookUpVoterDetailsBinding? = null
    private var voterDetailsViewModel: VoterDetailsViewModel? = null
    var voterDetailsResponse: VotersList = VotersList()
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    var wardName = ""
    var duplicateId: Long = 0

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_roll_look_up_voter_details
        )
        voterDetailsViewModel =
            ViewModelProvider(this).get(VoterDetailsViewModel::class.java)
        binding?.voterDetailsViewModel = voterDetailsViewModel

        job = Job()

        dataBaseInstance =
            DatabaseClient.getInstance(this).appDatabase

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        voterDetailsResponse = intent.getSerializableExtra(ApiConstants.VOTER_DETAILS) as VotersList
        binding?.tvVoterName?.text = CommonMethods.getVoterName(voterDetailsResponse)


        setFooterData()
        setOnClickListeners()
        setVoterData(voterDetailsResponse)
    }

    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }


    private fun setFooterData() {

        val dpoName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_NAME,
            ""
        )
        val version = packageManager.getPackageInfo(packageName, 0).versionName

        val districtName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_GEO_DISTRICT,
            ""
        )
        val llgName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_WORKING_ROLL_LLG,
            securedSharedPreferenceWrapper.getString(
                ApiConstants.DPO_GEO_LLG,
                ""
            )
        )
        val wardName = securedSharedPreferenceWrapper.getString(
            ApiConstants.DPO_WORKING_ROLL_WARD,
            securedSharedPreferenceWrapper.getString(
                ApiConstants.DPO_GEO_WARD,
                ""
            )
        )
        val tvBottomDataTitle: String = if (wardName.isEmpty())
            "Version: $version | District: $districtName | DPO: $dpoName"
        else {
            val wardNameDisplay = "$districtName/$llgName/$wardName"
            "Version: $version | Ward: $wardNameDisplay | DPO: $dpoName"
        }
        binding?.tvBottomData?.text = tvBottomDataTitle
    }

    private fun setVoterData(voterDetailsResponse: VotersList) {

        if (voterDetailsResponse.electorProfileImageUrl != null
            && voterDetailsResponse.electorProfileImageUrl.isNotEmpty()
        ) {
            Glide.with(this)
                .load(voterDetailsResponse.electorProfileImageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: com.bumptech.glide.request.target.Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                }
                )
                .into(binding?.ivVoterImage!!)
        } else {
            binding?.ivVoterImage?.setImageResource(R.drawable.e_user)
        }

        binding?.etFamilyNumber?.setText(getVoterDetails(voterDetailsResponse.familyNumber)!!)
        binding?.etFamilyName?.setText(getVoterDetails(voterDetailsResponse.familyName)!!)
        binding?.etFamilyOrderNo?.setText(getVoterDetails(voterDetailsResponse.familySortOrder)!!)
        binding?.etLastName?.setText(getVoterDetails(voterDetailsResponse.lName)!!)
        binding?.etGivenName?.setText(getVoterDetails(voterDetailsResponse.fName)!!)

        binding?.etGivenName?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        binding?.etLastName?.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES

        binding?.etNationalIDCardNumber?.setText(getVoterDetails(voterDetailsResponse.natIDRecordNo)!!)
        binding?.etDateOfBirth?.setText(getDisplayDate(getVoterDetails(voterDetailsResponse.dob)!!))
        binding?.etPlaceOfBirth?.setText(getVoterDetails(voterDetailsResponse.placeOfBirth)!!)
        if (getVoterDetails(voterDetailsResponse.provinceOfBirth)!!.isEmpty()) {
            if (voterDetailsResponse.provOfBirthFK != 0)
                getVoterProvinceOfBirthName()
        } else {
            binding?.etProvinceOfBirth?.setText(getVoterDetails(voterDetailsResponse.provinceOfBirth)!!)
        }


        if (getVoterDetails(voterDetailsResponse.gender)!! == "F")
            binding?.etGender?.setText("Female")
        else if (getVoterDetails(voterDetailsResponse.gender)!! == "M")
            binding?.etGender?.setText("Male")
        else
            binding?.etGender?.setText("")

        binding?.etOccupation?.setText(getVoterDetails(voterDetailsResponse.occupation)!!)
        if (getVoterDetails(voterDetailsResponse.occupation)!!.isEmpty()) {
            if (voterDetailsResponse.occupationFK != null && voterDetailsResponse.occupationFK != 0)
                getOccupation(voterDetailsResponse.occupationFK)
            else {
                binding?.etOccupation?.text = "Not Specified"
            }

        } else
            binding?.etOccupation?.text = getVoterDetails(voterDetailsResponse.occupation)!!

        if (voterDetailsResponse.isPWD)
            binding?.etPersonWithDisability?.setText("Yes")
        else
            binding?.etPersonWithDisability?.setText("No")

        binding?.etReligion?.setText(getVoterDetails(voterDetailsResponse.religion)!!)

        binding?.etFathersFirstName?.setText(getVoterDetails(voterDetailsResponse.fatherFName)!!)
        binding?.etFathersLastName?.setText(getVoterDetails(voterDetailsResponse.fatherLName)!!)
        binding?.etMothersFirstName?.setText(getVoterDetails(voterDetailsResponse.motherFName)!!)
        binding?.etMothersLastName?.setText(getVoterDetails(voterDetailsResponse.motherLName)!!)

        // Place of residence

        binding?.etWardNoAndName?.setText(getVoterDetails(voterDetailsResponse.wardName)!!)
        wardName = getVoterDetails(voterDetailsResponse.wardName)!!
        if (voterDetailsResponse.wardId != 0)
            getVoterWardName()


        if (voterDetailsResponse.provinceName.isNullOrEmpty()) {
            if (voterDetailsResponse.provinceId != 0)
                getVoterProvinceName()
        } else {
            binding?.etProvince?.setText(getVoterDetails(voterDetailsResponse.provinceName)!!)
        }

        if (voterDetailsResponse.districtName.isNullOrEmpty()) {
            if (voterDetailsResponse.districtId != 0)
                getVoterDistrictName()
        } else {
            binding?.etOpenElectorate?.setText(getVoterDetails(voterDetailsResponse.districtName)!!)
        }

        if (voterDetailsResponse.llgName.isNullOrEmpty()) {
            if (voterDetailsResponse.llgId != 0)
                getVoterLLGName()
        } else {
            binding?.etLLG?.setText(getVoterDetails(voterDetailsResponse.llgName)!!)
        }


        if (voterDetailsResponse.locationName.isNullOrEmpty()) {
            if (voterDetailsResponse.locationId != 0)
                getVoterLocationName()
        } else {
            binding?.etLocationOrVillage?.setText(
                getVoterDetails(
                    voterDetailsResponse.locationName
                )!!
            )
        }

        // Rural

        binding?.etClan?.setText(getVoterDetails(voterDetailsResponse.clanName)!!)
        binding?.etSubClan?.setText(getVoterDetails(voterDetailsResponse.subClan)!!)
        binding?.etRestHouse?.setText(getVoterDetails(voterDetailsResponse.restHouse)!!)

        //Urban

        binding?.etSectionNo?.setText(getVoterDetails(voterDetailsResponse.sectionNo)!!)
        binding?.etLotNo?.setText(getVoterDetails(voterDetailsResponse.lotNo)!!)
        binding?.etStreetName?.setText(getVoterDetails(voterDetailsResponse.streetName)!!)
        binding?.etSuburb?.setText(getVoterDetails(voterDetailsResponse.suburbTownSettlement)!!)

        //Status

        binding?.etStatus?.text = getVoterDetails(voterDetailsResponse.status)!!
        binding?.etComment?.setText(getVoterDetails(voterDetailsResponse.comment)!!)
//        binding?.etGPSLocation?.setText(getVoterDetails(voterDetailsResponse.locationName)!!) // Need to replace with GPS Location
        binding?.etDateAndTime?.setText(getDisplayDateAndTime(getVoterDetails(voterDetailsResponse.posted)!!))
        binding?.etSubmittedBy?.setText(getVoterDetails(voterDetailsResponse.dpo)!!)

    }

    private fun getOccupation(occupationFK: Int) {
        launch(Dispatchers.Main) {
            val result: OccupationList? = withContext(Dispatchers.IO) {
                dataBaseInstance.occupationListDao()?.getOccupation(occupationFK)
            }
            binding?.etOccupation?.text = result!!.occupationName
        }
    }

    // Coroutine functions to perform DB Operations

    private fun getVoterProvinceName() {
        launch(Dispatchers.Main) {
            val result: ProvinceList? = withContext(Dispatchers.IO) {
                fetchVoterProvinceNameFromDB()
            }
            binding?.etProvince?.setText(result!!.provinceName)
        }
    }

    private fun fetchVoterProvinceNameFromDB(): ProvinceList? {
        return dataBaseInstance.provinceListDao()
            ?.getDPOProvince(voterDetailsResponse.provinceId)
    }

    private fun getVoterProvinceOfBirthName() {
        launch(Dispatchers.Main) {
            val result: ProvinceList? = withContext(Dispatchers.IO) {
                fetchVoterProvinceOfBirthNameFromDB()
            }
            binding?.etProvinceOfBirth?.setText(result!!.provinceName)
        }
    }

    private fun fetchVoterProvinceOfBirthNameFromDB(): ProvinceList? {
        return dataBaseInstance.provinceListDao()
            ?.getDPOProvince(voterDetailsResponse.provOfBirthFK)
    }


    private fun getVoterDistrictName() {
        launch(Dispatchers.Main) {
            val result: DistrictList? = withContext(Dispatchers.IO) {
                fetchVoterDistrictNameFromDB()
            }
            binding?.etOpenElectorate?.setText(result!!.districtName)
        }
    }

    private fun fetchVoterDistrictNameFromDB(): DistrictList? {
        return dataBaseInstance.districtListDao()
            ?.getDPODistrict(voterDetailsResponse.districtId)
    }

    private fun getVoterLLGName() {
        launch(Dispatchers.Main) {
            val result: LLGList? = withContext(Dispatchers.IO) {
                fetchVoterLLGFromDB()
            }
            binding?.etLLG?.setText(result!!.llgName)
        }
    }

    private fun fetchVoterLLGFromDB(): LLGList? {
        return dataBaseInstance.llgListDao()?.getDPOLLG(voterDetailsResponse.llgId)
    }

    private fun getVoterWardName() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                fetchVoterWardFromDB()
            }
            binding?.etWardNoAndName?.setText(result!!.wardNumber.toString() + "&" + result.wardName)
            wardName = result!!.wardName
        }
    }

    private fun fetchVoterWardFromDB(): WardList? {
        return dataBaseInstance.wardListDao()?.getDPOWard(voterDetailsResponse.wardId)
    }

    private fun getVoterLocationName() {
        launch(Dispatchers.Main) {
            val result: LocationList? = withContext(Dispatchers.IO) {
                fetchVoterLocationFromDB()
            }
            if (result != null)
                binding?.etLocationOrVillage?.setText(result.locationName)
        }
    }

    private fun fetchVoterLocationFromDB(): LocationList? {
        return dataBaseInstance.locationListDao()
            ?.getDPOLocation(voterDetailsResponse.locationId)
    }


    private fun setOnClickListeners() {

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }


    }


}
