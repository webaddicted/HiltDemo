package com.webaddicted.newhiltproject.view.dialog

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.DialogCompareBinding
import com.webaddicted.newhiltproject.utils.common.DialogUtils
import com.webaddicted.newhiltproject.utils.common.visible
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseDialog

class CompareDialog : BaseDialog(R.layout.dialog_compare) {
    private var compareTyreResponse: String? = null
    private var compareTyreResponse1: String? = null
    private lateinit var mBinding: DialogCompareBinding
    private var division = ""

    companion object {
        val TAG = CompareDialog::class.qualifiedName
        const val TYRE1 = "tyre1"
        const val TYRE2 = "tyre2"
        const val TILE_TYPE = "tileType"

        fun dialog(
            compareTyreResponse: String,
            compareTyreResponse1: String,
            tileType: String
        ): CompareDialog {
            val dialog = CompareDialog()
            val bundle = Bundle()
            bundle.putSerializable(TYRE1, compareTyreResponse)
            bundle.putSerializable(TYRE2, compareTyreResponse1)
            bundle.putString(TILE_TYPE, tileType)
            dialog.arguments = bundle
            return dialog
        }
    }

    override fun onBindTo(binding: ViewDataBinding) {
        mBinding = binding as DialogCompareBinding
        arguments?.let {
            compareTyreResponse = it.getString(TYRE1)
            compareTyreResponse1 = it.getString(TYRE2)
            division = it.getString(TILE_TYPE).toString()
        }
        init()
        clickListener()
        setUpData()
    }

    private fun init() {
//    mBinding.txtTitle.text = badgeName?.capitalize()
//    mActivity.showDrawable(badgeName, mBinding.imgBadge, false)
    }

    private fun clickListener() {
//    mBinding.imgClose.setOnClickListener(this)
    }

    private fun setUpData() {
        setLabels()
        showDivisionRating()
        mBinding.tyreName1.text = "Name :" ?: "--"
        mBinding.tyreGrip1.text = "--"
        mBinding.tyreWetGrip1.text = "586"
        mBinding.tyreLife1.text = "586"
        mBinding.tyreStrengthLoad1.text = "586"
        mBinding.tyreRide1.text = "586"
        mBinding.tyreRetread1.text = "586"
        mBinding.tyreMilage1.text = "586"
        mBinding.loadability1.text = "586"
        mBinding.tyreComfort1.text = "586"

        mBinding.tyreName2.text = "Name" ?: "--"
        mBinding.tyreGrip2.text = "369"
        mBinding.tyreLife2.text = "369"
        mBinding.tyreWetGrip2.text = "369"
        mBinding.tyreStrengthLoad2.text = "369"
        mBinding.tyreRide2.text = "369"
        mBinding.tyreRetread2.text = "369"
        mBinding.tyreMilage2.text = "369"
        mBinding.loadability2.text = "369"
        mBinding.tyreComfort2.text = "369"
    }

    private fun setLabels() {
            mBinding.ratingHdrTv.text = "rating"
            mBinding.gripHdrTv.text = "grip"
            mBinding.wetGripHdrTv.text = "wetGrip"
            mBinding.comfortHdrTv.text = "comfort"
            mBinding.milageHdrTv.text = "mileage"
            mBinding.strengthHdrTv.text = "strengthLoad"
            mBinding.tyreLyfHdrTv.text = "tyreLife"
            mBinding.rideHdrTv.text = "rideHandling"
            mBinding.loadabilityHdrTv.text = "loadability"
            mBinding.retreadHdrTv.text = "retreadability"
    }

    private fun showDivisionRating() {
        when (division) {
            AppConstant.ECatalogueVehicleType.MOTORCYCLE.value, AppConstant.ECatalogueVehicleType.SCOOTER.value -> {

                mBinding.gripHdrTv.visible()
                mBinding.horDivider2.visible()
                mBinding.tyreGrip1.visible()
                mBinding.tyreGrip2.visible()

                mBinding.comfortHdrTv.visible()
                mBinding.tyreComfort1.visible()
                mBinding.tyreComfort2.visible()

                mBinding.rideHdrTv.visible()
                mBinding.horDivider6.visible()
                mBinding.tyreRide1.visible()
                mBinding.tyreRide2.visible()

                mBinding.wetGripHdrTv.visible()
                mBinding.horDivider3.visible()
                mBinding.tyreWetGrip1.visible()
                mBinding.tyreWetGrip2.visible()

                mBinding.tyreLyfHdrTv.visible()
                mBinding.horDivider4.visible()
                mBinding.tyreLife1.visible()
                mBinding.tyreLife2.visible()
            }
            AppConstant.ECatalogueVehicleType.CAR.value, AppConstant.ECatalogueVehicleType.SUV.value -> {

                mBinding.gripHdrTv.visible()
                mBinding.horDivider2.visible()
                mBinding.tyreGrip1.visible()
                mBinding.tyreGrip2.visible()

                mBinding.comfortHdrTv.visible()
                mBinding.tyreComfort1.visible()
                mBinding.tyreComfort2.visible()

                mBinding.rideHdrTv.visible()
                mBinding.horDivider6.visible()
                mBinding.tyreRide1.visible()
                mBinding.tyreRide2.visible()

                mBinding.strengthHdrTv.visible()
                mBinding.horDivider5.visible()
                mBinding.tyreStrengthLoad1.visible()
                mBinding.tyreStrengthLoad2.visible()

                mBinding.milageHdrTv.visible()
                mBinding.horDivider8.visible()
                mBinding.tyreMilage1.visible()
                mBinding.tyreMilage2.visible()
            }
            AppConstant.ECatalogueVehicleType.TRUCK_BUS.value, AppConstant.ECatalogueVehicleType.LAST_MILE.value, AppConstant.ECatalogueVehicleType.LCV.value -> {

                mBinding.loadabilityHdrTv.visible()
                mBinding.horDivider9.visible()
                mBinding.loadability1.visible()
                mBinding.loadability2.visible()

                mBinding.milageHdrTv.visible()
                mBinding.horDivider8.visible()
                mBinding.tyreMilage1.visible()
                mBinding.tyreMilage1.visible()

                mBinding.retreadHdrTv.visible()
                mBinding.horDivider7.visible()
                mBinding.tyreRetread1.visible()
                mBinding.tyreRetread2.visible()
            }
            else -> {
                mBinding.tyreLyfHdrTv.visible()
                mBinding.horDivider4.visible()
                mBinding.tyreLife1.visible()
                mBinding.tyreLife2.visible()

                mBinding.gripHdrTv.visible()
                mBinding.horDivider2.visible()
                mBinding.tyreGrip1.visible()
                mBinding.tyreGrip2.visible()

                mBinding.retreadHdrTv.visible()
                mBinding.horDivider7.visible()
                mBinding.tyreRetread1.visible()
                mBinding.tyreRetread2.visible()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        DialogUtils.fullScreenTransDialogBounds(mActivity, dialog)
    }

    override fun onClick(v: View) {
        super.onClick(v)
        when (v.id) {
            R.id.img_close -> {
                dismiss()
            }
        }
    }
}
