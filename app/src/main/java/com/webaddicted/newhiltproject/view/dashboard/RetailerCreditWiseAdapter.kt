package com.webaddicted.newhiltproject.view.dashboard

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowRetailerCreditBinding
import com.webaddicted.newhiltproject.view.base.BaseAdapter
import java.text.SimpleDateFormat
import java.util.*

class RetailerCreditWiseAdapter(private val itemList: ArrayList<String>?) :
    BaseAdapter(R.layout.row_retailer_credit) {
    private lateinit var mBinding: RowRetailerCreditBinding

    override fun getListSize(): Int? {
        return itemList?.size
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowRetailerCreditBinding
        val item = itemList?.get(position)
        mBinding.txtInvoiceHdr.text = mContext.getString(R.string.bills)
        mBinding.txtBalanceHdr.text = mContext.getString(R.string.due)
        mBinding.txtName.text =
            item + " :  " + SimpleDateFormat("dd-MM-yyyy hh:mm", Locale.getDefault()).format(Date())
        mBinding.txtInvoice.text = "33"
        mBinding.txtBalance.text = "2344"

    }
}
