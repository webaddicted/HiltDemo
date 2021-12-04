package com.webaddicted.newhiltproject.view.dashboard

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowL3mRetailerBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class L3mRetailersAdapter(private val itemList: ArrayList<String>?) :
    BaseAdapter(R.layout.row_l3m_retailer) {
    private lateinit var mBinding: RowL3mRetailerBinding

    override fun getListSize(): Int? {
        return itemList?.size
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowL3mRetailerBinding
        val item = itemList?.get(position)
        mBinding.txtTitle.text = "$item : "
        mBinding.txtValue.text = (30 * position).toString()
    }
}