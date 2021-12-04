package com.webaddicted.newhiltproject.view.visit.outlet

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowOutletDashboardBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.utils.constant.AppConstant
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class OutletDashboardAdapter(
    private val selectTab: String,
    private val outletData: ArrayList<String>?
) :
    BaseAdapter(R.layout.row_outlet_dashboard) {
    private lateinit var mBinding: RowOutletDashboardBinding

    override fun getListSize(): Int? {
        return outletData?.size
    }

    private fun getTabItem(): List<String> {
        return AppConstant.OutletDashboardTab.values().map { it.value }
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowOutletDashboardBinding
        if (selectTab == getTabItem()[1]) {
            val item = outletData?.get(position)
            val orderDate = GlobalUtils.getFormattedDateFromDate(
                "1995-06-04",
                AppConstant.DISPLAY_DATE_FORMAT,
                AppConstant.SERVER_DATE_FORMAT
            )
            mBinding.materialDescTv.text = orderDate
            mBinding.txtMtdSaleValue.text = "86"
            mBinding.txtMtdSaleQty.text = "63"

        } else {
            val item = outletData?.get(position)
            mBinding.materialDescTv.text = "Product Name"
            mBinding.txtMtdSaleValue.text = "86"
            mBinding.txtMtdSaleQty.text = "63"
        }
    }

}
