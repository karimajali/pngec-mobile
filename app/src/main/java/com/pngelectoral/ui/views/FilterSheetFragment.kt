//package com.pngelectoral.ui.views
//
//import android.annotation.SuppressLint
//import android.app.AlertDialog
//import android.app.Dialog
//import android.content.Context
//import android.content.DialogInterface
//import android.graphics.Color
//import android.os.AsyncTask
//import android.os.Bundle
//import android.util.DisplayMetrics
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.FrameLayout
//import androidx.appcompat.widget.AppCompatEditText
//import androidx.appcompat.widget.AppCompatTextView
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.google.android.material.bottomsheet.BottomSheetBehavior
//import com.google.android.material.bottomsheet.BottomSheetDialog
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import com.pngelectoral.R
//import com.pngelectoral.data.db.DatabaseClient
//import com.pngelectoral.databinding.ItemFilterBinding
//import com.pngelectoral.ui.adapter.GenericAdapter
//import com.pngelectoral.ui.model.ProvinceList
//import com.pngelectoral.ui.model.RegionList
//import com.pngelectoral.ui.model.VoterListFilterModel
//import com.pngelectoral.utility.helper.GlobalCallBack
//import kotlinx.android.synthetic.main.fragment_item_fitersheet.view.*
//import org.jetbrains.anko.alert
//import org.jetbrains.anko.okButton
//import org.jetbrains.anko.support.v4.alert
//import java.util.*
//
//class FilterSheetFragment(
//    private val regionList: List<RegionList>,
//    val statusList: List<VoterListFilterModel>
//) : BottomSheetDialogFragment() {
//    private var callBack: GlobalCallBack? = null
//    private var statusAdapter: GenericAdapter<VoterListFilterModel, ItemFilterBinding>? = null
//    internal var statusId = ArrayList<String>()
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.fragment_item_fitersheet, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val statusListLayoutManager = LinearLayoutManager(activity)
//        saveSelectedLocationAndStatus()
//
//
//        statusAdapter = prepareStatusAdapter()
//        view.rvStatusList.layoutManager = statusListLayoutManager
//        view.rvStatusList.adapter = statusAdapter
//
//        view.ivClose.setOnClickListener {
//            dismissAllowingStateLoss()
//        }
//
//        view.cardApply.setOnClickListener {
//            dismiss()
//            setSelectedLocationAndStatus()
//        }
//
//        view.cardClear.setOnClickListener {
//            clearFilterValues()
//        }
//
//        view.rlRegion.setOnClickListener {
//            showRegionOptions(regionList, view)
//        }
//
//        view.rlRegion.setOnClickListener {
//
//            if (view.etRegion.text.toString().isEmpty()) {
//                showAlertMessage(
//                    "Please select region"
//                )
//            } else {
//                ProvinceListAsync().execute()
//            }
//
//        }
//    }
//
//    private fun showAlertMessage(message: String) {
//        alert(message) {
//            okButton {
//            }
//        }.show()
//    }
//
//
//    private fun showRegionOptions(result: List<RegionList>?, mView: View) {
//        val regionList: Array<String> = getRegionList(ArrayList(result!!))
//        showSingleSelectionDialog(
//            regionList,
//            mView.etRegion!!,
//            "Region",
//            mView
//        )
//
//    }
//
//    private fun getRegionList(regions: ArrayList<RegionList>): Array<String> {
//        val regionList: ArrayList<String> = ArrayList()
//        for (region in regions) {
//            regionList.add(region.regionName)
//        }
//        return regionList.toArray(arrayOfNulls<String>(regionList.size))
//    }
//
//    @SuppressLint("StaticFieldLeak")
//    internal inner class ProvinceListAsync : AsyncTask<Void?, Void?, List<ProvinceList>?>() {
//
//        override fun doInBackground(vararg p0: Void?): List<ProvinceList>? {
//
//            val dataBaseInstance =
//                DatabaseClient.getInstance(this@VoterDetailsActivity).appDatabase
//            return dataBaseInstance?.provinceListDao()
//                ?.getProvinceOfRegion(regionListArray!![bindingUpdateStatus?.etRegion?.tag as Int].regionId)
//        }
//
//        override fun onPostExecute(result: List<ProvinceList>?) {
//            super.onPostExecute(result)
//            showProvinceOptions(result)
//        }
//    }
//
//    var provinceListArray: ArrayList<ProvinceList>? = ArrayList()
//    private fun showProvinceOptions(result: List<ProvinceList>?) {
//
//        provinceListArray = ArrayList(result!!)
//        val provinceList: Array<String> = getProvinceList(provinceListArray!!)
//        showSingleSelectionDialog(
//            provinceList,
//            bindingUpdateStatus?.etNewProvince!!,
//            "Province"
//        )
//
//    }
//
//    private fun getProvinceList(provinces: ArrayList<ProvinceList>): Array<String> {
//
//        val provinceList: ArrayList<String> = ArrayList()
//        for (province in provinces) {
//            provinceList.add(province.provinceName)
//        }
//        return provinceList.toArray(arrayOfNulls<String>(provinceList.size))
//    }
//
//    private fun showSingleSelectionDialog(
//        singleChoiceItems: Array<String>,
//        tvSelection: AppCompatTextView,
//        selectedField: String,
//        mView: View
//    ) {
//
//        val selectionValue =
//            if (tvSelection.tag == null) -1 else tvSelection.tag as Int
//
//        val mBuilder =
//            AlertDialog.Builder(context)
//
//        mBuilder.setSingleChoiceItems(
//            singleChoiceItems,
//            selectionValue
//        ) { dialogInterface, clickItem ->
//            tvSelection.tag = clickItem
//            tvSelection.text = singleChoiceItems[clickItem]
//
//            when (selectedField) {
//                "Region" -> {
//
//                    mView.etProvince?.tag = -1
//                    mView.etProvince?.text = ""
//
//                    mView.etDistrict?.tag = -1
//                    mView.etDistrict?.text = ""
//
//                    mView.etLLG?.tag = -1
//                    mView.etLLG?.text = ""
//
//                    mView.etWard?.tag = -1
//                    mView.etWard?.text = ""
//
//                    mView.etLocation?.tag = -1
//                    mView.etLocation?.text = ""
//
//                    provinceListArray?.clear()
//                    districtListArray?.clear()
//                    llgListArray?.clear()
//                    wardNoAndNameArray?.clear()
//                    locationArray?.clear()
//
//
//                }
//                "Province" -> {
//
//                    mView.etDistrict?.tag = -1
//                    mView.etDistrict?.text = ""
//
//                    mView.etLLG?.tag = -1
//                    mView.etLLG?.text = ""
//
//                    mView.etWard?.tag = -1
//                    mView.etWard?.text = ""
//
//                    mView.etLocation?.tag = -1
//                    mView.etLocation?.text = ""
//
//                    districtListArray?.clear()
//                    llgListArray?.clear()
//                    wardNoAndNameArray?.clear()
//                    locationArray?.clear()
//
//                }
//                "District" -> {
//
//                    mView.etLLG?.tag = -1
//                    mView.etLLG?.text = ""
//
//                    mView.etWard?.tag = -1
//                    mView.etWard?.text = ""
//
//                    mView.etLocation?.tag = -1
//                    mView.etLocation?.text = ""
//
//                    llgListArray?.clear()
//                    wardNoAndNameArray?.clear()
//                    locationArray?.clear()
//
//                }
//                "LLG" -> {
//
//                    mView.etWard?.tag = -1
//                    mView.etWard?.text = ""
//
//                    mView.etLocation?.tag = -1
//                    mView.etLocation?.text = ""
//
//                    wardNoAndNameArray?.clear()
//                    locationArray?.clear()
//
//                }
//                "Ward" -> {
//
//                    mView.etLocation?.tag = -1
//                    mView.etLocation?.text = ""
//
//                    locationArray?.clear()
//                }
//            }
//
//            dialogInterface.dismiss()
//        }
//        val mDialog = mBuilder.create()
//        mDialog.show()
//    }
//
//    private fun clearFilterValues() {
//        statusId.clear()
//        for (i in statusList.indices) {
//            statusList[i].selected = false
//        }
//        statusAdapter!!.notifyDataSetChanged()
//    }
//
//    private fun saveSelectedLocationAndStatus() {
//        for (i in statusList.indices) {
//            if (statusList[i].selected!!) {
//                statusId.add(statusList[i].id!!)
//            }
//        }
//    }
//
//    private fun prepareStatusAdapter(): GenericAdapter<VoterListFilterModel, ItemFilterBinding>? {
//        return object : GenericAdapter<VoterListFilterModel, ItemFilterBinding>(statusList) {
//            override fun getLayoutId(): Int {
//                return R.layout.item_filter
//            }
//
//            override fun onBindData(
//                model: VoterListFilterModel,
//                position: Int,
//                dataBinding: ItemFilterBinding
//            ) {
//
//                dataBinding.tvFilterItem.text = model.name
//
//                if (statusList[position].selected!!) {
//                    dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box_tick1)
//                } else {
//                    dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box)
//                }
//
//                dataBinding.root.setOnClickListener {
//
//                    if (statusId.contains(statusList[position].id)) {
//                        statusId.remove(statusList[position].id)
//                        dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box)
//                    } else {
//                        statusId.add(statusList[position].id!!)
//                        dataBinding.ivSelectFilter.setImageResource(R.drawable.check_box_tick1)
//                    }
//                }
//            }
//
//        }
//    }
//
//    private fun setSelectedLocationAndStatus() {
//        for (i in statusList.indices) {
//            statusList[i].selected = statusId.contains(statusList[i].id)
//        }
//        callBack?.callBack(regionList, statusList)
//    }
//
//    fun setOnItemSelectListener(callBack: GlobalCallBack) {
//        this.callBack = callBack
//    }
//
//    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
//        dialog.setOnShowListener {
//            setupBottomSheet(it)
//            setupFullHeight(dialog)
//        }
//        return dialog
//    }
//
//    private fun setupBottomSheet(dialogInterface: DialogInterface) {
//        val bottomSheetDialog = dialogInterface as BottomSheetDialog
//        val bottomSheet = bottomSheetDialog.findViewById<View>(
//            com.google.android.material.R.id.design_bottom_sheet
//        )
//            ?: return
//        bottomSheet.setBackgroundColor(Color.WHITE)
//    }
//
//    private fun setupFullHeight(dialog: BottomSheetDialog) {
//
//        val bottomSheet = dialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
//        val behavior = BottomSheetBehavior.from(bottomSheet!!)
//        val layoutParams = bottomSheet.layoutParams
//        val windowHeight = getWindowHeight()
//        if (layoutParams != null) layoutParams.height = windowHeight
//        bottomSheet.layoutParams = layoutParams
//        behavior.state = BottomSheetBehavior.STATE_EXPANDED
//    }
//
//    private fun getWindowHeight(): Int {
//        // Calculate window height for fullscreen use
//        val displayMetrics = DisplayMetrics()
//        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
//        return displayMetrics.heightPixels
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        val parent = parentFragment
//    }
//
//    companion object {
//
//        fun newInstance(
//            regionList: List<RegionList>,
//            statusList: List<VoterListFilterModel>
//        ): FilterSheetFragment =
//            FilterSheetFragment(regionList, statusList).apply {}
//    }
//
//
//}
//
//
