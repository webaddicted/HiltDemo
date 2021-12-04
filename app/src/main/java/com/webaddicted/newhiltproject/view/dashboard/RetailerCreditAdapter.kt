package com.webaddicted.newhiltproject.view.dashboard

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowRetailerCreditBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class RetailerCreditAdapter(private val itemList: ArrayList<String>?) :
    BaseAdapter(R.layout.row_retailer_credit) {
    private lateinit var mBinding: RowRetailerCreditBinding

    override fun getListSize(): Int? {
        return itemList?.size
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowRetailerCreditBinding
        val item = itemList?.get(position)
        mBinding.txtName.text = item ?: "--"
        mBinding.txtInvoice.text = "250033" ?: "--"
        mBinding.txtBalance.text = "616546" ?: "--"

    }
}
