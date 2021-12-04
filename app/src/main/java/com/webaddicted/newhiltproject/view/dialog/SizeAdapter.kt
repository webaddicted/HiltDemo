package com.webaddicted.newhiltproject.view.dialog

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowVehicleSizesBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class SizeAdapter(private val itemList: List<String>) : BaseAdapter(R.layout.row_vehicle_sizes) {
    private lateinit var mBinding: RowVehicleSizesBinding

    override fun getListSize() = itemList.size

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowVehicleSizesBinding
        val item = itemList[position]
        mBinding.sizeTv.text = item
    }

}