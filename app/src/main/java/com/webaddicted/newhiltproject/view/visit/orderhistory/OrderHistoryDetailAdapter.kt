package com.webaddicted.newhiltproject.view.visit.orderhistory

import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowOrderHistoryItemBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.view.base.BaseAdapter

class OrderHistoryDetailAdapter(
    private val itemList: List<String>?,
    val clickListener: (String) -> Unit
) :
    BaseAdapter(R.layout.row_order_history_item) {
    private lateinit var mBinding: RowOrderHistoryItemBinding

    override fun getListSize(): Int? {
        return itemList?.size
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        mBinding = binding as RowOrderHistoryItemBinding
        val item = itemList?.get(position)
        mBinding.txtSubDealerName.text = "Sku Name"

        mBinding.txtStock.text = "25"
        mBinding.txtPrice.text = GlobalUtils.getCurrencyFormat(mContext, "586")
        mBinding.txtTotalPrice.text = GlobalUtils.getCurrencyFormat(mContext, "258")
        mBinding.txtOrderQty.text = ("25" ?: "--").toString()
//        mBinding.txtOrderQty.setOnClickListener { item?.let { it1 -> clickListener(it1) } }
    }
}