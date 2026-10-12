package com.pngelectoral.ui.activity

import ApiConstants
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.pngelectoral.R
import com.pngelectoral.base.BaseActivity
import com.pngelectoral.data.db.AppDatabase
import com.pngelectoral.data.db.DatabaseClient
import com.pngelectoral.databinding.ActivityReverseSyncBinding
import com.pngelectoral.ui.model.DPOUserData
import com.pngelectoral.ui.model.WardList
import com.pngelectoral.ui.viewModel.SyncVotersListViewModel
import kotlinx.coroutines.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.okButton
import kotlin.coroutines.CoroutineContext

class ReverseSyncActivity : BaseActivity(), CoroutineScope {

    private var binding: ActivityReverseSyncBinding? = null
    private var syncVotersListViewModel: SyncVotersListViewModel? = null
    lateinit var loggedInDPOUserData: DPOUserData
    lateinit var dataBaseInstance: AppDatabase
    var districtId = 0
    var llgId = 0
    var wardId = 0
    var locationId = 0
    var districtName = ""
    var llgName = ""
    var wardName = ""
    private var wardNoAndName = ""
    var wardNumber = 0
    var locationName = ""

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_reverse_sync
        )
        syncVotersListViewModel =
            ViewModelProvider(this).get(SyncVotersListViewModel::class.java)
        binding?.syncVotersListViewModel = syncVotersListViewModel

        job = Job()

        loggedInDPOUserData = securedSharedPreferenceWrapper.getObject(
            ApiConstants.DPO_LOGGED_IN_USER_DATA,
            DPOUserData::class.java
        )
        dataBaseInstance = DatabaseClient.getInstance(this).appDatabase
        districtId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_GEO_DISTRICT_ID, 0)
        llgId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LLG_ID, 0)
        wardId = securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_WARD_ID, 0)
        locationId =
            securedSharedPreferenceWrapper.getInt(ApiConstants.DPO_WORKING_ROLL_LOCATION_ID, 0)

        districtName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_GEO_DISTRICT, "")
        llgName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LLG, "")
        wardName = securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_WARD, "")
        if (wardName.isNotEmpty()) {
            getWardNumberAndNameUsingCoroutines()
        }
        locationName =
            securedSharedPreferenceWrapper.getString(ApiConstants.DPO_WORKING_ROLL_LOCATION, "")

        setOnClickListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun getWardNumberAndNameUsingCoroutines() {
        launch(Dispatchers.Main) {
            val result: WardList? = withContext(Dispatchers.IO) {
                dataBaseInstance.wardListDao()?.getDPOWard(wardId)
            }
            wardNumber = result!!.wardNumber
            wardNoAndName = "$wardNumber & $wardName"
        }
    }


    override fun onDestroy() {
        job.cancel() // cancel the Job
        super.onDestroy()
    }

    private fun setOnClickListeners() {

        binding?.btnHome?.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        binding?.btnBack?.setOnClickListener {
            finish()
        }

        binding?.cardReverseSync?.setOnClickListener {
            val selectedOption = binding?.rgReverseSyncOptions!!.checkedRadioButtonId
            if (selectedOption == -1) {
                showAlertMessage("Please select any one of the option to Reverse Sync")
            } else {
                when {
                    binding?.rbNewAndTransferIn!!.isChecked -> {

                    }
                    binding?.rbUpdated!!.isChecked -> {

                    }
                    binding?.rbDeleted!!.isChecked -> {

                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun showAlertMessage(alertMessage: String) {
        alert(alertMessage) {
            okButton {
            }
        }.show()
    }


}