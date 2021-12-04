package com.webaddicted.newhiltproject.view.visit.order

import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.webaddicted.newhiltproject.R
import com.webaddicted.newhiltproject.databinding.RowOrderBinding
import com.webaddicted.newhiltproject.utils.common.GlobalUtils
import com.webaddicted.newhiltproject.view.base.BaseAdapter
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class OrderAdapter @Inject constructor(
    private var itemList: ArrayList<String>?,
    val clickListener: (isShowLoader: Boolean) -> Unit,
    val listSizeListener: (Int) -> Unit
) : BaseAdapter(R.layout.row_order) {

    private var searchText: String? = null
    private val searchArray: ArrayList<String> = ArrayList()

    init {
        itemList?.let { this.searchArray.addAll(it) }
    }

    override fun getListSize(): Int {
        return itemList?.size ?: 0
    }

    override fun onBindTo(binding: ViewDataBinding, position: Int) {
        val mBinding = binding as RowOrderBinding
        val item = itemList?.get(position)
        mBinding.prodNameTv.text = "Sku Name"
        mBinding.stockTv.text =
            if (position == 0 || position == 1) mContext.getString(R.string.available_qty, "5")
            else mContext.getString(R.string.not_available_qty, "0")
        mBinding.stockTv.setTextColor(
            ContextCompat.getColor(
                mContext,
                if (position == 0 || position == 1) (R.color.green) else (R.color.light_red)
            )
        )
        val prodAmount =if (position == 0 || position == 1) "3000" else
            GlobalUtils.getCurrencyFormat(
                mContext, "0"
            )
        mBinding.totalAmtTv.text = prodAmount?.split(".")?.get(0)
        GlobalUtils.setBackgroundTintColor(
            mBinding.totalAmtTv,
            if (position != 0 || position != 1) R.color.tabIconDefault else R.color.green
        )
        mBinding.qtyEdtTxt.setText(if (position == 0 || position == 1)"10" else "0")
        mBinding.soqTv.text = mContext.getString(R.string.soq_amt, "4589")
        mBinding.nbpTv.text =
                mContext.getString(R.string.nbp_amt, "25896")
        onClickListener(mBinding, mBinding.minusTv, position)
        onClickListener(mBinding, mBinding.plusTv, position)
    }

    override fun getClickEvent(mBinding: ViewDataBinding, view: View?, position: Int) {
        super.getClickEvent(mBinding, view, position)
        mBinding as RowOrderBinding
        val item = itemList?.get(position)
        when (view?.id) {
            R.id.minusTv -> {
            }
            R.id.plusTv -> {
            }
        }
    }

    private fun updateData() {
        notifyDataSetChanged()
    }

    fun filter(textStr: String?) {
        val charText = textStr!!.lowercase(Locale.getDefault())
        searchText = charText
        itemList?.clear()
        if (charText == null && charText.isBlank()) {
            itemList?.addAll(searchArray)
        } else {
            for (wp in searchArray) {
                if (wp.lowercase(Locale.getDefault()).contains(charText)) {
                    itemList?.add(wp)
                }
//                    else if (wp.skuCode?.lowercase(Locale.getDefault())
//                        ?.contains(charText) == true
//                    ) {
//                      itemList?.add(wp)
//                    }
            }
        }
        itemList?.size?.let { listSizeListener(it) }
        notifyDataSetChanged()
    }
}
